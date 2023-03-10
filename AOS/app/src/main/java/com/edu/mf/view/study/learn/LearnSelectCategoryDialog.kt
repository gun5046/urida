package com.edu.mf.view.study.learn

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.edu.mf.R
import com.edu.mf.databinding.DialogFragmentLearnSelectProblemBinding
import com.edu.mf.repository.model.study.PCategory

class LearnSelectCategoryDialog(
    createSelectProblemDialogListener: CreateSelectProblemDialogListener
) : DialogFragment(){

    private var _binding: DialogFragmentLearnSelectProblemBinding? = null
    private val binding get() = _binding!!
    private var createSelectProblemDialog:CreateSelectProblemDialogListener?=null
    private var categories = ArrayList<PCategory>()
    private lateinit var dialogAdapter: LearnDialogAdapter
    init{
        this.createSelectProblemDialog = createSelectProblemDialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogFragmentLearnSelectProblemBinding.inflate(inflater,container,false)
        val view = binding.root
        init()

        binding.buttonDialogLearnCancle.setOnClickListener {
            dismiss()
        }
        binding.buttonDialogLearnOk.setOnClickListener {
            createSelectProblemDialog?.onOkButtonClick()
        }


        return view
    }
    private fun init(){
        setFullScreen()
        setCategories()
        setAdapter()
    }

    private fun setAdapter(){
        binding.recyclerviewDialogLearnSelectProblem.apply {
            dialogAdapter = LearnDialogAdapter()
            dialogAdapter.setOnDialogClickListener(object : LearnDialogAdapter.OnClickLearnDialogListener{
                override fun onClick(view: View, position: Int) {
                    view.setBackgroundResource(R.drawable.learn_list_gray_10)
                }

            })
            dialogAdapter.setData(categories)
            adapter = dialogAdapter
        }
    }

    private fun setCategories(){
        categories.add(PCategory("그림보고 낱말 맞추기","그림을 보고 어떤 단어인지 맞춰 보아요"))
        categories.add(PCategory("낱말보고 그림 맞추기","주어진 단어를 보고 어떤 그림인지 맞춰 보아요"))
        categories.add(PCategory("빈칸에 알맞는 말 넣기","주어진 그림과 문장을 보고 어떤 단어가 들어가야 할지 맞춰 보아요"))
        categories.add(PCategory("연관된 단어 맞추기","제시된 단어들과 연관된 단어를 골라 보아요"))
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