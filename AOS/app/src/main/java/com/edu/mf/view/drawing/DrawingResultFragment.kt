package com.edu.mf.view.drawing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.edu.mf.databinding.FragmentDrawingResultBinding
import com.edu.mf.repository.model.drawing.DrawingResponse
import com.edu.mf.utils.App
import com.edu.mf.viewmodel.DrawingViewModel

class DrawingResultFragment(
    private val drawingResponse: DrawingResponse
): Fragment() {
    private lateinit var binding: FragmentDrawingResultBinding
    private lateinit var drawingViewModel: DrawingViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDrawingResultBinding.inflate(inflater, container, false)
        drawingViewModel = DrawingViewModel()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.drawingViewModel = drawingViewModel

        drawingViewModel.setDrawingResponse(drawingResponse)
        getImgIdx()
    }

    fun getImgIdx(){
        drawingViewModel = DrawingViewModel()

        val imgInfoList = arrayListOf<ImgInfo>()
        val pictures = App.PICTURES
        val categorySize = 6

        for (i in 0 until categorySize){
            val imageSize = pictures[i].size

            for (j in 0 until imageSize){
                if (
                    pictures[i][j] == drawingResponse.firstPrediction
                    || pictures[i][j] == drawingResponse.secondPrediction
                ){
                    imgInfoList.add(ImgInfo(i, j))
                }

                if (imgInfoList.size == 2) {
                    drawingViewModel.setImgInfoList(imgInfoList)
                    return
                }
            }
        }
        drawingViewModel.setImgInfoList(imgInfoList)
    }

    data class ImgInfo(
        val categoryIdx: Int,
        val pictureIdx: Int
    )
}