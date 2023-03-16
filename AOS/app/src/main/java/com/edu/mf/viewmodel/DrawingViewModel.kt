package com.edu.mf.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edu.mf.repository.model.drawing.DrawingResponse
import com.edu.mf.view.drawing.DrawingResultFragment.ImgInfo
import kotlinx.coroutines.launch

class DrawingViewModel: ViewModel() {
    private val _drawingResponse = MutableLiveData<DrawingResponse>()
    val drawingResponse: LiveData<DrawingResponse> = _drawingResponse

    private val _imgInfoList = MutableLiveData<ArrayList<ImgInfo>>()
    val imgInfoList: LiveData<ArrayList<ImgInfo>> = _imgInfoList

    private val _firstImgName = MutableLiveData<String>()
    val firstImgName: LiveData<String> = _firstImgName

    private val _secondImgName = MutableLiveData<String>()
    val secondImgName: LiveData<String> = _secondImgName

    fun setDrawingResponse(drawingResponse: DrawingResponse){
        viewModelScope.launch {
            _drawingResponse.value = drawingResponse
        }
    }

    fun setImgInfoList(imgInfoList: ArrayList<ImgInfo>){
        viewModelScope.launch {
            println("### ${imgInfoList.toString()}")
            _imgInfoList.value = imgInfoList

            makeImgResName()
        }
    }

    private fun makeImgResName(){
        viewModelScope.launch {
            val firstVal = _imgInfoList.value!![0]
            _firstImgName.value = "pictures_${firstVal.categoryIdx}_${firstVal.pictureIdx}"

//        val secondVal = _imgInfoList.value!![1]
//        _secondImgName.value = "R.drawable.pictures_${secondVal.categoryIdx}_${secondVal.pictureIdx}"
        }
    }
}