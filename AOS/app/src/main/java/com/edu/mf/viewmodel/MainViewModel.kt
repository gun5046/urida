package com.edu.mf.viewmodel

import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edu.mf.repository.db.ProblemRepository
import com.edu.mf.repository.model.resolve.ResolveResponse
import com.edu.mf.repository.model.study.Problem
import com.edu.mf.repository.model.study.Quiz
import com.edu.mf.utils.App
import com.navercorp.nid.NaverIdLoginSDK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList

private const val TAG = "MainViewModel_지훈"
class MainViewModel(private val repository: ProblemRepository) : ViewModel(){

    /**
     * 단어 학습 모드 선택
     * 1 : 낱말 익히기 , 2 : 낱말 퀴즈
     */
    private var _mode : MutableLiveData<Int> = MutableLiveData()
    val mode : LiveData<Int> get() = _mode

    //3번 카테고리 문제
    private var _selectedProblem : MutableLiveData<Problem> = MutableLiveData()
    val selectedProblem : LiveData<Problem> get() = _selectedProblem

    //정답으로 뽑힌 index 번호
    private var _selectedIndex : MutableLiveData<Int> = MutableLiveData()
    val selectedIndex : LiveData<Int> get() = _selectedIndex

    //해당 문제의 quiz
    private var _quiz : MutableLiveData<Quiz> = MutableLiveData()
    val quiz : LiveData<Quiz> get() = _quiz

    //quiz index 번호
    private var _quizIndex: MutableLiveData<ArrayList<Int>> = MutableLiveData()
    val quizIndex: LiveData<ArrayList<Int>> get() = _quizIndex


    //다시풀기 cache list
    private var _resolve : MutableLiveData<List<ResolveResponse>> = MutableLiveData()
    val resolve : LiveData<List<ResolveResponse>> get() = _resolve

    //다시풀어보기인지 quiz인지 체크
    private var _resolveMode : Boolean = false
    val resolveMode : Boolean get() = _resolveMode

    //다시풀어보기 현재 index 번호
    private var _resolveIndex : MutableLiveData<Int> = MutableLiveData()
    val resolveIndex : LiveData<Int> get() = _resolveIndex

    //해당 문제의 정답
    private var _answerIndex : Int = -1
    val answerIndex get() = _answerIndex

    private var _relateProblem : MutableLiveData<ArrayList<Int>> = MutableLiveData()
    val relateProblem : LiveData<ArrayList<Int>> get() = _relateProblem

    private var textToSpeech: TextToSpeech? = null

    private var _answer : MutableLiveData<String> = MutableLiveData()
    val answer get() = _answer

    private var _threeSelectedIndexTo : MutableLiveData<Int> = MutableLiveData()
    val threeSelectedIndexTo : LiveData<Int> get() = _threeSelectedIndexTo

    private var _selectedCategory : Int = -1
    val selectedCategory get() = _selectedCategory

    private var _selectedPCategory : Int = -1
    val selectedPCategory get() = _selectedPCategory

    private var _currentIndex : MutableLiveData<Int> = MutableLiveData()
    val currentIndex  get() = _currentIndex

    private var _bookMark : MutableLiveData<String> = MutableLiveData()
    val bookMark get() = _bookMark

    fun setResolve(resolves:List<ResolveResponse>){
        _resolve.value = resolves
    }

    fun enableResolveMode(){
        _resolveMode = true
    }
    fun disableResolveMode(){
        _resolveMode = false
    }

    fun setResolveIndex(index : Int){
        _resolveIndex.value = index
    }
    fun setNextResolve(){
        _resolveIndex.value = _resolveIndex.value!!.plus(1)
    }

