package com.edu.mf.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.edu.mf.R
import com.edu.mf.databinding.DialogNicknameBinding
import com.edu.mf.databinding.FragmentLanguageBinding
import com.edu.mf.utils.App
import com.edu.mf.view.common.MainActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


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
            mainActivity.user!!.language = 0
            showBottomDialog()
        }
        binding.layoutChinese.setOnClickListener {
            mainActivity.changeLocale(1)
            mainActivity.user!!.language = 1
            showBottomDialog()
        }
        binding.layoutVietnam.setOnClickListener {
            mainActivity.changeLocale(2)
            mainActivity.user!!.language = 2
            showBottomDialog()
        }
        binding.layoutEnglish.setOnClickListener {
            mainActivity.changeLocale(3)
            mainActivity.user!!.language = 3
            showBottomDialog()
        }
    }

    fun showBottomDialog(){
        var binding = DialogNicknameBinding.inflate(layoutInflater)
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(binding.root)
        binding.buttonNickname.setOnClickListener {
            if(Regex("[a-zA-Z0-9]{1,20}").matches(binding.edittextNickname.text.toString())){//정규표현식 알파벳 소문자, 대문자, 숫자만 허용
                mainActivity.user!!.nickname = binding.edittextNickname.text.toString()
                CoroutineScope(Dispatchers.IO).launch {
                    val response = mainActivity.loginService.register(mainActivity.user!!)
                    if(response.isSuccessful){
                        mainActivity.user = response.body()
                        App.sharedPreferencesUtil.setUser(mainActivity.user!!)
                    }
                }
                mainActivity.popFragment()
                mainActivity.changeFragment(MainFragment())
                bottomSheetDialog.dismiss()
            }
        }
        binding.edittextNickname.addTextChangedListener {
            if(it!!.isBlank()){
                binding.textinputlayoutNickname.error = resources.getString(R.string.dialog_nickname_blank)
            } else if(Regex("[a-zA-Z0-9]{1,20}").matches(it!!.toString())){
                CoroutineScope(Dispatchers.IO).launch {
                    val response = mainActivity.loginService.duplicateCheck(binding.edittextNickname.text.toString())
                    if(response.isSuccessful){
                        if(response.body() == true){
                            requireActivity().runOnUiThread {
                                binding.textinputlayoutNickname.error = null
                            }
                        } else {
                            requireActivity().runOnUiThread {
                                binding.textinputlayoutNickname.error = resources.getString(R.string.dialog_nickname_duplicate)
                            }
                        }
                    }
                }
            } else {
                binding.textinputlayoutNickname.error = resources.getString(R.string.dialog_nickname_unallowed)
            }
        }
        bottomSheetDialog.show()
    }
}