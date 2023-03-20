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
import com.edu.mf.viewmodel.MainViewModel

class DrawingResultFragment(
    private val drawingResponse: DrawingResponse
): Fragment() {
    private lateinit var binding: FragmentDrawingResultBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var drawingViewModel: DrawingViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDrawingResultBinding.inflate(inflater, container, false)
        mainViewModel = MainViewModel()
        drawingViewModel = DrawingViewModel()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mainViewModel = mainViewModel
        binding.drawingViewModel = drawingViewModel

        drawingViewModel.setDrawingResponse(drawingResponse)
        getImgIdx(drawingViewModel)

        setMainViewModel(0, mainViewModel, drawingViewModel)
    }

    // mainViewModel TTS 설정
    fun setMainViewModel(
        idx: Int
        , mainViewModel: MainViewModel
        , drawingViewModel: DrawingViewModel
    ){
        mainViewModel.setTTS()

        val imgInfo = drawingViewModel.imgInfoList.value?.get(idx)!!
        mainViewModel.changeCategory(imgInfo.categoryIdx)
        mainViewModel.setCurrentIndex(imgInfo.pictureIdx)
    }

    // 결과로 받은 단어와 drawable의 이미지 매칭 위한 인덱스 찾기
    fun getImgIdx(_drawingViewModel: DrawingViewModel){
        val imgInfoList = arrayListOf<ImgInfo>()
        val pictures = App.PICTURES
        val categorySize = 7

        val existImg = BooleanArray(2)
        for (i in 0 until categorySize){
            val imageSize = pictures[i].size

            for (j in 0 until imageSize){
                if (pictures[i][j] == drawingResponse.firstPrediction){
                    imgInfoList.add(0, ImgInfo(i, j))
                    existImg[0] = true
                } else if (pictures[i][j] == drawingResponse.secondPrediction){
                    imgInfoList.add(ImgInfo(i, j))
                    existImg[1] = true
                }

                if (imgInfoList.size == 2) {
                    _drawingViewModel.setImgInfoList(imgInfoList)
                    return
                }
            }
        }

        // 이미지 없을 시 대체 이미지 띄우기
        if(
            drawingResponse.predictionType == 0
            && imgInfoList.size < 1 ||
            drawingResponse.predictionType == 1
            && imgInfoList.size < 2
        ){
            if (!existImg[0]){
                imgInfoList.add(0, ImgInfo(9, 9))
            }
            if (!existImg[1]){
                imgInfoList.add(1, ImgInfo(9, 9))
            }
        }

        _drawingViewModel.setImgInfoList(imgInfoList)
    }

    data class ImgInfo(
        val categoryIdx: Int,
        val pictureIdx: Int
    )
}