    fun setResolveQuiz(){
        val current_resolve =resolve.value!![resolveIndex.value!!]
        var current_answer = -1
        var q = Quiz(-1,-1)
        var oneList = arrayListOf<String>()
        _selectedCategory = current_resolve.category_id
        _selectedIndex.value = current_resolve.answer_id
        _selectedPCategory = current_resolve.type
        for(i in 0 until current_resolve.choices.size){
            if(current_resolve.choices[i]==current_resolve.answer_id) {
                current_answer = i
                _answerIndex = i
            }
            oneList.add(App.PICTURES[current_resolve.category_id][current_resolve.choices[i]])
        }

        when(current_resolve.type){
            0-> q = Quiz(
                current_answer,current_resolve.answer_id,
                App.PICTURES[current_resolve.category_id][current_resolve.answer_id],
                oneList)
            1-> {
                var twoList = arrayListOf<Int>()
                twoList.addAll(current_resolve.choices)
                q = Quiz(
                    current_answer,
                    _selectedIndex.value!!,
                    twoList
                )
            }

            2->{
                var three_answer = -1
                var indexSet = mutableSetOf<Int>()
                indexSet.add(selectedProblem.value!!.order_id)
                while(indexSet.size<4)
                    indexSet.add(Random().nextInt(App.PICTURES[selectedCategory].size))
                var problem_lists = indexSet.toList().shuffled()
                var p_lists = arrayListOf<Int>()
                p_lists.addAll(problem_lists)
                _quizIndex.value = p_lists
                for(i in 0 until 4){
                    if(current_resolve.answer_id==current_resolve.choices[i]) {
                        three_answer = i
                        Log.i(TAG, "코코코코코ㅗ: ${i}")
                    }
                }
                var threeList = arrayListOf<Int>()
                threeList.addAll(current_resolve.choices)
                q = Quiz(three_answer,current_resolve.category_id,threeList,selectedProblem.value!!)
            }
            else->{
                var cr = arrayListOf<Int>()
                var ce = arrayListOf<Int>()
                _relateProblem.value = current_resolve.titles!!
                cr.addAll(current_resolve.choices)
                ce.addAll(current_resolve.examples)
                q = Quiz(current_resolve.answer_id,current_resolve.answer_id,ce,cr)
            }

        }
        Log.i(TAG, "setResolveQuiz: ${q}")
        _quiz.value = q
    }

    /**
     * 문제풀기 0번 카테고리 문제 삽입
     * 문제 4개 랜덤 생성후 인데스 셔플
     */
    fun setQuiz(){
        var problems = ArrayList<Int>()
        _selectedIndex.value = Random().nextInt(App.PICTURES[selectedCategory].size)
        var currentSelectedIndex = _selectedIndex.value!!
        var current_answer  = -1
        var indexSet = mutableSetOf<Int>()
        indexSet.add(currentSelectedIndex)
        while(indexSet.size<4){
            indexSet.add(Random().nextInt(App.PICTURES[selectedCategory].size))
        }
        var indexTemps = indexSet.toList()
        problems.addAll(indexTemps)
        problems.shuffle()
        Log.i(TAG, "setQuiz: ${problems}")

        var datas = ArrayList<String>()
        for(i in 0..3) {
            datas.add(App.PICTURES[selectedCategory][problems[i]])
            if(problems[i]==currentSelectedIndex) {
                _answerIndex = i
                current_answer = i
            }
        }
        _quizIndex.value = problems


        var q:Quiz = Quiz(-1,-1)
        when(selectedPCategory){
            0-> q = Quiz(current_answer,currentSelectedIndex,App.PICTURES[selectedCategory][currentSelectedIndex],datas)
            1-> q = Quiz(current_answer,currentSelectedIndex,problems)
            2->{
                var three_answer = -1
                var indexSet = mutableSetOf<Int>()
                indexSet.add(selectedProblem.value!!.order_id)
                while(indexSet.size<4)
                    indexSet.add(Random().nextInt(App.PICTURES[selectedCategory].size))
                var problem_lists = indexSet.toList().shuffled()
                var p_lists = arrayListOf<Int>()
                p_lists.addAll(problem_lists)
                _quizIndex.value = p_lists
                for(i in 0 until 4){
                    if(p_lists[i]==selectedProblem.value!!.order_id)
                        three_answer = i
                }
                q = Quiz(three_answer,selectedProblem.value!!.category_id,p_lists,selectedProblem.value!!)
            }
            else->{
                var categorySet = mutableSetOf<Int>()
                categorySet.add(selectedCategory)

                var titleSet = mutableSetOf<Int>()
                while(titleSet.size<4) {
                    var rand = Random().nextInt(App.PICTURES[selectedCategory].size)
                    if(rand!=currentSelectedIndex){
                        titleSet.add(rand)
                    }
                }

                var titles = arrayListOf<Int>()
                titles.addAll(titleSet.toList())
                _relateProblem.value = titles


                //relate 보기에 들어갈 정답을 제외한 3가지 카테고리
                while(categorySet.size<4){
                    categorySet.add(Random().nextInt(6))
                }

                var categoryTemps = categorySet.toList()
                val temps = arrayListOf<Int>()
                temps.addAll(categoryTemps)
                temps.shuffle()


                for(i in 0..3){
                    if(temps[i]==_selectedCategory) {
                        current_answer = i
                        _answerIndex = i
                    }
                    if(App.PICTURES[temps[i]].size<=quizIndex.value!![i]){
                        quizIndex.value!![i] = App.PICTURES[temps[i]].size-1
                    }
                }
                q = Quiz(current_answer,currentSelectedIndex,quizIndex.value!!,temps)

            }
        }
        Log.i(TAG, "setQuiz: ${q}")
        _quiz.value = q
    }

