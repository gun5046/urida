package com.edu.mf.view.study

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.edu.mf.databinding.FragmentStudyBinding
import com.edu.mf.view.common.MainActivity
import com.edu.mf.view.study.learn.LearnFragment
import com.edu.mf.view.study.quiz.QuizFragment
import com.edu.mf.view.study.reslove.ResolveFragment
import com.edu.mf.viewmodel.MainViewModel

class StudyFragment: Fragment() {
    private lateinit var binding: FragmentStudyBinding
    private lateinit var mainActivity: MainActivity
    private lateinit var viewModel : MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStudyBinding.inflate(inflater, container, false)
        mainActivity = MainActivity.getInstance()!!
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            cardviewLearn.setOnClickListener {
                viewModel.setMode(1)
                mainActivity.addFragment(LearnFragment())
            }

            cardviewQuiz.setOnClickListener {
                viewModel.setMode(2)
                mainActivity.addFragment(QuizFragment())
            }

            cardviewResolve.setOnClickListener {
                mainActivity.addFragment(ResolveFragment())
            }
        }
    }
}