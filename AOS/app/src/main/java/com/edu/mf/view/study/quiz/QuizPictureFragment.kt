package com.edu.mf.view.study.quiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.edu.mf.R
import com.edu.mf.databinding.FragmentQuizPictureBinding
import com.edu.mf.utils.App
import com.edu.mf.view.common.MainActivity
import com.edu.mf.viewmodel.MainViewModel


class QuizPictureFragment : Fragment() {

    private lateinit var binding : FragmentQuizPictureBinding
    private lateinit var viewModel : MainViewModel
    private lateinit var mainActivity: MainActivity


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_quiz_picture,container,false)
        init()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun init(){
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        mainActivity = MainActivity.getInstance()!!
        viewModel.setWordQuiz()
        viewModel.setTTS()
        //mainActivity.disableBackPress()

        binding.apply{
            datas = App.PICTURES
            vm = viewModel
            lifecycleOwner = this@QuizPictureFragment
        }
    }
    fun onBackPressed(){
        mainActivity.popQuizFragment("picture")
        mainActivity.popFragment()
    }


}