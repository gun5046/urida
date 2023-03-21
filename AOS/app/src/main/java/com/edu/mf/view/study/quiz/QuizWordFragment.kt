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
import com.edu.mf.databinding.FragmentQuizBinding
import com.edu.mf.databinding.FragmentQuizWordBinding
import com.edu.mf.view.common.MainActivity
import com.edu.mf.viewmodel.MainViewModel

private const val TAG = "QuizWordFragment_지훈"
class QuizWordFragment : Fragment() {

    private lateinit var binding : FragmentQuizWordBinding
    private lateinit var viewModel : MainViewModel
    private lateinit var mainActivity: MainActivity
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_quiz_word,container,false)
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        mainActivity = MainActivity.getInstance()!!
        binding.apply {
            vm = viewModel
            handlers = this@QuizWordFragment
            lifecycleOwner = this@QuizWordFragment
        }
        viewModel.setWordQuiz()
        viewModel.setTTS()
        disableBackPress()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.quiz.observe(viewLifecycleOwner, Observer {
            binding.problems = it.problems
            Log.i(TAG, "onCreateView: ${it}")
            binding.imageviewFragmentQuizWordImage.setImageResource(resources.getIdentifier(
                "pictures_${viewModel.selectedCategory}_${it.answer_i}","drawable",requireActivity().packageName))
        })

    }

    fun onBackPressed(){
        mainActivity.popQuizFragment()
        mainActivity.popFragment()
    }
    /**
     * onBackPressed 막기
     */
    private fun disableBackPress(){
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner,object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
            }
        })
    }
    fun onOneClick(){
        val dialog = QuizResultDialog(binding.textviewFragmentQuizOne.text.toString())
        dialog.isCancelable = false
        dialog.show(activity?.supportFragmentManager!!,"CreateSelectCategoryDialog")
    }
    fun onTwoClick(){
        val dialog = QuizResultDialog(binding.textviewFragmentQuizTwo.text.toString())
        dialog.isCancelable = false
        dialog.show(activity?.supportFragmentManager!!,"CreateSelectCategoryDialog")
    }
    fun onThreeClick(){
        val dialog = QuizResultDialog(binding.textviewFragmentQuizThree.text.toString())
        dialog.isCancelable = false
        dialog.show(activity?.supportFragmentManager!!,"CreateSelectCategoryDialog")
    }
    fun onFourClick(){
        val dialog = QuizResultDialog(binding.textviewFragmentQuizFour.text.toString())
        dialog.isCancelable = false
        dialog.show(activity?.supportFragmentManager!!,"CreateSelectCategoryDialog")
    }
}