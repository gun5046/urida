package com.edu.mf.view.study.learn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.edu.mf.R
import com.edu.mf.databinding.FragmentLearnBinding


class LearnFragment: Fragment() {
    private var categories = arrayListOf<String>()
    private lateinit var learnAdapter: LearnAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding:FragmentLearnBinding =
            DataBindingUtil.inflate(inflater,R.layout.fragment_learn,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    /**
     * 초기화 데이터
     */
    private fun init(){
        setCategoryData()
    }
    /**
     * category 데이터 입력
     */
    private fun setCategoryData(){
        categories.add("과일/채소")
        categories.add("직업")
        categories.add("동물")
        categories.add("물체")
        categories.add("장소")
        categories.add("행동")
    }

    /**
     * adapter 초기화
     */
    private fun setAdapter(){
        learnAdapter.setList(categories)
        binding
    }
}