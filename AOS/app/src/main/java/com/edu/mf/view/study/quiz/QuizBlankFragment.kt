package com.edu.mf.view.study.quiz

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.edu.mf.R
import com.edu.mf.databinding.FragmentQuizBlankBinding
import com.edu.mf.utils.App
import com.edu.mf.view.common.MainActivity
import com.edu.mf.viewmodel.MainViewModel

private const val TAG = "QuizBlankFragment_지훈"
class QuizBlankFragment : Fragment() {
    private lateinit var binding : FragmentQuizBlankBinding
    private lateinit var viewModel : MainViewModel
    private lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_quiz_blank,container,false)
        init()
        return  binding.root
    }

    private fun init(){
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        mainActivity = MainActivity.getInstance()!!

        viewModel.selectedProblem.observe(viewLifecycleOwner, Observer {
            viewModel.setQuiz()
        })
        viewModel.getProblem()
        viewModel.setTTS()
        disableBackPress()
        binding.apply{
            datas = App.PICTURES
            handlers = this@QuizBlankFragment
            vm = viewModel
            lifecycleOwner = this@QuizBlankFragment
        }

    }
    fun onProblemClick(index:Int){
        val dialog = QuizResultDialog(index,2)
        dialog.isCancelable = false
        dialog.show(activity?.supportFragmentManager!!,"QuizResultDialog")
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
    fun onBackPressed(){
        mainActivity.popQuizFragment("blank")
        mainActivity.popFragment()
    }
}