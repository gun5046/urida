package com.edu.mf.view.drawing.result

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.edu.mf.databinding.FragmentDrawingResultRedrawingBinding
import com.edu.mf.view.MainFragment
import com.edu.mf.view.common.MainActivity
import com.edu.mf.view.drawing.DrawingFragment

class DrawingResultRedrawingDialog: DialogFragment() {
    private lateinit var binding: FragmentDrawingResultRedrawingBinding
    private lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDrawingResultRedrawingBinding.inflate(layoutInflater)
        mainActivity = MainActivity.getInstance()!!

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        isCancelable = false

        binding.buttonFragmentDrawingResultRedrawingMain.setOnClickListener {
            mainActivity.changeFragment(MainFragment())
        }

        binding.buttonFragmentDrawingResultRedrawing.setOnClickListener {
            mainActivity.changeFragment(DrawingFragment())
        }

        return binding.root
    }
}