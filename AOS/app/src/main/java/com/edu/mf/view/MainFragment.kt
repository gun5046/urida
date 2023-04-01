package com.edu.mf.view

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.edu.mf.R
import com.edu.mf.databinding.FragmentLanguageBinding
import com.edu.mf.databinding.FragmentMainBinding
import com.edu.mf.repository.model.Category
import com.edu.mf.repository.model.picture.DetectedPicture
import com.edu.mf.utils.BitmapUtil
import com.edu.mf.view.common.MainActivity
import com.edu.mf.view.community.CommunityFragment
import com.edu.mf.view.drawing.DrawingAdapter
import com.edu.mf.view.drawing.DrawingFragment
import com.edu.mf.view.picture.PictureAdapter
import com.edu.mf.view.picture.PictureFragment
import com.edu.mf.view.picture.PicturePreviewFragment
import com.edu.mf.view.picture.PictureResultFragment
import com.edu.mf.view.study.StudyAdapter
import com.edu.mf.view.study.StudyFragment
import com.edu.mf.view.study.learn.LearnFragment
import com.edu.mf.view.study.quiz.QuizFragment
import com.edu.mf.view.study.reslove.ResolveFragment
import com.edu.mf.viewmodel.MainViewModel
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG="MainFragment"
class MainFragment: Fragment() {
    private lateinit var binding: FragmentMainBinding
    private lateinit var mainActivity: MainActivity
    private lateinit var viewModel : MainViewModel
    private lateinit var wordAdapter : StudyAdapter
    private lateinit var pictureAdapter: PictureAdapter
    private lateinit var drawingAdapter : DrawingAdapter
    private var wordList : ArrayList<Category> = arrayListOf()
    private var photoList : ArrayList<Category> = arrayListOf()
    private var drawingList : ArrayList<Category> = arrayListOf()


