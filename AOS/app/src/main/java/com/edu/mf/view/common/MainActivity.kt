package com.edu.mf.view.common

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.edu.mf.BuildConfig
import com.edu.mf.R
import com.edu.mf.databinding.ActivityMainBinding
import com.edu.mf.utils.App
import com.edu.mf.view.LanguageFragment
import com.edu.mf.view.LoginFragment
import com.edu.mf.view.MainFragment
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import com.navercorp.nid.NaverIdLoginSDK
import java.util.*

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

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
        Log.d(TAG, "onCreate: ")
        changeLocale(App.sharedPreferencesUtil.getLanguageCode())
        changeFragment(LoginFragment())
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