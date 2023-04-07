package com.edu.mf.view.study.quiz

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.edu.mf.R
import com.edu.mf.databinding.DialogFragmentLearnSelectProblemBinding
import com.edu.mf.repository.model.study.PCategory
import com.edu.mf.utils.App
import com.edu.mf.view.common.MainActivity
import com.edu.mf.view.study.learn.LearnDialogAdapter
import com.edu.mf.viewmodel.MainViewModel

class QuizSelectCategoryDialog(
    createSelectProblemDialogListener: CreateSelectProblemDialogListener
) : DialogFragment(){
    private lateinit var mainActivity: MainActivity
    private lateinit var viewModel: MainViewModel
    private var _binding: DialogFragmentLearnSelectProblemBinding? = null
    private val binding get() = _binding!!
    private var createSelectProblemDialog: CreateSelectProblemDialogListener?=null
    private var categories = App.pCategories
    private lateinit var dialogAdapter: LearnDialogAdapter
    init{
        this.createSelectProblemDialog = createSelectProblemDialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate<DialogFragmentLearnSelectProblemBinding>(
            inflater,R.layout.dialog_fragment_learn_select_problem,container,false)
        val view = binding.root
        init()
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        mainActivity = MainActivity.getInstance()!!
        binding.apply {
            handlers = this@QuizSelectCategoryDialog
            lifecycleOwner = this@QuizSelectCategoryDialog
            vm = viewModel
        }
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return view
    }
    private fun init(){
        setFullScreen()
        setAdapter()
    }
    fun noListener(){
        dismiss()
    }
    fun okListener(){
        createSelectProblemDialog?.onOkButtonClick()
        dismiss()
        mainActivity.addFragment(
            when(viewModel.selectedPCategory.value!!){
                0-> QuizWordFragment()
                1-> QuizPictureFragment()
                2-> QuizBlankFragment()
                else -> QuizRelateFragment()
            }
        )
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    interface CreateSelectProblemDialogListener{
        fun onOkButtonClick()
    }

    fun setFullScreen(){
        val flag = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        dialog?.window?.decorView?.systemUiVisibility = flag
    }
}