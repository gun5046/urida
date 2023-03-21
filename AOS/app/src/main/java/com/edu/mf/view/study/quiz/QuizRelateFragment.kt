package com.edu.mf.view.study.quiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.edu.mf.R
import com.edu.mf.databinding.FragmentQuizRelateBinding


class QuizRelateFragment : Fragment() {
    private lateinit var binding : FragmentQuizRelateBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_quiz_relate,container,false)

        return binding.root
    }


}