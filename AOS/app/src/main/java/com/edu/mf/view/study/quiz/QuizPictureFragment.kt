package com.edu.mf.view.study.quiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.edu.mf.R
import com.edu.mf.databinding.FragmentQuizPictureBinding


class QuizPictureFragment : Fragment() {

    private lateinit var binding : FragmentQuizPictureBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_quiz_picture,container,false)
        return binding.root
    }


}