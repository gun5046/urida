package com.edu.mf.view.picture

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.edu.mf.databinding.FragmentPictureBinding
import com.edu.mf.repository.model.picture.DetectedPicture
import com.edu.mf.utils.BitmapUtil
import com.edu.mf.view.common.MainActivity
import com.edu.mf.viewmodel.PictureViewModel
import com.google.mlkit.common.model.LocalModel
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.objects.ObjectDetection
import com.google.mlkit.vision.objects.ObjectDetector
import com.google.mlkit.vision.objects.custom.CustomObjectDetectorOptions

private const val TAG = "PictureFragment"

class PictureFragment: Fragment() {
    private lateinit var binding: FragmentPictureBinding
    private lateinit var mainActivity: MainActivity
    private lateinit var pictureViewModel: PictureViewModel
    private var uri: Uri? = null
    private var translator: Translator? = null
    private val localModel = LocalModel.Builder()
        .setAssetFilePath("object_labeler.tflite")
        .build()
    private val customObjectDetectorOptions =
        CustomObjectDetectorOptions.Builder(localModel)
            .setDetectorMode(CustomObjectDetectorOptions.SINGLE_IMAGE_MODE)
            .enableClassification()
            .enableMultipleObjects().setMaxPerObjectLabelCount(1)
            .build()
    private val objectDetector = ObjectDetection.getClient(customObjectDetectorOptions)
    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == Activity.RESULT_OK){
            if(it.data != null){
                uri = it!!.data!!.data
                pictureViewModel.setUri(uri)
                detect(objectDetector)
            } else if(uri != null){
                pictureViewModel.setUri(uri)
                detect(objectDetector)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPictureBinding.inflate(inflater, container, false)
        mainActivity = MainActivity.getInstance()!!
        pictureViewModel = ViewModelProvider(requireActivity())[PictureViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val previewPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){
            if(it){
                mainActivity.addFragment(PicturePreviewFragment())
            } else {
                Toast.makeText(requireContext(), "카메라 권한을 확인해주세요", Toast.LENGTH_SHORT).show()
            }
        }
        val cameraPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){
            if(it){
                launchCamera()
            } else {
                Toast.makeText(requireContext(), "카메라 권한을 확인해주세요", Toast.LENGTH_SHORT).show()
            }
        }
        val storagePermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){
            if(it){
                launchGallery()
            } else {
                Toast.makeText(requireContext(), "저장장치 접근 권한을 확인해주세요", Toast.LENGTH_SHORT).show()
            }
        }
        binding.cardviewPreview.setOnClickListener {
            if(ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                mainActivity.addFragment(PicturePreviewFragment())
            } else {
                previewPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
        binding.cardviewCamera.setOnClickListener {
            if(ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                launchCamera()
            } else {
                cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
        binding.cardviewGallery.setOnClickListener {
            if(ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                launchGallery()
            } else {
                storagePermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
        val translateOptions = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.ENGLISH)
            .setTargetLanguage(TranslateLanguage.KOREAN)
            .build()
        translator = Translation.getClient(translateOptions)
        translator!!.downloadModelIfNeeded()
        lifecycle.addObserver(translator!!)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    fun detect(detector: ObjectDetector){
        pictureViewModel.clearPicture()
        val bitmap = BitmapUtil.getBitmapFromContentUri(requireActivity().contentResolver, pictureViewModel.uri!!)
        val image = InputImage.fromBitmap(bitmap!!, 0)
        mainActivity.addFragment(PictureResultFragment())
        detector.process(image)
            .addOnSuccessListener {
                for(detected in it){
                    if (detected.labels.isNotEmpty()){
                        translator!!.translate(detected.labels[0].text)
                            .addOnSuccessListener { text ->
                                val detectedPicture = DetectedPicture(
                                    Bitmap.createBitmap(
                                        bitmap,
                                        detected.boundingBox.left,
                                        detected.boundingBox.top,
                                        detected.boundingBox.width(),
                                        detected.boundingBox.height()
                                    ),
                                    if(text.equals("쥐")) "마우스" else text
                                )
                                Log.d(TAG, "detect: $text")
                                pictureViewModel.addPicture(detectedPicture)
                            }
                    }
                }
            }
    }

    fun launchCamera(){
        uri = null
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            val values = ContentValues()
            values.put(MediaStore.Images.Media.TITLE, "New Picture")
            values.put(MediaStore.Images.Media.DESCRIPTION, "From Camera")
            uri = requireActivity().contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
            launcher.launch(intent)
        }
    }

    fun launchGallery(){
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        launcher.launch(Intent.createChooser(intent, ""))
    }
}