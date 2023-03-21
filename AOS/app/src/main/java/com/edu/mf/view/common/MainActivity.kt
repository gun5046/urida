package com.edu.mf.view.common

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.edu.mf.BuildConfig
import androidx.lifecycle.ViewModelProvider
import com.edu.mf.R
import com.edu.mf.databinding.ActivityMainBinding
import com.edu.mf.repository.api.DrawingService
import com.edu.mf.repository.api.UserService
import com.edu.mf.repository.model.User
import com.edu.mf.utils.App
import com.edu.mf.view.LanguageFragment
import com.edu.mf.view.LoginFragment
import com.edu.mf.view.MainFragment
import com.kakao.sdk.common.KakaoSdk
import com.navercorp.nid.NaverIdLoginSDK
import java.util.*
import com.edu.mf.viewmodel.MainViewModel
import com.edu.mf.viewmodel.PictureViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val loginService = App.userRetrofit.create(UserService::class.java)
    val drawingService = App.drawingRetrofit.create(DrawingService::class.java)
    var user: User? = null

    private lateinit var mainViewModel: MainViewModel
    private lateinit var pictureViewModel: PictureViewModel
    init {
        instance = this
    }

    companion object{
        private var instance: MainActivity? = null
        fun getInstance(): MainActivity? {
            return instance
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        KakaoSdk.init(this, BuildConfig.Kakao_API_KEY)
        NaverIdLoginSDK.initialize(this, BuildConfig.OAUTH_CLIENT_ID, BuildConfig.OAUTH_CLIENT_SECRET, BuildConfig.OAUTH_CLIENT_NAME)

        user = App.sharedPreferencesUtil.getUser()
        if(user != null) {
            CoroutineScope(Dispatchers.IO).launch {
                user = loginService.login(user!!.social_id!!, user!!.type!!).body()
                if(user!!.uid != null){
                    changeLocale(user!!.language)
                    changeFragment(MainFragment())
                } else {
                    changeFragment(LoginFragment())
                }
            }
        } else {
            changeFragment(LoginFragment())
        }
        
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        pictureViewModel = ViewModelProvider(this)[PictureViewModel::class.java]
//        changeFragment(MainFragment())
    }

    fun changeFragment(fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.framelayout_main, fragment)
            .commit()
    }

    fun addFragment(fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.slide_in_right,R.anim.slide_out_left,R.anim.slide_in_left,R.anim.slide_out_right)
            .replace(R.id.framelayout_main, fragment)
            .addToBackStack(null)
            .commit()
    }

    fun popFragment(){
        supportFragmentManager
            .popBackStack()
    }

    fun changeLocale(type: Int){
        var locale = Locale("ko", "KR")
        when(type){
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