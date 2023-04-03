package com.edu.mf.view.study.quiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.edu.mf.R
import com.edu.mf.databinding.FragmentQuizSelectBinding
import com.edu.mf.utils.App
import com.edu.mf.viewmodel.MainViewModel


class QuizSelectFragment : Fragment() {
    private lateinit var quizAdapter: QuizAdapter
    private lateinit var binding : FragmentQuizSelectBinding
    private lateinit var viewModel : MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_quiz_select,container,false)
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        init()
        return binding.root
    }

    private fun init(){
        setAdapter()
    }

    private fun setAdapter(){
        quizAdapter = QuizAdapter()
        quizAdapter.setList(App.categories)
        binding.recyclerviewFragmentQuizList.apply{
            layoutManager = LinearLayoutManager(context)
            adapter = quizAdapter
        }
        quizAdapter.setOnQuizClickListener(object : QuizAdapter.QuizClickListener{
            override fun onClick(view: View, position: Int) {
                viewModel.changeCategory(position)
                addFragment(QuizSelectCategoryFragment())
                /*val dialog = QuizSelectCategoryDialog(this@QuizFragment)
                dialog.isCancelable = false
                dialog.show(activity?.supportFragmentManager!!,"CreateSelectCategoryDialog")*/
            }
        })
    }
    fun addFragment(fragment: Fragment) {
        parentFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in_right,
                R.anim.slide_out_left,
                R.anim.slide_in_left,
                R.anim.slide_out_right
            )
            .replace(R.id.framelayout_container_quiz, fragment)
            .addToBackStack(null)
            .commit()
    }

}