    fun getResolveProblem(){
        viewModelScope.launch(Dispatchers.IO){
            withContext(Dispatchers.Main){
                _selectedProblem.value = repository.selectProblemById(resolve.value!![resolveIndex.value!!].sentence_id)
                Log.i(TAG, "getResolveProblem: ${_selectedProblem.value!!}")
            }
        }
    }

    fun getProblem() {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                val lists = repository.select(selectedCategory)
                val threeSelectedIndex = Random().nextInt(10)
                val threeSelectedProblem = lists[threeSelectedIndex]
                _threeSelectedIndexTo.value = threeSelectedProblem.id
                _selectedProblem.value = threeSelectedProblem
            }
        }
    }

    fun startTitleTTS(){
        textToSpeech?.speak(
            when(selectedPCategory){
                0->"다음 그림은 무엇일까요"
                1->"다음 단어에 해당하는 그림은 무엇일까요?"
                2->"다음 빈칸에 들어갈 알맞은 단어를 골라주세요"
                else -> "다음 단어들과 연관된 단어는 무엇일까요?"
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
            2->{
                for(index in 0 until 4){
                    textToSpeech?.speak("${index+1}번 "+App.PICTURES[selectedCategory][quiz.value!!.problems_i[index]],TextToSpeech.QUEUE_ADD,null,null)
                }
            }
            else->{
                for(index in 0 until 4){
                    textToSpeech?.speak("${index+1}번 "+App.PICTURES[_quiz.value!!.relate_categories[index]][_quiz.value!!.problems_i[index]],
                        TextToSpeech.QUEUE_ADD,null,null)
                }
            }
        }
    }
    fun startTTSWithParameter(text: String){
        textToSpeech?.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    fun insertData() = viewModelScope.launch(Dispatchers.IO) {
        var isStored = repository.selectAll().size
        val cateThreeProblems = insertLocalData()
        withContext(Dispatchers.Main){
            if(isStored<1){
                for(i in 0 until 6){
                    for(j in 0 until 10){
                        repository.insert(cateThreeProblems[i][j])
                    }
                }
            }
        }
    }

    fun insertLocalData() : ArrayList<ArrayList<Problem>>{
        var datas : ArrayList<ArrayList<Problem>> = arrayListOf()
        for(i in 0..6)
            datas.add(arrayListOf())

        datas[0].add(Problem(0,"태국은 ____가 유명하다.",0,21))
        datas[0].add(Problem(0,"____는 맵다.",0,32))
        datas[0].add(Problem(0,"____는 쌈 싸먹을때 자주 먹는다.",0,19))
        datas[0].add(Problem(0,"____는 강원도가 유명하다.",0,35))
        datas[0].add(Problem(0,"단____은 스프로 자주 해먹는다.",0,36))
        datas[0].add(Problem(0,"____는 빨갛고 햄버거에 들어간다.",0,41))
        datas[0].add(Problem(0,"____은 씨가 검고 속이 빨갛다.",0,42))
        datas[0].add(Problem(0,"____차는 쓰다.",0,16))
        datas[0].add(Problem(0,"____는 초록색이다.",0,14))
        datas[0].add(Problem(0,"____은 시다.",0,18))

        datas[1].add(Problem(0,"철수는 ____이 되고싶다.",1,39))
        datas[1].add(Problem(0,"지훈이는 ____에게 교육을 받고 있다.",1,47))
        datas[1].add(Problem(0,"관재는 _____인 옥자를 좋아한다",1,45))
        datas[1].add(Problem(0,"____는 팀을 응원하는 직업이다.",1,11))
        datas[1].add(Problem(0,"관재는 절에 들어가 ____이 되기로 했다.",1,34))
        datas[1].add(Problem(0,"지훈이는 세계 최고의 ____다.",1,17))
        datas[1].add(Problem(0,"지훈이는 ____에게 치료를 받았다.",1,36))
        datas[1].add(Problem(0,"____는 비행기를 운전하고 있다",1,38))
        datas[1].add(Problem(0,"전쟁에서 승리하기 위해 많은 ____들이 희생되었다.",1,2))
        datas[1].add(Problem(0,"____는 우리에게 맛있는 음식을 가져다 주었다.",1,12))

        datas[2].add(Problem(0,"관재는 ____ 를 보고 삐약삐약 소리 내었다.",2,27))
        datas[2].add(Problem(0,"____ 는 턱 힘이 강력하다.",2,0))
        datas[2].add(Problem(0,"____ 는 큰 덩치로 쿵쾅쿵쾅 소리내며 걸었다.",2,15))
        datas[2].add(Problem(0,"이 악당은 ____ 가 처리했으니 안심하라구.",2,33))
        datas[2].add(Problem(0,"긴 목을 가진 ____ 가 조용히 풀을 뜯어 먹고있다.",2,19))
        datas[2].add(Problem(0,"수면 위로 나온 ____가 물을 뿜어 낸다.",2,47))
        datas[2].add(Problem(0,"아름다운 ___가 꽃을 찾아 날고있다.",2,3))
        datas[2].add(Problem(0,"나는야 ____. 느리지만 꾸준히 기어다니지.",2,10))
        datas[2].add(Problem(0,"엄마를 잃어버린 ____ 는 비가 오면 서글프게 운다.",2,18))
        datas[2].add(Problem(0,"고기, 야채, 곡물 안 가리고 잘 먹는 나는 ____.",2,38))

        datas[3].add(Problem(0,"너무 더워서 ____을 켜야겠다.",3,0))
        datas[3].add(Problem(0,"____로 사진을 찍는다.",3,6))
        datas[3].add(Problem(0,"다리가 아파서 ____에 앉는다.",3,8))
        datas[3].add(Problem(0,"____에 담긴 물을 마신다.",3,11))
        datas[3].add(Problem(0,"____을 끼고 음악을 듣는다.",3,15))
        datas[3].add(Problem(0,"불이 나면 ____을 통해 나가야한다.",3,18))
        datas[3].add(Problem(0,"____으로 물건을 산다.",3,28))
        datas[3].add(Problem(0,"초록불이 켜지면 ____를 건넌다.",3,32))
        datas[3].add(Problem(0,"____로 손을 씻는다.",3,39))
        datas[3].add(Problem(0,"____을 열고 밖을 본다.",3,50))

        datas[4].add(Problem(0,"머리카락을 자르기 위해 ____에 갔다.",4,0))
        datas[4].add(Problem(0,"돈을 저축하기 위해 ____에 갔다.",4,8))
        datas[4].add(Problem(0,"아플 때는 ____에 가서 치료를 받아야 한다.",4,13))
        datas[4].add(Problem(0,"____에서는 실험가운을 입어야 한다.",4,15))
        datas[4].add(Problem(0,"____에서 책을 빌리자.",4,17))
        datas[4].add(Problem(0,"____에 가면 약을 살 수 있다.",4,23))
        datas[4].add(Problem(0,"____에 들러서 차에 기름을 넣자.",4,32))
        datas[4].add(Problem(0,"____에는 많은 행성이 있다.",4,42))
        datas[4].add(Problem(0,"편지를 보내기 위해 ____에 갈거야.",4,47))
        datas[4].add(Problem(0,"삼촌이 ____에서 결혼을 한대.",4,48))

        datas[5].add(Problem(0,"친구를 만나 반갑게 ___를 한다.",5,14))
        datas[5].add(Problem(0,"도서관에서 ___를 한다.",5,25))
        datas[5].add(Problem(0,"종건이는 깊은 ___에 빠졌다.",5,35))
        datas[5].add(Problem(0,"콩은 작아서 ______하기 힘들다.",5,3))
        datas[5].add(Problem(0,"밥을 먹고 그릇을 ____했다.",5,7))
        datas[5].add(Problem(0,"___는 공을 차는 스포츠다.",5,24))
        datas[5].add(Problem(0,"산을 올라가는 것을 ___이라고 한다.",5,15))
        datas[5].add(Problem(0,"방을 깨끗하게 ___했다.",5,4))
        datas[5].add(Problem(0,"피곤해서 그런지 자꾸 ___이 나온다.",5,24))
        datas[5].add(Problem(0,"그림 ____를 좋아한다",5,8))

        return datas
    }

}