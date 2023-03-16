package com.edu.mf.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel

class PictureViewModel : ViewModel() {

    private var _uri : Uri? = null
    val uri get() = _uri

    fun setUri(uri: Uri?){
        _uri = uri
    }
}