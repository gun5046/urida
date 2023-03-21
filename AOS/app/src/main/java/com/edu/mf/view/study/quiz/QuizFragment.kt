package com.edu.mf.view.study.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.edu.mf.R
import com.edu.mf.databinding.FragmentQuizBinding
import com.edu.mf.utils.App
import com.edu.mf.view.common.MainActivity
import com.edu.mf.view.study.learn.LearnAdapter
import com.edu.mf.viewmodel.MainViewModel

class QuizFragment: Fragment(), QuizSelectCategoryDialog.CreateSelectProblemDialogListener{
    private lateinit var learnAdapter: LearnAdapter
    private lateinit var binding: FragmentQuizBinding
    private lateinit var viewModel : MainViewModel
    private lateinit var mainActivity: MainActivity
    private val TAG = "QuizFragment_지훈"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_quiz,container,false)
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        mainActivity = MainActivity.getInstance()!!

        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }
    private fun init(){
        setAdapter()
    }

    private fun setAdapter(){
        learnAdapter = LearnAdapter()
        learnAdapter.setFlag(2)
        learnAdapter.setList(App.categories)
        binding.recyclerviewFragmentQuizList.apply{
            layoutManager = LinearLayoutManager(context)
            adapter = learnAdapter
        }
        learnAdapter.setOnCategoryClickListener(object : LearnAdapter.CategoryClickListener{
            override fun onClick(view: View, position: Int) {
                viewModel.changeCategory(position)
                val dialog = QuizSelectCategoryDialog(this@QuizFragment)
                dialog.isCancelable = false
                dialog.show(activity?.supportFragmentManager!!,"CreateSelectCategoryDialog")
            }
        })
    }

    override fun onOkButtonClick() {

    }


}