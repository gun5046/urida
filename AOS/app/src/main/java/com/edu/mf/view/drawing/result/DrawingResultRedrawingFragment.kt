package com.edu.mf.view.drawing.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.edu.mf.databinding.DialogFragmentDrawingResultRedrawingBinding
import com.edu.mf.view.common.MainActivity
import com.edu.mf.view.drawing.DrawingFragment

class DrawingResultRedrawingFragment: Fragment() {
    private lateinit var binding:DialogFragmentDrawingResultRedrawingBinding
    private lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogFragmentDrawingResultRedrawingBinding.inflate(inflater, container, false)
        mainActivity = MainActivity.getInstance()!!

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonDialogFragmentDrawingResultRedrawing.setOnClickListener{
            mainActivity.changeFragment(DrawingFragment())
        }
    }
}