package com.edu.mf.view.study

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.edu.mf.databinding.FragmentStudyBinding
import com.edu.mf.view.common.MainActivity
import com.edu.mf.view.study.learn.LearnFragment
import com.edu.mf.view.study.quiz.QuizFragment
import com.edu.mf.view.study.reslove.ResolveFragment

class StudyFragment: Fragment() {
    private lateinit var binding: FragmentStudyBinding
    private lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStudyBinding.inflate(inflater, container, false)
        mainActivity = MainActivity.getInstance()!!
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            cardviewFragmentStudyLearn.setOnClickListener {
                mainActivity.addFragment(LearnFragment())
            }

            cardviewFragmentStudyQuiz.setOnClickListener {
                mainActivity.addFragment(QuizFragment())
            }

            cardviewFragmentStudyResolve.setOnClickListener {
                mainActivity.addFragment(ResolveFragment())
            }
        }
    }
}