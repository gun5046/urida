package com.edu.mf.view.common

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.window.SplashScreen
import com.edu.mf.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        loadSplash()
    }

    private fun loadSplash(){
        val handlers : Handler = Handler()
        handlers.postDelayed(Runnable{
            startActivity(Intent(applicationContext,MainActivity::class.java))
            finish()
        },2500)
    }

}
