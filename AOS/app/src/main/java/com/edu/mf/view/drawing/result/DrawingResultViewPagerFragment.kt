package com.edu.mf.view.drawing.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.edu.mf.databinding.FragmentDrawingResultViewpagerBinding
import com.edu.mf.repository.model.drawing.DrawingResponse
import com.edu.mf.viewmodel.DrawingViewModel

class DrawingResultViewPagerFragment(
    private val drawingResponse: DrawingResponse
): Fragment() {
    private lateinit var binding: FragmentDrawingResultViewpagerBinding
    private lateinit var drawingViewModel: DrawingViewModel
    private lateinit var resultWordList: ArrayList<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDrawingResultViewpagerBinding.inflate(inflater, container, false)
        drawingViewModel = DrawingViewModel()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.drawingViewModel = drawingViewModel

        drawingViewModel.setDrawingResponse(drawingResponse)
        DrawingResultFragment(drawingResponse).getImgIdx(drawingViewModel)

        val viewpager= binding.viewpagerFragmentDrawingResultViewpager
        viewpager.adapter = DrawingResultViewPagerItemFragment(
            requireActivity(), drawingViewModel, makeWordList(), drawingResponse
        )
        viewpager.pageMargin = 100

        val indicator = binding.circleindicatorFragmentDrawingResultViewpager
        indicator.createIndicators(2, 0)
        indicator.setViewPager(viewpager)
    }

    // make prediction list
    private fun makeWordList(): ArrayList<String>{
        resultWordList = arrayListOf()
        resultWordList.add(drawingResponse.firstPrediction)
        resultWordList.add(drawingResponse.secondPrediction)

        return resultWordList
    }
}