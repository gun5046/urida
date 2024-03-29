package com.edu.mf.view

import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.edu.mf.R
import com.edu.mf.databinding.FragmentLoginBinding
import com.edu.mf.databinding.FragmentPictureBinding
import com.edu.mf.repository.model.User
import com.edu.mf.utils.App
import com.edu.mf.view.common.MainActivity
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "LoginFragment"

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var mainActivity: MainActivity
    private var flag = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        mainActivity = MainActivity.getInstance()!!
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CoroutineScope(Dispatchers.IO).launch {
            while (flag){
                delay(3000)
                fadeOut(binding.textLoginKorean)
                delay(500)
                fadeIn(binding.textLoginEnglish)
                delay(500)
                delay(3000)
                fadeOut(binding.textLoginEnglish)
                delay(500)
                fadeIn(binding.textLoginChinese)
                delay(500)
                delay(3000)
                fadeOut(binding.textLoginChinese)
                delay(500)
                fadeIn(binding.textLoginVietnamese)
                delay(500)
                delay(3000)
                fadeOut(binding.textLoginVietnamese)
                delay(500)
                fadeIn(binding.textLoginKorean)
                delay(500)
            }
        }
        binding.imageviewKakao.setOnClickListener {
            UserApiClient.instance.loginWithKakaoTalk(requireContext()) { token, error ->
                if (error != null) {
                    Log.e(TAG, "로그인 실패", error)
                }
                else if (token != null) {
                    Log.i(TAG, "로그인 성공 ${token.accessToken}")
                    UserApiClient.instance.me { user, error ->
                        login(user?.id.toString(), "kakao")
                    }
                }
            }
        }
        binding.imageviewNaver.setOnClickListener {
            NaverIdLoginSDK.authenticate(requireContext(), object : OAuthLoginCallback {
                override fun onSuccess() {
                    // 네이버 로그인 인증이 성공했을 때 수행할 코드 추가
                    NaverIdLoginSDK.getAccessToken()
//                var naverRefreshToken = NaverIdLoginSDK.getRefreshToken()
//                var naverExpiresAt = NaverIdLoginSDK.getExpiresAt().toString()
//                var naverTokenType = NaverIdLoginSDK.getTokenType()
//                var naverState = NaverIdLoginSDK.getState().toString()

                    //로그인 유저 정보 가져오기
                    NidOAuthLogin().callProfileApi(object : NidProfileCallback<NidProfileResponse> {
                        override fun onSuccess(response: NidProfileResponse) {
                            login(response.profile?.id ?: "", "naver")
//                            mainActivity.addFragment(LanguageFragment())
//                            Toast.makeText(requireContext(), "네이버 아이디 로그인 성공! ${userId}", Toast.LENGTH_SHORT).show()
                        }
                        override fun onFailure(httpStatus: Int, message: String) {
                            val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                            val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
                            Toast.makeText(requireContext(), "errorCode: ${errorCode}\n" +
                                    "errorDescription: ${errorDescription}", Toast.LENGTH_SHORT).show()
                        }
                        override fun onError(errorCode: Int, message: String) {
                            onFailure(errorCode, message)
                        }
                    })
                }
                override fun onFailure(httpStatus: Int, message: String) {
                    val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                    val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
                    Toast.makeText(requireContext(), "errorCode: ${errorCode}\n" +
                            "errorDescription: ${errorDescription}", Toast.LENGTH_SHORT).show()
                }
                override fun onError(errorCode: Int, message: String) {
                    onFailure(errorCode, message)
                }
            })
        }
    }

    fun login(id: String, type: String){
        CoroutineScope(Dispatchers.IO).launch {
            val response = mainActivity.loginService.login(id, type)
            flag = false
            if(response.isSuccessful){
                mainActivity.user = mainActivity.loginService.login(id, type).body()
                if(mainActivity.user!!.uid != null){
                    mainActivity.changeFragment(MainFragment())
                    App.sharedPreferencesUtil.setUser(response.body()!!)
                } else {
                    mainActivity.user!!.social_id = id
                    mainActivity.user!!.type = type
                    mainActivity.addFragment(LanguageFragment())
                }
            } else {
                Log.d(TAG, "login: ${response.code()}")
            }
        }
    }

    fun fadeIn(view: View){
        if(flag){
            requireActivity().runOnUiThread {
                ObjectAnimator.ofFloat(view, "alpha", 0f, 1f).apply {
                    duration = 500
                    start()
                }
            }
        }
    }

    fun fadeOut(view: View){
        if(flag){
            requireActivity().runOnUiThread {
                ObjectAnimator.ofFloat(view, "alpha", 1f, 0f).apply {
                    duration = 500
                    start()
                }
            }
        }
    }
}