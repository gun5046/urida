package com.edu.mf.view.study.learn

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.edu.mf.R
import com.edu.mf.databinding.FragmentLearnBinding
import com.edu.mf.utils.App
import com.edu.mf.view.common.MainActivity
import com.edu.mf.viewmodel.MainViewModel


class LearnFragment: Fragment(){
    private val TAG = "LearnFragment_지훈"
    private lateinit var viewModel: MainViewModel
    private lateinit var learnAdapter: LearnAdapter
    private lateinit var binding:FragmentLearnBinding
    private lateinit var mainActivity: MainActivity
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_learn,container,false)
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        mainActivity = MainActivity.getInstance()!!
        Log.i(TAG, "onCreateView: ${viewModel.mode}")
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
        setAdapter()
    }


    /**
     * adapter 초기화
     */
    private fun setAdapter(){
        learnAdapter = LearnAdapter()
        learnAdapter.setFlag(1)
        learnAdapter.setList(App.categories)
        binding.recyclerviewFragmentLearnList.apply{
            layoutManager = LinearLayoutManager(context)
            adapter = learnAdapter
        }
        learnAdapter.setOnCategoryClickListener(object : LearnAdapter.CategoryClickListener{
            override fun onClick(view: View, position: Int) {
                viewModel.changeCategory(position)
                mainActivity.addFragment(LearnMainFragment())
            }
        })
    }




}