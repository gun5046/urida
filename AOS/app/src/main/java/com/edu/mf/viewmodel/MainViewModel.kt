package com.edu.mf.viewmodel

import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.edu.mf.utils.App
import com.navercorp.nid.NaverIdLoginSDK
import java.util.*

private const val TAG = "MainViewModel"
class MainViewModel : ViewModel(){

    /**
     * 단어 학습 모드 선택
     * 1 : 낱말 익히기 , 2 : 낱말 퀴즈
     */
    private var _mode : MutableLiveData<Int> = MutableLiveData()
    val mode : LiveData<Int> get() = _mode

    private var textToSpeech: TextToSpeech? = null
    private var _answer : MutableLiveData<String> = MutableLiveData()
    val answer get() = _answer

    private var _selectedCategory : Int = -1
    val selectedCategory get() = _selectedCategory

    private var _selectedPCategory : Int = -1
    val selectedPCategory get() = _selectedPCategory

    private var _currentIndex : MutableLiveData<Int> = MutableLiveData()
    val currentIndex  get() = _currentIndex

    private var _bookMark : MutableLiveData<String> = MutableLiveData()
    val bookMark get() = _bookMark

    fun setMode(mode:Int){
        _mode.value = mode
    }

    fun changeCategory(selected:Int){
        _selectedCategory = selected
    }

    fun changeBookMark(bookMark : String){
        _bookMark.value = bookMark
    }

    fun changePCategory(selected:Int){
        _selectedPCategory = selected
    }
    fun setCurrentIndex(index:Int){
        _currentIndex.value = index
    }
    fun setCurrentAnswer(answer:String){
        _answer.value = answer
    }
    fun increaseIndex(){
        _currentIndex.value = _currentIndex.value?.plus(1)
    }
    fun decreaseIndex(){
        _currentIndex.value = _currentIndex.value?.minus(1)
    }
    fun setTTS(){
        textToSpeech = TextToSpeech(NaverIdLoginSDK.applicationContext,TextToSpeech.OnInitListener {
            if(it==TextToSpeech.SUCCESS){
                val result = textToSpeech!!.setLanguage(Locale.KOREAN)
                if(result==TextToSpeech.LANG_MISSING_DATA||result==TextToSpeech.LANG_NOT_SUPPORTED){
                    Log.i(TAG, "setTTS: 지원하지 않는 언어입니다.")
                    return@OnInitListener
                }
            }else{
                Log.d(TAG, "error")
            }

        })
    }
    fun startTTS(){
        println("###   ${selectedCategory}  ${currentIndex.value!!}    ${App.PICTURES[selectedCategory][currentIndex.value!!]}")
        textToSpeech?.speak(App.PICTURES[selectedCategory][currentIndex.value!!],TextToSpeech.QUEUE_FLUSH,null,null)
    }

}