package com.edu.mf.view.community

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.ActionBar
import android.os.Bundle
import android.provider.MediaStore.Images
import android.provider.Settings
import android.view.*
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.edu.mf.R
import com.edu.mf.databinding.FragmentCommunityRegisterBinding
import com.edu.mf.view.common.MainActivity

class CommunityRegisterFragment: Fragment(), MenuProvider {
    private lateinit var binding: FragmentCommunityRegisterBinding
    private lateinit var mainActivity: MainActivity

    private lateinit var actionBar: ActionBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCommunityRegisterBinding.inflate(inflater, container, false)
        mainActivity = MainActivity.getInstance()!!

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setActionBar()
        permissionChk()
    }

    // cardview 클릭 시 permission 체크
    private fun permissionChk(){
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
                mainActivity.popFragment()
                actionBar.hide()
            }
            android.R.id.home->{
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