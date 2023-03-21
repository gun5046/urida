package com.edu.mf.viewmodel

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.edu.mf.repository.model.picture.DetectedPicture

class PictureViewModel : ViewModel() {

    private var _uri : Uri? = null
    val uri get() = _uri

    fun setUri(uri: Uri?){
        _uri = uri
    }

    private val list = mutableListOf<DetectedPicture>()

    private var _detectedPictureList : MutableLiveData<MutableList<DetectedPicture>> = MutableLiveData()
    val detectedPictureList get() = _detectedPictureList

    fun addPicture(detectedPicture: DetectedPicture){
        list.add(detectedPicture)
        _detectedPictureList.value = list
    }

    fun clearPicture(){
        list.clear()
        _detectedPictureList.value = list
    }

    init {
        _detectedPictureList.value = list
    }
}