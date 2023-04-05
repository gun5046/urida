package com.edu.mf.view.community

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.ActionBar
import android.os.Bundle
import android.provider.MediaStore.Images
import android.provider.OpenableColumns
import android.provider.Settings
import android.util.Log
import android.view.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.loader.content.CursorLoader
import com.edu.mf.R
import com.edu.mf.databinding.FragmentCommunityRegisterBinding
import com.edu.mf.repository.api.CommunityService
import com.edu.mf.repository.model.User
import com.edu.mf.repository.model.community.*
import com.edu.mf.utils.App
import com.edu.mf.view.common.LoadingDialog
import com.edu.mf.view.common.MainActivity
import com.edu.mf.view.common.NotificationDialog
import com.edu.mf.view.community.board.CommunityBoardFragment
import com.edu.mf.view.drawing.result.DrawingResultShareDialog
import com.edu.mf.viewmodel.CommunityViewModel
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okio.BufferedSink
import okio.source
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

private const val TAG = "CommunityRegisterFragme"
class CommunityRegisterFragment(
    private val boardItem: BoardListItem?,
    private var tabPosition: Int
    ): Fragment(), MenuProvider {
    private lateinit var binding: FragmentCommunityRegisterBinding
    private lateinit var mainActivity: MainActivity
    private lateinit var communityService: CommunityService
    private lateinit var user: User

    private lateinit var actionBar: ActionBar
    private lateinit var drawingUri: Uri
    private var galleryUri = "".toUri()
    private val loadingDialog = LoadingDialog()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCommunityRegisterBinding.inflate(inflater, container, false)
        mainActivity = MainActivity.getInstance()!!
        communityService = mainActivity.communityService
        user = App.sharedPreferencesUtil.getUser()!!

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setActionBar()
        chkPermissionGallery()
        chkDrawingUri()

        if (boardItem != null){
            binding.imageviewFragmentCommunityRegisterPlus.visibility = View.GONE
        }
        binding.boardListItem = boardItem
    }

    // 그림 uri가 있는지 체크
    private fun chkDrawingUri(){
        drawingUri = DrawingResultShareDialog.savedDrawingUri
        if (drawingUri != "".toUri()){
            binding.imageviewFragmentCommunityRegister.background =
                ContextCompat.getDrawable(requireContext(), R.color.white)
            setImg(drawingUri)

            DrawingResultShareDialog.savedDrawingUri = "".toUri()
            binding.cardviewFragmentCommunityRegister.isClickable = false
        }
    }

    // cardview 클릭 시 permission 체크
    private fun chkPermissionGallery(){
        val reqPermission = requestPermission()
        binding.cardviewFragmentCommunityRegister.setOnClickListener {
            if (boardItem == null){
                reqPermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
    }

    // 미디어 접근권한
    private fun requestPermission(): ActivityResultLauncher<String> {
        return registerForActivityResult(ActivityResultContracts.RequestPermission()){
            if (it){
                openGallery()
            } else{
                NotificationDialog(
                    resources.getString(
                        R.string.fragment_picture_permission_storage
                    )
                ).show(childFragmentManager, null)

                val settingsIntent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    .setData(Uri.parse("package:" + requireContext().packageName))
                requireContext().startActivity(settingsIntent)
            }
        }
    }

    // 갤러리 띄우기
    private fun openGallery(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.setDataAndType(Images.Media.EXTERNAL_CONTENT_URI, "image/*")
        result.launch(intent)
    }

    val result = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        if (it.resultCode == Activity.RESULT_OK){
            val uri = it.data!!.data

            if (uri != null){
                setImg(uri)
            }
        }
    }

    // 이미지뷰에 이미지 띄우기
    private fun setImg(uri: Uri){
        if (!getFileSize(uri)){
            NotificationDialog(
                resources.getString(
                    R.string.fragment_community_register_edittext_imagesize_fail
                )
            ).show(childFragmentManager, null)
        } else{
            if (drawingUri == "".toUri()){
                galleryUri = uri
            }
            binding.imageviewFragmentCommunityRegister.setImageURI(uri)
            binding.imageviewFragmentCommunityRegisterPlus.visibility = View.GONE
        }
    }

    // get img size
    private fun getFileSize(imgUri: Uri): Boolean{
        val path = getPath(imgUri)
        if (path == ""){
            return false
        }

        val file = File(path)
        val fileSize = Integer.parseInt((file.length()).toString())
        if(fileSize > 83_886_080){
            return false
        }
        return true
    }

    // 이미지 절대경로 가져오기
    private fun getPath(uri: Uri): String{
        val data = arrayOf(Images.Media.DATA)
        val cursorLoader = CursorLoader(requireContext(), uri, data, null, null, null)
        val cursor = cursorLoader.loadInBackground()!!
        val idx = cursor.getColumnIndexOrThrow(Images.Media.DATA)
        cursor.moveToFirst()

        return cursor.getString(idx)
    }

    // uri to multipart
    @SuppressLint("Range")
    private fun Uri.asMultipart(name: String, contentResolver: ContentResolver): MultipartBody.Part?{
        return contentResolver.query(this, null, null, null, null)?.let {
            if (it.moveToNext()){
                val displayName = it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                val requestBody = object : RequestBody(){
                    override fun contentType(): MediaType? {
                        return contentResolver.getType(this@asMultipart)?.toMediaType()
                    }

                    @SuppressLint("Recycle")
                    override fun writeTo(sink: BufferedSink) {
                        sink.writeAll(contentResolver.openInputStream(this@asMultipart)?.source()!!)
                    }
                }
                it.close()
                MultipartBody.Part.createFormData(name, displayName, requestBody)
            } else{
                it.close()
                null
            }
        }
    }

    // DrawingFragment에서 생성된 이미지 삭제
    private fun delSavedImg(delImgUri: Uri){
        if (drawingUri != "".toUri()){
            val file = File(getPath(delImgUri))
            file.delete()
        }
    }

    // 빈 칸 유효성 검사
    private fun chkEmpty(): Boolean{
        if (binding.edittextFragmentCommunityRegisterTitle.text.toString().trim() == ""
            || binding.edittextFragmentCommunityRegisterContent.text.toString().trim() == ""){
            NotificationDialog(
                resources.getString(
                    R.string.fragment_community_register_chk_content
                )
            ).show(childFragmentManager, null)

            return false
        }
        return true
    }

    // 로딩화면 띄우고 없애기
    private fun changeLoadingState(state: Boolean){
        if(!state){
            loadingDialog.dismiss()
        } else{
            loadingDialog.show(mainActivity.supportFragmentManager, null)
        }
    }

    // 작성한 게시글 서버로 전송
    private fun sendBoard(){
        var multipart:MultipartBody.Part? = null

        if (drawingUri != "".toUri()){
            multipart = drawingUri.asMultipart("image", requireContext().contentResolver)!!
        } else if (galleryUri != "".toUri()){
            multipart = galleryUri.asMultipart("image", requireContext().contentResolver)!!
        }

        val boardData = CreateBoardData(
            binding.edittextFragmentCommunityRegisterTitle.text.toString()
            , binding.edittextFragmentCommunityRegisterContent.text.toString()
            , tabPosition
            , user.uid!!
        )

        communityService.createBoard(
            multipart,
            boardData
        ).enqueue(object : Callback<CreateBoardResponse>{
                override fun onResponse(
                    call: Call<CreateBoardResponse>,
                    response: Response<CreateBoardResponse>
                ) {
                    if (response.code() == 200){
                        delSavedImg(drawingUri)

                        mainActivity.popFragment()
                        actionBar.hide()
                    }
                    changeLoadingState(false)
                }

                override fun onFailure(call: Call<CreateBoardResponse>, t: Throwable) {
                    Log.d(TAG, "onFailure: ${t.message}")
                }
            }
        )
    }

    // 게시글 수정
    private fun updateBoard(){
        val updateBoardData = UpdateBoardData(
            binding.edittextFragmentCommunityRegisterTitle.text.toString(),
            binding.edittextFragmentCommunityRegisterContent.text.toString(),
            ""
        )
        
        communityService.updateBoard(boardItem!!.boardId, updateBoardData)
            .enqueue(object : Callback<UpdateBoardResponse>{
                override fun onResponse(
                    call: Call<UpdateBoardResponse>,
                    response: Response<UpdateBoardResponse>
                ) {
                    if (response.code() == 200){
                        delSavedImg(drawingUri)

                        mainActivity.popFragment()
                        actionBar.hide()
                    }
                    changeLoadingState(false)
                }

                override fun onFailure(call: Call<UpdateBoardResponse>, t: Throwable) {
                    Log.d(TAG, "onFailure: ${t.message}")
                }
            })
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.actionbar_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when(menuItem.itemId){
            R.id.actionbar_register -> {
                if (chkEmpty()){
                    changeLoadingState(true)

                    if (boardItem != null){
                        updateBoard()
                    } else{
                        CommunityBoardFragment.rViewItemPosition = 0
                        sendBoard()
                    }
                }
            }
            android.R.id.home -> {
                delSavedImg(drawingUri)
                mainActivity.popFragment()
                actionBar.hide()
            }
        }
        return true
    }

    // 액션바 설정
    private fun setActionBar(){
        actionBar = mainActivity.supportActionBar!!
        actionBar.title = ""
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setBackgroundDrawable(
            ContextCompat.getDrawable(requireContext(), R.color.background_light_green)
        )
        actionBar.show()

        requireActivity().addMenuProvider(
            this
            , viewLifecycleOwner
            , Lifecycle.State.RESUMED
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        actionBar.hide()
    }
}