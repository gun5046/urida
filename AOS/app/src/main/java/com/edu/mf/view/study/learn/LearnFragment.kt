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
import com.edu.mf.viewmodel.MainViewModel


class LearnFragment: Fragment(),LearnSelectCategoryDialog.CreateSelectProblemDialogListener{
    private var categories = arrayListOf<String>()
    private val TAG = "LearnFragment_지훈"
    private lateinit var viewModel: MainViewModel
    private lateinit var learnAdapter: LearnAdapter
    private lateinit var binding:FragmentLearnBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_learn,container,false)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
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
        setAdapter()
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
        learnAdapter = LearnAdapter()
        learnAdapter.setList(categories)
        binding.recyclerviewFragmentLearnList.apply{
            layoutManager = LinearLayoutManager(context)
            adapter = learnAdapter
        }
        learnAdapter.setOnCategoryClickListener(object : LearnAdapter.CategoryClickListener{
            override fun onClick(view: View, position: Int) {
                viewModel.changeCategory(position)
                val dialog = LearnSelectCategoryDialog(this@LearnFragment)
                dialog.isCancelable = false
                dialog.show(activity?.supportFragmentManager!!,"CreateSelectCategoryDialog")
            }
        })
    }

    override fun onOkButtonClick() {

    }


}