package com.edu.mf.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.edu.mf.repository.model.picture.DetectedPicture

class PictureViewModel : ViewModel() {

    private var _uri : Uri? = null
    val uri get() = _uri

    fun setUri(uri: Uri?){
        _uri = uri
    }

    private var _detectedPictureList : MutableList<DetectedPicture> = mutableListOf()
    val detectedPictureList get() = _detectedPictureList

    fun addPicture(detectedPicture: DetectedPicture){
        _detectedPictureList.add(detectedPicture)
    }

    fun clearPicture(){
        _detectedPictureList.clear()
    }

}