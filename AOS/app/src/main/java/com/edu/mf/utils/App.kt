package com.edu.mf.utils

import android.app.Application
import android.content.res.Resources
import android.util.Log
import com.edu.mf.BuildConfig
import com.edu.mf.R
import com.edu.mf.repository.model.study.PCategory
import com.google.gson.GsonBuilder
import com.kakao.sdk.common.KakaoSdk
import com.navercorp.nid.NaverIdLoginSDK
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class App : Application(){

    companion object{
        const val TAG = "App_지훈"
        var PICTURES : ArrayList<ArrayList<String>> = arrayListOf()
        var categories : ArrayList<String> = arrayListOf()
        var pCategories : ArrayList<PCategory> = arrayListOf()
        var firstUser = true
        lateinit var sharedPreferencesUtil:SharedPreferencesUtil
        lateinit var userRetrofit: Retrofit
        lateinit var drawingRetrofit: Retrofit
        lateinit var communityRetrofit: Retrofit
        lateinit var resolveRetrofit : Retrofit

        fun resetCategory(resources: Resources){
            categories.clear()
            categories.add(resources.getString(R.string.fruit_vegetables))
            categories.add(resources.getString(R.string.job))
            categories.add(resources.getString(R.string.animal))
            categories.add(resources.getString(R.string.`object`))
            categories.add(resources.getString(R.string.place))
            categories.add(resources.getString(R.string.action))
        }

        fun resetPCategories(resources: Resources){
            pCategories.clear()
            pCategories.add(PCategory(resources.getString(R.string.problem_category_first_title),
                resources.getString(R.string.problem_category_first_subtitle)))
            pCategories.add(PCategory(resources.getString(R.string.problem_category_second_title),
                resources.getString(R.string.problem_category_second_subtitle)))
            pCategories.add(PCategory(resources.getString(R.string.problem_category_third_title),
                resources.getString(R.string.problem_category_third_subtitle)))
            pCategories.add(PCategory(resources.getString(R.string.problem_category_fourth_title),
                resources.getString(R.string.problem_category_fourth_subtitle)))
        }
    }

    override fun onCreate() {
        super.onCreate()
        sharedPreferencesUtil = SharedPreferencesUtil(applicationContext)
        KakaoSdk.init(this, BuildConfig.Kakao_API_KEY)
        NaverIdLoginSDK.initialize(this, BuildConfig.OAUTH_CLIENT_ID, BuildConfig.OAUTH_CLIENT_SECRET, BuildConfig.OAUTH_CLIENT_NAME)
        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(5000, TimeUnit.MILLISECONDS)
            .connectTimeout(5000, TimeUnit.MILLISECONDS)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
        userRetrofit = Retrofit.Builder()
            .baseUrl("http://j8d202.p.ssafy.io:8081/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        setImageData()
        drawingRetrofit = Retrofit.Builder()
            .baseUrl("http://j8d202.p.ssafy.io:8084/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        communityRetrofit = Retrofit.Builder()
            .baseUrl("http://j8d202.p.ssafy.io:8083/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        resolveRetrofit = Retrofit.Builder()
            .baseUrl("http://j8d202.p.ssafy.io:8082/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * 이미지 데이터 생성
     */
    private fun setImageData(){
        setPictures()
        setFruitsImage()
        setJobImage()
        setAnimalImage()
        setObjectImage()
        setPlaceImage()
        setActionImage()
        setDrawsImage()
        setCategoryData()
        setPCategories()
    }

    private fun setPictures(){
        for(i in 0..6)
            PICTURES.add(arrayListOf())
    }
    /**
     * category 데이터 입력
     */
    private fun setCategoryData(){
        categories.clear()
        categories.add("과일/채소")
        categories.add("직업")
        categories.add("동물")
        categories.add("물체")
        categories.add("장소")
        categories.add("행동")
    }

    private fun setPCategories(){
        pCategories.clear()
        pCategories.add(PCategory("그림보고 낱말 맞추기","그림을 보고 어떤 단어인지 맞춰 보아요"))
        pCategories.add(PCategory("낱말보고 그림 맞추기","주어진 단어를 보고 어떤 그림인지 맞춰 보아요"))
        pCategories.add(PCategory("빈칸에 알맞는 말 넣기","주어진 그림과 문장을 보고 \n어떤 단어가 들어가야 할지 맞춰 보아요"))
        pCategories.add(PCategory("연관된 단어 맞추기","제시된 단어들과 연관된 단어를 골라 보아요"))
    }

    private fun setFruitsImage(){
        PICTURES[0].add("사과")
        PICTURES[0].add("아보카도")
        PICTURES[0].add("바나나")
        PICTURES[0].add("피망")
        PICTURES[0].add("브로콜리")
        PICTURES[0].add("양배추")
        PICTURES[0].add("당근")
        PICTURES[0].add("자두")
        PICTURES[0].add("옥수수")
        PICTURES[0].add("오이")
        PICTURES[0].add("가지")
        PICTURES[0].add("마늘")
        PICTURES[0].add("생강")
        PICTURES[0].add("포도")
        PICTURES[0].add("청포도")
        PICTURES[0].add("대파")
        PICTURES[0].add("대추")
        PICTURES[0].add("키위")
        PICTURES[0].add("레몬")
        PICTURES[0].add("상추")
        PICTURES[0].add("귤")
        PICTURES[0].add("망고")
        PICTURES[0].add("멜론")
        PICTURES[0].add("버섯")
        PICTURES[0].add("양배추")
        PICTURES[0].add("양파")
        PICTURES[0].add("오렌지")
        PICTURES[0].add("참외")
        PICTURES[0].add("파프리카")
        PICTURES[0].add("완두콩")
        PICTURES[0].add("복숭아")
        PICTURES[0].add("배")
        PICTURES[0].add("고추")
        PICTURES[0].add("감")
        PICTURES[0].add("파인애플")
        PICTURES[0].add("감자")
        PICTURES[0].add("호박")
        PICTURES[0].add("무")
        PICTURES[0].add("시금치")
        PICTURES[0].add("딸기")
        PICTURES[0].add("고구마")
        PICTURES[0].add("토마토")
        PICTURES[0].add("수박")
    }
    private fun setJobImage(){
        PICTURES[1].add("배우")
        PICTURES[1].add("건축가")
        PICTURES[1].add("군인")
        PICTURES[1].add("화가")
        PICTURES[1].add("우주비행사")
        PICTURES[1].add("작가")
        PICTURES[1].add("농구선수")
        PICTURES[1].add("경호원")
        PICTURES[1].add("촬영기사")
        PICTURES[1].add("카레이서")
        PICTURES[1].add("목수")
        PICTURES[1].add("치어리더")
        PICTURES[1].add("요리사")
        PICTURES[1].add("환경미화원")
        PICTURES[1].add("작곡가")
        PICTURES[1].add("지휘자")
        PICTURES[1].add("상담사")
        PICTURES[1].add("댄서")
        PICTURES[1].add("집배원")
        PICTURES[1].add("개발자")
        PICTURES[1].add("영화감독")
        PICTURES[1].add("의사")
        PICTURES[1].add("농부")
        PICTURES[1].add("소방관")
        PICTURES[1].add("어부")
        PICTURES[1].add("미용사")
        PICTURES[1].add("헬스트레이너")
        PICTURES[1].add("야구선수")
        PICTURES[1].add("기자")
        PICTURES[1].add("판사")
        PICTURES[1].add("마술사")
        PICTURES[1].add("마사지사")
        PICTURES[1].add("정비사")
        PICTURES[1].add("광부")
        PICTURES[1].add("스님")
        PICTURES[1].add("수녀")
        PICTURES[1].add("간호사")
        PICTURES[1].add("피아니스트")
        PICTURES[1].add("조종사")
        PICTURES[1].add("경찰")
        PICTURES[1].add("프로게이머")
        PICTURES[1].add("과학자")
        PICTURES[1].add("판매원")
        PICTURES[1].add("가수")
        PICTURES[1].add("축구선수")
        PICTURES[1].add("승무원")
        PICTURES[1].add("학생")
        PICTURES[1].add("선생님")
        PICTURES[1].add("웨이터")
        PICTURES[1].add("유튜버")
    }
    private fun setAnimalImage(){
        PICTURES[2].add("악어")
        PICTURES[2].add("곰")
        PICTURES[2].add("벌")
        PICTURES[2].add("나비")
        PICTURES[2].add("카멜레온")
        PICTURES[2].add("고양이")
        PICTURES[2].add("닭")
        PICTURES[2].add("치타")
        PICTURES[2].add("소")
        PICTURES[2].add("게")
        PICTURES[2].add("달팽이")
        PICTURES[2].add("사슴")
        PICTURES[2].add("두더지")
        PICTURES[2].add("개")
        PICTURES[2].add("오리")
        PICTURES[2].add("코끼리")
        PICTURES[2].add("물고기")
        PICTURES[2].add("여우")
        PICTURES[2].add("개구리")
        PICTURES[2].add("기린")
        PICTURES[2].add("해파리")
        PICTURES[2].add("하마")
        PICTURES[2].add("하이에나")
        PICTURES[2].add("말")
        PICTURES[2].add("잠자리")
        PICTURES[2].add("캥거루")
        PICTURES[2].add("사자")
        PICTURES[2].add("병아리")
        PICTURES[2].add("미어캣")
        PICTURES[2].add("메뚜기")
        PICTURES[2].add("원숭이")
        PICTURES[2].add("쥐")
        PICTURES[2].add("무당벌레")
        PICTURES[2].add("너구리")
        PICTURES[2].add("문어")
        PICTURES[2].add("오랑우탄")
        PICTURES[2].add("올빼미")
        PICTURES[2].add("팬더")
        PICTURES[2].add("돼지")
        PICTURES[2].add("풍뎅이")
        PICTURES[2].add("토끼")
        PICTURES[2].add("사마귀")
        PICTURES[2].add("상어")
        PICTURES[2].add("양")
        PICTURES[2].add("뱀")
        PICTURES[2].add("호랑이")
        PICTURES[2].add("거북이")
        PICTURES[2].add("고래")
        PICTURES[2].add("늑대")
        PICTURES[2].add("염소")
    }
    private fun setObjectImage(){
        PICTURES[3].add("에어컨")
        PICTURES[3].add("자동차")
        PICTURES[3].add("백팩")
        PICTURES[3].add("바구니")
        PICTURES[3].add("자전거")
        PICTURES[3].add("칠판")
        PICTURES[3].add("카메라")
        PICTURES[3].add("카드")
        PICTURES[3].add("의자")
        PICTURES[3].add("바지")
        PICTURES[3].add("노트북")
        PICTURES[3].add("컵")
        PICTURES[3].add("커튼")
        PICTURES[3].add("책상")
        PICTURES[3].add("문")
        PICTURES[3].add("이어폰")
        PICTURES[3].add("엘리베이터")
        PICTURES[3].add("지우개")
        PICTURES[3].add("비상문")
        PICTURES[3].add("안경")
        PICTURES[3].add("꽃")
        PICTURES[3].add("냉장고")
        PICTURES[3].add("구두")
        PICTURES[3].add("열쇠")
        PICTURES[3].add("사물함")
        PICTURES[3].add("마스크")
        PICTURES[3].add("마이크")
        PICTURES[3].add("휴대폰")
        PICTURES[3].add("돈")
        PICTURES[3].add("대걸레")
        PICTURES[3].add("목걸이")
        PICTURES[3].add("노트")
        PICTURES[3].add("횡단보도")
        PICTURES[3].add("펜")
        PICTURES[3].add("연필")
        PICTURES[3].add("리모컨")
        PICTURES[3].add("쓰레기통")
        PICTURES[3].add("샤워기")
        PICTURES[3].add("신발")
        PICTURES[3].add("비누")
        PICTURES[3].add("계단")
        PICTURES[3].add("빨대")
        PICTURES[3].add("가로등")
        PICTURES[3].add("TV")
        PICTURES[3].add("칫솔")
        PICTURES[3].add("치약")
        PICTURES[3].add("신호등")
        PICTURES[3].add("나무")
        PICTURES[3].add("전선줄")
        PICTURES[3].add("청소기")
        PICTURES[3].add("창문")
    }
    private fun setPlaceImage(){
        PICTURES[4].add("미용실")
        PICTURES[4].add("아파트")
        PICTURES[4].add("미술관")
        PICTURES[4].add("편의점")
        PICTURES[4].add("빵집")
        PICTURES[4].add("사막")
        PICTURES[4].add("소방서")
        PICTURES[4].add("낚시터")
        PICTURES[4].add("은행")
        PICTURES[4].add("민속촌")
        PICTURES[4].add("화장실")
        PICTURES[4].add("보건소")
        PICTURES[4].add("유적지")
        PICTURES[4].add("병원")
        PICTURES[4].add("주택")
        PICTURES[4].add("실험실")
        PICTURES[4].add("세탁소")
        PICTURES[4].add("도서관")
        PICTURES[4].add("마트")
        PICTURES[4].add("산")
        PICTURES[4].add("박물관")
        PICTURES[4].add("바다")
        PICTURES[4].add("공원")
        PICTURES[4].add("약국")
        PICTURES[4].add("사진관")
        PICTURES[4].add("놀이공원")
        PICTURES[4].add("방송국")
        PICTURES[4].add("카페")
        PICTURES[4].add("주민센터")
        PICTURES[4].add("공연장")
        PICTURES[4].add("백화점")
        PICTURES[4].add("꽃집")
        PICTURES[4].add("주유소")
        PICTURES[4].add("놀이터")
        PICTURES[4].add("경찰서")
        PICTURES[4].add("수영장")
        PICTURES[4].add("음식점")
        PICTURES[4].add("강")
        PICTURES[4].add("학교")
        PICTURES[4].add("스키장")
        PICTURES[4].add("썰매장")
        PICTURES[4].add("분식집")
        PICTURES[4].add("우주")
        PICTURES[4].add("경기장")
        PICTURES[4].add("문구점")
        PICTURES[4].add("영화관")
        PICTURES[4].add("시장")
        PICTURES[4].add("우체국")
        PICTURES[4].add("결혼식장")
        PICTURES[4].add("동물원")
    }
    private fun setActionImage(){
        PICTURES[5].add("자전거타기")
        PICTURES[5].add("양치")
        PICTURES[5].add("옮기기")
        PICTURES[5].add("젓가락질")
        PICTURES[5].add("청소")
        PICTURES[5].add("요리")
        PICTURES[5].add("자르기")
        PICTURES[5].add("설거지")
        PICTURES[5].add("그리기")
        PICTURES[5].add("마시기")
        PICTURES[5].add("밥먹기")
        PICTURES[5].add("운동")
        PICTURES[5].add("낚시")
        PICTURES[5].add("듣기")
        PICTURES[5].add("인사")
        PICTURES[5].add("등산")
        PICTURES[5].add("포옹")
        PICTURES[5].add("뛰기")
        PICTURES[5].add("눕기")
        PICTURES[5].add("명상")
        PICTURES[5].add("줍기")
        PICTURES[5].add("놀이")
        PICTURES[5].add("야구")
        PICTURES[5].add("농구")
        PICTURES[5].add("축구")
        PICTURES[5].add("독서")
        PICTURES[5].add("달리기")
        PICTURES[5].add("앉기")
        PICTURES[5].add("노래")
        PICTURES[5].add("잠자기")
        PICTURES[5].add("웃기")
        PICTURES[5].add("공부")
        PICTURES[5].add("수영")
        PICTURES[5].add("달리기")
        PICTURES[5].add("말하기")
        PICTURES[5].add("생각")
        PICTURES[5].add("던지기")
        PICTURES[5].add("타자치기")
        PICTURES[5].add("걷기")
        PICTURES[5].add("빨래")
        PICTURES[5].add("손씻기")
        PICTURES[5].add("물주기")
        PICTURES[5].add("글쓰기")
        PICTURES[5].add("하품")
    }
    private fun setDrawsImage(){
        PICTURES[6].add("비행기")
        PICTURES[6].add("천사")
        PICTURES[6].add("개미")
        PICTURES[6].add("팔")
        PICTURES[6].add("도끼")
        PICTURES[6].add("붕대")
        PICTURES[6].add("야구방망이")
        PICTURES[6].add("박쥐")
        PICTURES[6].add("욕조")
        PICTURES[6].add("해변")
        PICTURES[6].add("블루베리")
        PICTURES[6].add("수염")
        PICTURES[6].add("침대")
        PICTURES[6].add("하프")
        PICTURES[6].add("혁대")
        PICTURES[6].add("빵")
        PICTURES[6].add("양동이")
        PICTURES[6].add("새")
        PICTURES[6].add("버스")
        PICTURES[6].add("풀숲")
        PICTURES[6].add("책")
        PICTURES[6].add("부메랑")
        PICTURES[6].add("팔찌")
        PICTURES[6].add("뇌")
        PICTURES[6].add("선인장")
        PICTURES[6].add("케이크")
        PICTURES[6].add("계산기")
        PICTURES[6].add("달력")
        PICTURES[6].add("낙타")
        PICTURES[6].add("구름")
        PICTURES[6].add("컴파스")
        PICTURES[6].add("컴퓨터")
        PICTURES[6].add("성")
        PICTURES[6].add("첼로")
        PICTURES[6].add("교회")
        PICTURES[6].add("원")
        PICTURES[6].add("양초")
        PICTURES[6].add("대포")
        PICTURES[6].add("카누")
        PICTURES[6].add("쿠키")
        PICTURES[6].add("소파")
        PICTURES[6].add("다이아몬드")
        PICTURES[6].add("식기세척기")
        PICTURES[6].add("돌고래")
        PICTURES[6].add("도넛")
        PICTURES[6].add("발가락")
        PICTURES[6].add("용")
        PICTURES[6].add("시계")
        PICTURES[6].add("왕관")
        PICTURES[6].add("귀")
        PICTURES[6].add("팔꿈치")
        PICTURES[6].add("봉투")
        PICTURES[6].add("램프")
        PICTURES[6].add("얼굴")
        PICTURES[6].add("깃털")
        PICTURES[6].add("드릴")
        PICTURES[6].add("크레파스")
        PICTURES[6].add("모자")
        PICTURES[6].add("헤드폰")
        PICTURES[6].add("헬리콥터")
        PICTURES[6].add("발")
        PICTURES[6].add("포크")
        PICTURES[6].add("핫도그")
        PICTURES[6].add("집")
        PICTURES[6].add("아이스크림")
        PICTURES[6].add("재킷")
        PICTURES[6].add("감옥")
        PICTURES[6].add("해머")
        PICTURES[6].add("손")
        PICTURES[6].add("키보드")
        PICTURES[6].add("무릎")
        PICTURES[6].add("사다리")
        PICTURES[6].add("햄버거")
        PICTURES[6].add("나뭇입")
        PICTURES[6].add("다리")
        PICTURES[6].add("전구")
        PICTURES[6].add("벼락")
        PICTURES[6].add("소방차")
        PICTURES[6].add("지도")
        PICTURES[6].add("지갑")
        PICTURES[6].add("싱크대")
        PICTURES[6].add("해골")
        PICTURES[6].add("빌딩")
        PICTURES[6].add("울타리")
        PICTURES[6].add("손가락")
        PICTURES[6].add("잔디")
        PICTURES[6].add("기타")
        PICTURES[6].add("치아")
        PICTURES[6].add("성냥")
        PICTURES[6].add("토네이도")
        PICTURES[6].add("달")
        PICTURES[6].add("모기")
        PICTURES[6].add("기차")
        PICTURES[6].add("삼각형")
        PICTURES[6].add("우산")
        PICTURES[6].add("입")
        PICTURES[6].add("립스틱")
        PICTURES[6].add("손톱")
        PICTURES[6].add("코")
        PICTURES[6].add("테이블")
        PICTURES[6].add("팔각형")
        PICTURES[6].add("칼")
        PICTURES[6].add("티셔츠")
        PICTURES[6].add("오븐")
        PICTURES[6].add("부엉이")
        PICTURES[6].add("붓")
        PICTURES[6].add("베개")
        PICTURES[6].add("피자")
        PICTURES[6].add("연못")
        PICTURES[6].add("낙하산")
        PICTURES[6].add("여권")
        PICTURES[6].add("땅콩")
        PICTURES[6].add("코뿔소")
        PICTURES[6].add("펭귄")
        PICTURES[6].add("피아노")
        PICTURES[6].add("액자")
        PICTURES[6].add("라디오")
        PICTURES[6].add("비")
        PICTURES[6].add("무지개")
        PICTURES[6].add("리모콘")
        PICTURES[6].add("클립")
        PICTURES[6].add("판다")
        PICTURES[6].add("롤러코스터")
        PICTURES[6].add("샌드위치")
        PICTURES[6].add("톱")
        PICTURES[6].add("가위")
        PICTURES[6].add("전갈")
        PICTURES[6].add("드라이버")
        PICTURES[6].add("와인잔")
        PICTURES[6].add("얼룩말")
        PICTURES[6].add("별")
        PICTURES[6].add("스테이크")
        PICTURES[6].add("잠수함")
        PICTURES[6].add("풍차")
        PICTURES[6].add("바퀴")
        PICTURES[6].add("눈송이")
        PICTURES[6].add("축구공")
        PICTURES[6].add("눈사람")
        PICTURES[6].add("양말")
        PICTURES[6].add("거미")
        PICTURES[6].add("숟가락")
        PICTURES[6].add("태양")
        PICTURES[6].add("백조")
        PICTURES[6].add("빗자루")
    }

}