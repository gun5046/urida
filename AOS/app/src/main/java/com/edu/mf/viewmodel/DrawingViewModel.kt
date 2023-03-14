package com.edu.mf.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.edu.mf.repository.model.drawing.DrawingResponse
import com.edu.mf.view.common.Event

class DrawingViewModel: ViewModel() {
    private val _drawingResponse = MutableLiveData<Event<DrawingResponse>>()
    val drawingResponse: LiveData<Event<DrawingResponse>> = _drawingResponse

    fun setDrawingResponse(drawingResponse: DrawingResponse){
        _drawingResponse.value = Event(drawingResponse)
    }
}