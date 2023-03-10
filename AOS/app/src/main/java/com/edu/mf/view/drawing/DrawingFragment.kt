package com.edu.mf.view.drawing

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.edu.mf.R
import com.edu.mf.databinding.FragmentDrawingBinding
import com.edu.mf.view.common.MainActivity
import com.jaredrummler.android.colorpicker.ColorPickerDialog

class DrawingFragment: Fragment() {
    private lateinit var binding: FragmentDrawingBinding
    private lateinit var mainActivity: MainActivity

    companion object{
        lateinit var graphicView:GraphicView
        val colorPicker = ColorPickerDialog.newBuilder()!!
        var palette = mutableSetOf<Int>()//mutableListOf<Int>()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDrawingBinding.inflate(inflater, container, false)
        mainActivity = MainActivity.getInstance()!!
        graphicView = GraphicView(this.requireContext())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.constraintlayoutCanvas.addView(graphicView)
        graphicView.paint.color = Color.BLUE
        paletteInit()

        binding.imageviewEraser.setOnClickListener {
            graphicView.undo()
        }

        binding.imageview1.setOnClickListener {
            graphicView.paint.color = Color.RED
        }

        binding.imageview2.setOnClickListener {
            graphicView.paint.color = Color.BLUE
        }

        binding.imageview5.setOnClickListener {
            startActivity(Intent(context, ColorPickerActivity::class.java))
        }
    }

    // colorPicker palette 색상 초기화
    private fun paletteInit(){
        val colorList = resources.getIntArray(R.array.drawing_palette)
        for (color in colorList){
            palette.add(color)
        }
    }
}