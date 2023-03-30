package com.edu.mf.view

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.edu.mf.R
import com.edu.mf.databinding.FragmentLanguageBinding
import com.edu.mf.databinding.FragmentMainBinding
import com.edu.mf.view.common.MainActivity
import com.edu.mf.view.community.CommunityFragment
import com.edu.mf.view.drawing.DrawingFragment
import com.edu.mf.view.picture.PictureFragment
import com.edu.mf.view.study.StudyFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainFragment: Fragment() {
    private lateinit var binding: FragmentMainBinding
    private lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        mainActivity = MainActivity.getInstance()!!
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(this).load(R.raw.charac).into(binding.imageviewChrac)

        chkPermissionDrawingFragment()

        binding.apply {
            cardviewStudy.setOnClickListener {
                mainActivity.addFragment(StudyFragment())
            }

            cardviewPicture.setOnClickListener {
                mainActivity.addFragment(PictureFragment())
            }

            cardviewCommunity.setOnClickListener {
                mainActivity.addFragment(CommunityFragment())
            }

            imageviewSettings.setOnClickListener {
                showSettingDialog()
            }
        }
    }

    // 그림으로 찾기 클릭 시 permission 체크
    private fun chkPermissionDrawingFragment(){
        val reqPermission = requestPermission()
        binding.cardviewDrawing.setOnClickListener {
            reqPermission.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
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

    fun changeLocale(language: Int){
        mainActivity.changeLocale(language)
        CoroutineScope(Dispatchers.IO).launch {
            mainActivity.loginService.languageSetting(language, mainActivity.user!!.uid!!)
        }
        mainActivity.changeFragment(MainFragment())
    }
}