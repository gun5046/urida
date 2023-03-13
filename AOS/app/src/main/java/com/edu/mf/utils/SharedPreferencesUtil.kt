package com.edu.mf.utils

import android.content.Context
import android.content.SharedPreferences
import com.edu.mf.repository.model.User
import com.google.gson.Gson

class SharedPreferencesUtil(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("", Context.MODE_PRIVATE)

    fun setLanguageCode(code: Int){
        prefs.edit().putInt("LanguageCode", code).commit()
    }

    fun getLanguageCode(): Int{
        return prefs.getInt("LanguageCode", 0)
    }

    fun setUser(user: User){
        prefs.edit().putString("user", Gson().toJson(user))
    }

    fun getUser(): User?{
        val string = prefs.getString("user", "")
        if(string!!.isBlank()){
            return null
        }
        return Gson().fromJson(string, User::class.java)
    }
}