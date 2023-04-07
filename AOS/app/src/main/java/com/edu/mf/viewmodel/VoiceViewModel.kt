package com.edu.mf.viewmodel

import androidx.lifecycle.ViewModel

class VoiceViewModel : ViewModel() {
    private var _word = ""
    val word get() = _word

    fun setWord(word: String){
        _word = word
    }
}