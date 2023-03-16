package com.edu.mf.view.drawing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.edu.mf.databinding.FragmentDrawingResultListBinding
import com.edu.mf.repository.model.drawing.DrawingResponse
import com.edu.mf.viewmodel.DrawingViewModel

class DrawingResultListFragment(
    private val drawingResponse: DrawingResponse
): Fragment() {
    private lateinit var binding: FragmentDrawingResultListBinding
    private lateinit var drawingViewModel: DrawingViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDrawingResultListBinding.inflate(inflater, container, false)
        drawingViewModel = DrawingViewModel()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.drawingViewModel = drawingViewModel

        drawingViewModel.setDrawingResponse(drawingResponse)
        DrawingResultFragment(drawingResponse).getImgIdx(drawingViewModel)
    }
}