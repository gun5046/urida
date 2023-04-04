package com.edu.mf.view.study.quiz

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.edu.mf.R
import com.edu.mf.databinding.FragmentQuizSelectBinding
import com.edu.mf.databinding.FragmentQuizSelectCategoryBinding
import com.edu.mf.repository.model.study.PCategory
import com.edu.mf.utils.App
import com.edu.mf.view.common.MainActivity
import com.edu.mf.view.study.learn.LearnDialogAdapter
import com.edu.mf.viewmodel.MainViewModel


class QuizSelectCategoryFragment : Fragment() {
    private lateinit var binding : FragmentQuizSelectCategoryBinding
    private lateinit var viewModel : MainViewModel
    private var categories = App.pCategories
    private lateinit var dialogAdapter: LearnDialogAdapter
    private lateinit var mainActivity: MainActivity
    private var nextFlag : Boolean = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_quiz_select_category,container,false)
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        binding.apply {
            handlers = this@QuizSelectCategoryFragment
            lifecycleOwner = this@QuizSelectCategoryFragment
            vm = viewModel
        }
        mainActivity = MainActivity.getInstance()!!
        init()
        return binding.root
    }
    private fun init(){
        setAdapter()
    }
    private fun setAdapter(){
        binding.recyclerviewDialogLearnSelectProblem.apply {
            dialogAdapter = LearnDialogAdapter()
            dialogAdapter.setOnDialogClickListener(object :
                LearnDialogAdapter.OnClickLearnDialogListener {
                override fun onClick(view: View, position: Int) {
                    for(i in 0..3){
                        when(i){
                            position->{
                                viewModel.changePCategory(position)
                                categories[i].isClicked = true
                                nextFlag = true
                            }
                            else->{
                                categories[i].isClicked = false
                            }
                        }
                    }
                    dialogAdapter.notifyDataSetChanged()
                }

            })
            dialogAdapter.setData(categories)
            adapter = dialogAdapter
        }
    }
    fun clickOkButton(){
        if(nextFlag){
            mainActivity.addFragment(
                when(viewModel.selectedPCategory.value!!){
                    0-> QuizWordFragment()
                    1-> QuizPictureFragment()
                    2-> QuizBlankFragment()
                    else -> QuizRelateFragment()
                }
            )
            clickBackButton()
        }else{
            val tv = TextView(requireContext())
            tv.text = resources.getString(R.string.fragment_quiz_select_category_empty)
            tv.setTextColor(Color.parseColor("#FF0000"))
            var toast = Toast.makeText(requireContext(),"",Toast.LENGTH_SHORT)
            toast.view = tv
            toast.show()
        }

    }

    fun clickBackButton(){
        parentFragmentManager.popBackStack()
    }


}