    private lateinit var previewPermissionLauncher : ActivityResultLauncher<String>
    private lateinit var cameraPermissionLauncher : ActivityResultLauncher<String>
    private lateinit var storagePermissionLauncher : ActivityResultLauncher<String>
    private lateinit var reqPermission : ActivityResultLauncher<String>
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
        binding = FragmentMainBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        pictureViewModel = ViewModelProvider(requireActivity())[PictureViewModel::class.java]
        mainActivity = MainActivity.getInstance()!!
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        Glide.with(this).load(R.raw.charac).into(binding.imageviewChrac)
        binding.apply {
            cardviewCommunity.setOnClickListener {
                mainActivity.addFragment(CommunityFragment())
            }
            imageviewSettings.setOnClickListener{
                showSettingDialog()
            }
        }
    }

    // 그림으로 찾기 클릭 시 permission 체크
    private fun chkPermissionDrawingFragment(){
        reqPermission = requestPermission()
    }
    // 미디어 접근권한
    private fun requestPermission(): ActivityResultLauncher<String> {
        return registerForActivityResult(ActivityResultContracts.RequestPermission()){
            if (it){
                mainActivity.addFragment(DrawingFragment())
            } else{
                Toast.makeText(
                    requireContext(), "저장장치 접근 권한을 확인해주세요", Toast.LENGTH_SHORT
                ).show()

                val settingsIntent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    .setData(Uri.parse("package:" + requireContext().packageName))
                requireContext().startActivity(settingsIntent)
            }
        }
    }

    fun showSettingDialog(){
        val builder = AlertDialog.Builder(requireContext())
        val dialogBinding = FragmentLanguageBinding.inflate(layoutInflater)
        builder.setView(dialogBinding.root)
        val dialog = builder.create()
        dialogBinding.apply {
            layoutKorean.setOnClickListener {
                changeLocale(0)
                dialog.dismiss()
            }
            layoutChinese.setOnClickListener {
                changeLocale(1)
                dialog.dismiss()
            }
            layoutVietnam.setOnClickListener {
                changeLocale(2)
                dialog.dismiss()
            }
            layoutEnglish.setOnClickListener {
                changeLocale(3)
                dialog.dismiss()
            }
        }
        dialog.show()
    }
    private fun init(){
        chkPermissionDrawingFragment()
        initPhoto()
        if(wordList.size<1)
        setListData()
        setAdapter()
   }

    private fun initPhoto(){
        previewPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){
            if(it){
                mainActivity.addFragment(PicturePreviewFragment())
            } else {
                Toast.makeText(requireContext(), "카메라 권한을 확인해주세요", Toast.LENGTH_SHORT).show()
            }
        }
        cameraPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){
            if(it){
                launchCamera()
            } else {
                Toast.makeText(requireContext(), "카메라 권한을 확인해주세요", Toast.LENGTH_SHORT).show()
            }
        }
        storagePermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){
            if(it){
                launchGallery()
            } else {
                Toast.makeText(requireContext(), "저장장치 접근 권한을 확인해주세요", Toast.LENGTH_SHORT).show()
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


    private fun setAdapter(){
        wordAdapter = StudyAdapter(wordList)
        wordAdapter.setOnStudyClickListener(object:StudyAdapter.StudyClickListener{
            override fun onClick(position: Int, data: Category) {
                when(position){
                    //낱말 익히기
                    0->{
                        viewModel.setMode(1)
                        mainActivity.addFragment(LearnFragment())
                    }
                    //낱말 퀴즈
                    1->{
                        viewModel.setMode(2)
                        mainActivity.addFragment(QuizFragment())
                    }
                    //다시 풀어보기
                    else->{
                        mainActivity.addFragment(ResolveFragment())
                    }

                }
            }

        })
        binding.recylcerviewFragmentMainWord.apply{
            adapter = wordAdapter
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        }
        pictureAdapter = PictureAdapter(photoList)
        pictureAdapter.setOnPictureClickListener(object : PictureAdapter.PictureClickListener{
            override fun onClick(position: Int, data: Category) {
                when(position){
                    //실시간으로 확인하기
                    0->{
                        if(ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                            mainActivity.addFragment(PicturePreviewFragment())
                        } else {
                            previewPermissionLauncher.launch(Manifest.permission.CAMERA)
                        }
                    }
                    //사진 찍고 확인하기
                    1->{
                        if(ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                            launchCamera()
                        } else {
                            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                        }
                    }
                    //갤러리에서 불러오기
                    else->{
                        if(ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                            launchGallery()
                        } else {
                            storagePermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                        }
                    }
                }
            }

        })
        binding.recylcerviewFragmentMainPhoto.apply {
            adapter = pictureAdapter
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        }

        drawingAdapter = DrawingAdapter(drawingList)
        drawingAdapter.setOnClickDrawingClickListener(object:DrawingAdapter.DrawingClickListener{
            override fun onClick(position: Int, data: Category) {
                when(position){
                    //그림으로 찾아보기
                    0->{
                        reqPermission.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    }
                    //음성으로 찾아보기
                    else->{

                    }
                }
            }

        })
        binding.recylcerviewFragmentDrawing.apply {
            adapter = drawingAdapter
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        }
    }

    /**
     * Recyclerview안에 들어가는 data 초기화
     */
    private fun setListData(){
        wordList.add(Category("낱말 익히기","평소 헷갈렸던 단어\n그림과 함께 \n학습해보아요",R.drawable.ic_learn,LearnFragment()))
        wordList.add(Category("낱말 퀴즈","같이 공부했던 단어를\n활용해서\n퀴즈를 풀어보아요",R.drawable.ic_quiz,QuizFragment()))
        wordList.add(Category("다시 풀어보기","아쉽게 틀렸던 \n퀴즈들은\n다시 한번 풀어보아요",R.drawable.ic_resolve,ResolveFragment()))
        photoList.add(Category("실시간으로 확인하기","어?저게 뭐였지?\n걱정마세요\n카메라에 탐지된 물체가\n무엇인지 알려줄게요",R.drawable.ic_video,LearnFragment()))
        photoList.add(Category("사진 찍고 확인하기","사진을 찍어보세요\n사진에 탐지된 물체가\n무엇인지 알려줄게요",R.drawable.ic_camera_plus,LearnFragment()))
        photoList.add(Category("갤러리에서 불러오기","갤러리에서 사진을 선택해보세요\n사진에 탐지된 물체가\n무엇인지 알려줄게요",R.drawable.ic_gallery,LearnFragment()))
        drawingList.add(Category("그림으로 찾기","무슨 단어였는지 헷갈리세요?\n그림을 그려보세요\n저희가 알려줄게요",R.drawable.ic_drawing_main,DrawingFragment()))
        drawingList.add(Category("음성으로 찾기","어떻게 쓰는지 모르시겠다고요?\n음성을 녹음해보세요\n저희가 알려줄게요",R.drawable.ic_mic,DrawingFragment()))
    }

    fun changeLocale(language: Int){
        mainActivity.changeLocale(language)
        CoroutineScope(Dispatchers.IO).launch {
            mainActivity.loginService.languageSetting(language, mainActivity.user!!.uid!!)
        }
        mainActivity.changeFragment(MainFragment())
    }
}