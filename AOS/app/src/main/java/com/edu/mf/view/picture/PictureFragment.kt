package com.edu.mf.view.picture

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.edu.mf.databinding.FragmentPictureBinding
import com.edu.mf.utils.BitmapUtil
import com.edu.mf.view.common.MainActivity
import com.edu.mf.viewmodel.PictureViewModel
import com.google.mlkit.common.model.LocalModel
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
        val localModel = LocalModel.Builder()
            .setAssetFilePath("object_labeler.tflite")
            .build()
        val customObjectDetectorOptions =
            CustomObjectDetectorOptions.Builder(localModel)
                .setDetectorMode(CustomObjectDetectorOptions.SINGLE_IMAGE_MODE)
                .enableClassification()
                .enableMultipleObjects()
                .build()
        val objectDetector = ObjectDetection.getClient(customObjectDetectorOptions)
        val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode == Activity.RESULT_OK){
                if(it.data != null){
                    uri = it!!.data!!.data
                    pictureViewModel.setUri(uri)
                    detect(objectDetector)
//                    mainActivity.addFragment(PictureResultFragment())
                    Log.d(TAG, "onViewCreated: $uri")
                } else if(uri != null){
                    pictureViewModel.setUri(uri)
                    detect(objectDetector)
//                    mainActivity.addFragment(PictureResultFragment())
                }
            }
        }
        binding.cardviewCamera.setOnClickListener {
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
        binding.cardviewGallery.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            launcher.launch(Intent.createChooser(intent, ""))
        }
    }

    fun detect(detector: ObjectDetector){
        val bitmap = BitmapUtil.getBitmapFromContentUri(requireActivity().contentResolver, pictureViewModel.uri!!)
        val image = InputImage.fromBitmap(bitmap!!, 0)
        detector.process(image)
            .addOnSuccessListener {
                for(detected in it){
                    Log.d(TAG, "detect: ${bitmap.height}")
                    Log.d(TAG, "detect: ${bitmap.width}")
                    Log.d(TAG, "detect: ${detected.labels[0].text}")
                    Log.d(TAG, "detect: ${detected.boundingBox.toShortString()}")
                }
            }
    }
}