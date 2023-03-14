package com.edu.mf.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.edu.mf.repository.model.User
import com.google.gson.Gson

class SharedPreferencesUtil(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("", Context.MODE_PRIVATE)

    fun setUser(user: User){
        prefs.edit().putString("user", Gson().toJson(user)).commit()
    }

    fun getUser(): User?{
        val string = prefs.getString("user", "")
        if(string!!.isBlank()){
            return null
        }
        return Gson().fromJson(string, User::class.java)
    }
}