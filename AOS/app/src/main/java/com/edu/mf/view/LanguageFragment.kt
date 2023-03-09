package com.edu.mf.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.edu.mf.R
import com.edu.mf.databinding.DialogNicknameBinding
import com.edu.mf.databinding.FragmentLanguageBinding
import com.edu.mf.view.common.MainActivity
import com.google.android.material.bottomsheet.BottomSheetDialog


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
            showBottomDialog()
        }
        binding.layoutEnglish.setOnClickListener {
            showBottomDialog()
        }
        binding.layoutChinese.setOnClickListener {
            showBottomDialog()
        }
        binding.layoutVietnam.setOnClickListener {
            showBottomDialog()
        }
    }

    fun showBottomDialog(){
        var binding = DialogNicknameBinding.inflate(layoutInflater)
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(binding.root)
        binding.buttonNickname.setOnClickListener {
            mainActivity.popFragment()
            mainActivity.changeFragment(MainFragment())
            bottomSheetDialog.dismiss()
        }
        bottomSheetDialog.show()
    }
}