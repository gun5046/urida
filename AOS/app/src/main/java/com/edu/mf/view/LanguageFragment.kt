package com.edu.mf.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.edu.mf.R
import com.edu.mf.databinding.DialogNicknameBinding
import com.edu.mf.databinding.FragmentLanguageBinding
import com.edu.mf.utils.App
import com.edu.mf.view.common.MainActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.Locale


class LanguageFragment : Fragment() {

    private lateinit var binding: FragmentLanguageBinding
    private lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLanguageBinding.inflate(inflater, container, false)
        mainActivity = MainActivity.getInstance()!!
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.layoutKorean.setOnClickListener {
            mainActivity.changeLocale(0)
            App.sharedPreferencesUtil.setLanguageCode(0)
            showBottomDialog()
        }
        binding.layoutChinese.setOnClickListener {
            mainActivity.changeLocale(1)
            App.sharedPreferencesUtil.setLanguageCode(1)
            showBottomDialog()
        }
        binding.layoutVietnam.setOnClickListener {
            mainActivity.changeLocale(2)
            App.sharedPreferencesUtil.setLanguageCode(2)
            showBottomDialog()
        }
        binding.layoutEnglish.setOnClickListener {
            mainActivity.changeLocale(3)
            App.sharedPreferencesUtil.setLanguageCode(3)
            showBottomDialog()
        }
    }

    fun showBottomDialog(){
        var binding = DialogNicknameBinding.inflate(layoutInflater)
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(binding.root)
        binding.buttonNickname.setOnClickListener {
            if(Regex("[a-zA-Z0-9]{1,20}").matches(binding.edittextNickname.text)){//정규표현식 알파벳 소문자, 대문자, 숫자만 허용
                mainActivity.popFragment()
                mainActivity.changeFragment(MainFragment())
                bottomSheetDialog.dismiss()
            }
        }
        bottomSheetDialog.show()
    }
}