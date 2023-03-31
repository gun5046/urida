package com.edu.mf.view.study.reslove

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
import com.edu.mf.R
import com.edu.mf.databinding.DialogFragmentQuizResultBinding
import com.edu.mf.databinding.DialogFragmentResovleResultBinding

class ResolveAnswerDialog(
    text : String
) : DialogFragment(){

    private var _binding: DialogFragmentResovleResultBinding? = null
    private val binding get() = _binding!!
    private var text : String? = null

    init{
        this.text = text
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = DataBindingUtil.inflate(inflater,R.layout.dialog_fragment_resovle_result,container,false)
        val view = binding.root

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.apply {
            handlers = this@ResolveAnswerDialog
            lifecycleOwner = this@ResolveAnswerDialog
            answer = text
        }

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    fun onClick(){
        dismiss()
    }


    override fun onResume() {
        super.onResume()
        context?.dialogFragmentResize(this@ResolveAnswerDialog,0.9f,0.15f)
    }


    /**
     * Dialog Size 설정
     */

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