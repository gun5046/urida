package com.edu.mf.view.study.quiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.edu.mf.R
import com.edu.mf.databinding.FragmentQuizBlankBinding


class QuizBlankFragment : Fragment() {
    private lateinit var binding : FragmentQuizBlankBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_quiz_blank,container,false)

        return  binding.root
    }


}