package com.edu.mf.viewmodel

import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.edu.mf.repository.model.study.Quiz
import com.edu.mf.utils.App
import com.navercorp.nid.NaverIdLoginSDK
import java.util.*

private const val TAG = "MainViewModel_지훈"
class MainViewModel : ViewModel(){

    /**
     * 단어 학습 모드 선택
     * 1 : 낱말 익히기 , 2 : 낱말 퀴즈
     */
    private var _mode : MutableLiveData<Int> = MutableLiveData()
    val mode : LiveData<Int> get() = _mode

    private var _selectedIndex : MutableLiveData<Int> = MutableLiveData()
    val selectedIndex : LiveData<Int> get() = _selectedIndex

    private var _quiz : MutableLiveData<Quiz> = MutableLiveData()
    val quiz : LiveData<Quiz> get() = _quiz

    private var _quizIndex : MutableLiveData<ArrayList<Int>> = MutableLiveData()
    val quizIndex : LiveData<ArrayList<Int>> get() = _quizIndex


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

    /**
     * 문제풀기 0번 카테고리 문제 삽입
     * 문제 4개 랜덤 생성후 인데스 셔플
     */

    fun setWordQuiz(){
        var problems = ArrayList<Int>()
        _selectedIndex.value = Random().nextInt(App.PICTURES[selectedCategory].size)
        var currentSelectedIndex = _selectedIndex.value!!
        var current_answer  = -1
        var set = mutableSetOf<Int>()
        set.add(currentSelectedIndex)
        while(set.size<4){
            set.add(Random().nextInt(App.PICTURES[selectedCategory].size))
        }
        var temps = set.toList()
        problems.addAll(temps)
        problems.shuffle()
        var datas = ArrayList<String>()
        for(i in 0..3) {
            datas.add(App.PICTURES[selectedCategory][problems[i]])
            if(problems[i]==currentSelectedIndex)
                current_answer = i
        }
        _quizIndex.value = problems

        var q:Quiz = Quiz(-1,-1)
        when(selectedPCategory){
            0-> q = Quiz(current_answer,currentSelectedIndex,App.PICTURES[selectedCategory][currentSelectedIndex],datas)
            1-> q = Quiz(current_answer,currentSelectedIndex,problems)
            else->{

            }
        }
        _quiz.value = q
    }

    fun startTitleTTS(){
        Log.i(TAG, "startTitleTTS: ")
        textToSpeech?.speak(
            when(selectedPCategory){
                0->"다음 그림은 무었일까요"
                1->"다음 단어에 해당하는 그림은 무었일까요?"
                else -> "else"
            }
            ,TextToSpeech.QUEUE_FLUSH,null,null)
    }


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
    fun startCategoryTTS(){
        when(_selectedPCategory){
            0->{
                for(index in 0 until 4){
                    textToSpeech?.speak("${index+1}번 "+_quiz.value!!.problems[index],TextToSpeech.QUEUE_ADD,null,null)
                }
            }
            1->{
                textToSpeech?.speak(App.PICTURES[selectedCategory][selectedIndex.value!!],TextToSpeech.QUEUE_ADD,null,null)
            }
            else->{

            }
        }
    }


}