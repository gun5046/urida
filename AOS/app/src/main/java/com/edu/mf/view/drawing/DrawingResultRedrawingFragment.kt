package com.edu.mf.view.drawing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.edu.mf.databinding.FragmentDrawingResultRedrawingBinding
import com.edu.mf.utils.App
import com.edu.mf.view.common.MainActivity

class DrawingResultRedrawingFragment: Fragment() {
    private lateinit var binding:FragmentDrawingResultRedrawingBinding
    private lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDrawingResultRedrawingBinding.inflate(inflater, container, false)
        mainActivity = MainActivity.getInstance()!!

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFragmentDrawingResultRedrawing.setOnClickListener{
            mainActivity.changeFragment(DrawingFragment())
        }
    }
}