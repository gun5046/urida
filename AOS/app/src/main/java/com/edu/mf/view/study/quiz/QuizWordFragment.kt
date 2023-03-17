package com.edu.mf.view.study.quiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.edu.mf.R
import com.edu.mf.databinding.FragmentQuizBinding
import com.edu.mf.databinding.FragmentQuizWordBinding
import com.edu.mf.view.common.MainActivity
import com.edu.mf.viewmodel.MainViewModel


class QuizWordFragment : Fragment() {

    private lateinit var binding : FragmentQuizWordBinding
    private lateinit var viewModel : MainViewModel
    private lateinit var mainActivity: MainActivity
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_quiz_word,container,false)
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        mainActivity = MainActivity.getInstance()!!
        binding.apply {
            vm = viewModel
            handlers = this@QuizWordFragment
            lifecycleOwner = this@QuizWordFragment
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
    fun nextQuiz(){
        mainActivity.addFragment(QuizWordFragment())
    }

}