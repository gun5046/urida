package com.edu.mf.view.community

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.ActionBar
import android.os.Bundle
import android.provider.MediaStore.Images
import android.provider.Settings
import android.util.Log
import android.view.*
import android.widget.Toast
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
import com.edu.mf.repository.model.community.CreateBoardData
import com.edu.mf.repository.model.community.CreateBoardResponse
import com.edu.mf.utils.App
import com.edu.mf.utils.SharedPreferencesUtil
import com.edu.mf.view.common.MainActivity
import com.edu.mf.view.drawing.result.DrawingResultShareDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

private const val TAG = "CommunityRegisterFragme"
class CommunityRegisterFragment: Fragment(), MenuProvider {
    private lateinit var binding: FragmentCommunityRegisterBinding
    private lateinit var mainActivity: MainActivity
    private lateinit var communityService: CommunityService
    private lateinit var user: User

    private lateinit var actionBar: ActionBar
    private lateinit var drawingUri: Uri
    private var categoryId = 0

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
    }

    // 그림 uri가 있는지 체크
    private fun chkDrawingUri(){
        drawingUri = DrawingResultShareDialog.savedDrawingUri
        if (drawingUri != "".toUri()){
            binding.imageviewFragmentCommunityRegister.background =
                ContextCompat.getDrawable(requireContext(), R.color.white)
            setImg(drawingUri)

            DrawingResultShareDialog.savedDrawingUri = "".toUri()
            categoryId = 1
        }
    }

    // cardview 클릭 시 permission 체크
    private fun chkPermissionGallery(){
        val reqPermission = requestPermission()
        binding.cardviewFragmentCommunityRegister.setOnClickListener {
            reqPermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    // 미디어 접근권한
    private fun requestPermission(): ActivityResultLauncher<String> {
        return registerForActivityResult(ActivityResultContracts.RequestPermission()){
            if (it){
                openGallery()
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

    private fun setImg(uri: Uri){
        binding.imageviewFragmentCommunityRegister.setImageURI(uri)
        binding.imageviewFragmentCommunityRegisterPlus.visibility = View.GONE
    }

    // DrawingFragment에서 생성된 이미지 삭제
    private fun delSavedImg(delImgUri: Uri){
        if (drawingUri != "".toUri()){
            val file = File(getPath(delImgUri))
            file.delete()
        }
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

    // 빈 칸 유효성 검사
    private fun chkEmpty(): Boolean{
        if (binding.edittextFragmentCommunityRegisterTitle.text.toString() == ""
            || binding.edittextFragmentCommunityRegisterContent.text.toString() == ""){
            Toast.makeText(requireContext(), "빈 칸을 모두 입력해주세요", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    // 작성한 게시글 서버로 전송
    private fun sendBoard(){
        val boardData = CreateBoardData(
            binding.edittextFragmentCommunityRegisterTitle.text.toString()
            , binding.edittextFragmentCommunityRegisterContent.text.toString()
            , categoryId
            , user.uid!!
        )
        communityService.createBoard(boardData)
            .enqueue(object : Callback<CreateBoardResponse>{
                override fun onResponse(
                    call: Call<CreateBoardResponse>,
                    response: Response<CreateBoardResponse>
                ) {
                    if (response.code() == 200){
                        val body = response.body()!!

                    }
                }

                override fun onFailure(call: Call<CreateBoardResponse>, t: Throwable) {
                    Log.d(TAG, "onFailure: ${t.message}")
                }
            }
        )
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

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.actionbar_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when(menuItem.itemId){
            R.id.actionbar_register -> {
                if (chkEmpty()){
                    sendBoard()
                    delSavedImg(drawingUri)
                    mainActivity.popFragment()
                    actionBar.hide()
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

    override fun onDestroyView() {
        super.onDestroyView()
        actionBar.hide()
    }
}