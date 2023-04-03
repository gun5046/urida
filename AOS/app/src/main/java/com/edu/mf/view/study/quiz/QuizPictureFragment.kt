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
import com.edu.mf.R
import com.edu.mf.databinding.FragmentQuizPictureBinding
import com.edu.mf.utils.App
import com.edu.mf.view.common.MainActivity
import com.edu.mf.viewmodel.MainViewModel


private const val TAG = "QuizPictureFragment_지훈"
class QuizPictureFragment : Fragment() {

    private lateinit var binding : FragmentQuizPictureBinding
    private lateinit var viewModel : MainViewModel
    private lateinit var mainActivity: MainActivity


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_quiz_picture,container,false)
        init()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun init(){
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        mainActivity = MainActivity.getInstance()!!
        if(viewModel.resolveMode)viewModel.setResolveQuiz()
        else viewModel.setQuiz()

        viewModel.setTTS()
        binding.apply{
            datas = App.PICTURES
            handlers = this@QuizPictureFragment
            vm = viewModel
            lifecycleOwner = this@QuizPictureFragment
        }
        //disableBackPress()
    }
    fun onBackPressed(){
        mainActivity.popQuizFragment("picture")
        mainActivity.popFragment()
    }
    fun onProblemClick(index:Int){
        val dialog = QuizResultDialog(index,1)
        dialog.isCancelable = false
        dialog.show(activity?.supportFragmentManager!!,"QuizResultDialog")
    }
    override fun onStop() {
        super.onStop()
        viewModel.stopTTS()
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