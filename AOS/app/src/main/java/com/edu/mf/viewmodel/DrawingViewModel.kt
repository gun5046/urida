package com.edu.mf.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edu.mf.repository.model.drawing.DrawingResponse
import com.edu.mf.view.common.Event
import com.edu.mf.view.drawing.result.DrawingResultFragment.ImgInfo
import kotlinx.coroutines.launch

class DrawingViewModel: ViewModel() {
    private val _drawingResponse = MutableLiveData<Event<DrawingResponse>>()
    val drawingResponse: LiveData<Event<DrawingResponse>> = _drawingResponse

    private val _imgInfoList = MutableLiveData<ArrayList<ImgInfo>>()
    val imgInfoList: LiveData<ArrayList<ImgInfo>> = _imgInfoList

    fun setDrawingResponse(drawingResponse: DrawingResponse){
        viewModelScope.launch {
            _drawingResponse.value = Event(drawingResponse)
        }
    }

    fun setImgInfoList(imgInfoList: ArrayList<ImgInfo>){
        viewModelScope.launch {
            _imgInfoList.value = imgInfoList
        }
    }
}