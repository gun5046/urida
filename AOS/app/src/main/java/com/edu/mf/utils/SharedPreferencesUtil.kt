package com.edu.mf.utils

import android.content.Context
import android.content.SharedPreferences
import com.edu.mf.repository.model.User
import com.google.gson.Gson

class SharedPreferencesUtil(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("datas", Context.MODE_PRIVATE)


    fun setFruitsBookMark(page:Int){
        prefs.edit().putInt("fruits",page).commit()
    }
    fun getFruitsBookMark() = prefs.getInt("fruits",0)

    fun setJobsBookMark(page:Int){
        prefs.edit().putInt("jobs",page).commit()
    }
    fun getJobsBookMark() = prefs.getInt("jobs",0)

    fun setAnimalsBookMark(page:Int){
        prefs.edit().putInt("animals",page).commit()
    }
    fun getAnimalsBookMark() = prefs.getInt("animals",0)

    fun setObjectsBookMark(page:Int){
        prefs.edit().putInt("objects",page).commit()
    }
    fun getObjectsBookMark() = prefs.getInt("objects",0)

    fun setPlacesBookMark(page:Int){
        prefs.edit().putInt("places",page).commit()
    }
    fun getPlacesBookMark() = prefs.getInt("places",0)

    fun setActionsBookMark(page:Int){
        prefs.edit().putInt("actions",page).commit()
    }
    fun getActionsBookMark() = prefs.getInt("actions",0)

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