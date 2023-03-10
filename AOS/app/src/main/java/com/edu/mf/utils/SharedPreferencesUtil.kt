package com.edu.mf.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesUtil(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("", Context.MODE_PRIVATE)

    fun setLanguageCode(code: Int){
        prefs.edit().putInt("LanguageCode", code).commit()
    }

    fun getLanguageCode(): Int{
        return prefs.getInt("LanguageCode", 0)
    }

}