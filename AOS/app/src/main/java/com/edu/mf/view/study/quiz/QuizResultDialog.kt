package com.edu.mf.view.study.quiz

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.edu.mf.R
import com.edu.mf.databinding.DialogFragmentQuizResultBinding
import com.edu.mf.view.common.MainActivity
import com.edu.mf.viewmodel.MainViewModel

class QuizResultDialog(
    answers : String
) : DialogFragment(){
    private lateinit var mainActivity: MainActivity
    private lateinit var viewModel: MainViewModel
    private var _binding: DialogFragmentQuizResultBinding? = null
    private val binding get() = _binding!!
    private var answers : String?= null
    init{
        this.answers = answers
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate<DialogFragmentQuizResultBinding>(inflater, R.layout.dialog_fragment_quiz_result,container,false)
        val view = binding.root

        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        mainActivity = MainActivity.getInstance()!!

        if(answers!!.substring(3,answers!!.length).equals(viewModel.quiz.value!!.answer_s)){
            binding.textviewDialogFragmentQuizTitle.text = "정답입니다"
        }else{
            binding.textviewDialogFragmentQuizTitle.text = "정답은 ${viewModel.quiz.value!!.answer_s} 입니다"
            binding.textviewDialogFragmentQuizTitle.setTextColor(Color.parseColor("#FFEB1635"))
        }

        binding.apply {
            handlers = this@QuizResultDialog
            lifecycleOwner = this@QuizResultDialog
            vm = viewModel
        }

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


        return view
    }
    override fun onResume() {
        super.onResume()
        context?.dialogFragmentResize(this@QuizResultDialog,0.9f,0.15f)
    }

    fun onCancleClick(){
        dismiss()
        mainActivity.popQuizFragment()
        mainActivity.popFragment()
    }
    fun onOkClick(){
        dismiss()
        mainActivity.addQuizFragment(QuizWordFragment())
    }

    @SuppressLint("ServiceCast")
    fun Context.dialogFragmentResize(dialogFragment: DialogFragment, width:Float, height:Float){
        val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager

        if(Build.VERSION.SDK_INT<30){
            val display = windowManager.defaultDisplay
            val size = Point()

            display.getSize(size)

            val window = dialogFragment.dialog?.window

            val x = (size.x*width).toInt()
            val y = (size.y*height).toInt()
            window?.setLayout(x,y)
        }else{
            val rect = windowManager.currentWindowMetrics.bounds

            val window = dialogFragment.dialog?.window

            val x = (rect.width() * width).toInt()
            val y = (rect.height() * height).toInt()

            window?.setLayout(x,y)

        }
}
}