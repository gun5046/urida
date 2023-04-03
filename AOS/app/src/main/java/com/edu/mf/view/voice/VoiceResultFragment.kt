package com.edu.mf.view.voice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.edu.mf.R
import com.edu.mf.databinding.FragmentVoiceResultBinding
import com.edu.mf.utils.App
import com.edu.mf.view.common.MainActivity
import com.edu.mf.viewmodel.MainViewModel
import com.edu.mf.viewmodel.VoiceViewModel


class VoiceResultFragment : Fragment() {

    private lateinit var binding: FragmentVoiceResultBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var voiceViewModel: VoiceViewModel
    private lateinit var mainActivity: MainActivity
    private var hasResult = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = MainActivity.getInstance()!!
        binding = FragmentVoiceResultBinding.inflate(inflater, container, false)
        voiceViewModel = ViewModelProvider(requireActivity())[VoiceViewModel::class.java]
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        mainViewModel.setTTS()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mainViewModel = mainViewModel
        binding.voiceResultWord = voiceViewModel.word
        val pictures = App.PICTURES
        for (i in 0 until pictures.size){
            for(j in 0 until pictures[i].size){
                if(pictures[i][j].equals(voiceViewModel.word)){
                    binding.categoryIdx = i
                    mainViewModel.changeCategory(i)
                    binding.pictureIdx = j
                    mainViewModel.setCurrentIndex(j)
                    hasResult = true
                }
            }
        }
        binding.buttonFragmentVoiceResultMain.setOnClickListener {
            mainActivity.popFragment()
            mainActivity.popFragment()
        }
        binding.buttonFragmentVoiceResultRedrawing.setOnClickListener {
            mainActivity.popFragment()
        }

        if(hasResult){
            binding.constraintlayoutVoiceResultEmpty.visibility = View.GONE
            binding.constraintlayoutFragmentVoiceResult.visibility = View.VISIBLE
        } else {
            binding.constraintlayoutVoiceResultEmpty.visibility = View.VISIBLE
            binding.constraintlayoutFragmentVoiceResult.visibility = View.GONE
        }
    }
}