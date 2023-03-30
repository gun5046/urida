package com.edu.mf.view.study.quiz

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.edu.mf.R
import com.edu.mf.databinding.FragmentQuizRelateBinding
import com.edu.mf.utils.App
import com.edu.mf.view.common.MainActivity
import com.edu.mf.viewmodel.MainViewModel

private const val TAG = "QuizRelateFragment_지훈"
class QuizRelateFragment : Fragment() {
    private lateinit var binding : FragmentQuizRelateBinding
    private lateinit var mainActivity: MainActivity
    private lateinit var viewModel : MainViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_quiz_relate,container,false)
        init()
        return binding.root
    }

    private fun init(){
        mainActivity = MainActivity.getInstance()!!
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        binding.apply{
            vm = viewModel
            handlers = this@QuizRelateFragment
            datas = App.PICTURES
            lifecycleOwner = this@QuizRelateFragment
        }
        if(viewModel.resolveMode)viewModel.setResolveQuiz()
        else viewModel.setQuiz()
        viewModel.setTTS()
        disableBackPress()
    }
    fun onProblemClick(index:Int){
        val dialog = QuizResultDialog(index,3)
        dialog.isCancelable = false
        dialog.show(activity?.supportFragmentManager!!,"QuizResultDialog")
    }
    fun onBackPressed(){
        mainActivity.popQuizFragment("relate")
        mainActivity.popFragment()
    }


    /**
     * onBackPressed 막기
     */
    fun disableBackPress(){
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner,object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {

            }

        })
    }
}