package com.edu.mf.view.common

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.edu.mf.BuildConfig
import androidx.lifecycle.ViewModelProvider
import com.edu.mf.R
import com.edu.mf.databinding.ActivityMainBinding
import com.edu.mf.repository.api.CommunityService
import com.edu.mf.repository.api.DrawingService
import com.edu.mf.repository.api.UserService
import com.edu.mf.repository.db.ProblemDatabase
import com.edu.mf.repository.db.ProblemRepository
import com.edu.mf.repository.model.User
import com.edu.mf.utils.App
import com.edu.mf.view.LanguageFragment
import com.edu.mf.view.LoginFragment
import com.edu.mf.view.MainFragment
import com.edu.mf.view.study.quiz.QuizResultDialog
import com.kakao.sdk.common.KakaoSdk
import com.navercorp.nid.NaverIdLoginSDK
import java.util.*
import com.edu.mf.viewmodel.MainViewModel
import com.edu.mf.viewmodel.MainViewModelFactory
import com.edu.mf.viewmodel.PictureViewModel
import com.kakao.sdk.common.util.Utility
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val loginService = App.userRetrofit.create(UserService::class.java)
    val drawingService = App.drawingRetrofit.create(DrawingService::class.java)
    val communityService = App.communityRetrofit.create(CommunityService::class.java)

    var user: User? = null

    private lateinit var mainViewModel: MainViewModel
    private lateinit var pictureViewModel: PictureViewModel

    init {
        instance = this
    }

    companion object {
        private var instance: MainActivity? = null
        fun getInstance(): MainActivity? {
            return instance
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val dao = ProblemDatabase.getInstance(applicationContext).problemDao
        val repository = ProblemRepository(dao)
        val factory = MainViewModelFactory(repository)

        KakaoSdk.init(this, BuildConfig.Kakao_API_KEY)
        NaverIdLoginSDK.initialize(
            this,
            BuildConfig.OAUTH_CLIENT_ID,
            BuildConfig.OAUTH_CLIENT_SECRET,
            BuildConfig.OAUTH_CLIENT_NAME
        )

        user = App.sharedPreferencesUtil.getUser()
        if (user != null) {
            CoroutineScope(Dispatchers.IO).launch {
                user = loginService.login(user!!.social_id!!, user!!.type!!).body()
                if (user!!.uid != null) {
                    changeLocale(user!!.language)
                    changeFragment(MainFragment())
                } else {
                    changeFragment(LoginFragment())
                }
            }
        } else {
            changeFragment(LoginFragment())
        }

        mainViewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]
        pictureViewModel = ViewModelProvider(this)[PictureViewModel::class.java]
        //        changeFragment(MainFragment())
        mainViewModel.insertData()
    }

    fun changeFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.framelayout_main, fragment)
            .commit()
    }


    /**
     * 퀴즈 문제 출제시 backstack을 한번에 지우기 위해 name설정
     */
    fun addQuizFragment(fragment: Fragment, fragmentName: String) {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in_right,
                R.anim.slide_out_left,
                R.anim.slide_in_left,
                R.anim.slide_out_right
            )
            .replace(R.id.framelayout_main, fragment)
            .addToBackStack(fragmentName)
            .commit()
    }

    /**
     * 퀴즈 문제 출제시 backstack을 한번에 지움
     */
    fun popQuizFragment(fragmentName: String) {
        supportFragmentManager
            .popBackStack(fragmentName, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    fun addFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in_right,
                R.anim.slide_out_left,
                R.anim.slide_in_left,
                R.anim.slide_out_right
            )
            .replace(R.id.framelayout_main, fragment)
            .addToBackStack(null)
            .commit()
    }

    fun addFragmentNoAnim(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
            .replace(R.id.framelayout_main, fragment)
            .addToBackStack(null)
            .commit()
    }

    fun popFragment() {
        supportFragmentManager
            .popBackStack()
    }

    fun changeLocale(type: Int) {
        var locale = Locale("ko", "KR")
        when (type) {
            0 -> {
                locale = Locale("ko", "KR")
            }
            1 -> {
                locale = Locale("zh", "CN")
            }
            2 -> {
                locale = Locale("vi", "VN")
            }
            3 -> {
                locale = Locale("en", "US")
            }
        }
        val configuration = this.resources.configuration
        configuration.setLocale(locale)
        resources.updateConfiguration(configuration, resources.displayMetrics)
    }


}