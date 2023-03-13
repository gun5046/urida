package com.edu.mf.repository.api

import com.edu.mf.repository.model.User
import org.intellij.lang.annotations.Language
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserService {
    @GET("user/info")
    fun info(@Query("uid") uid: Int): Call<User>

    @GET("user/login")
    fun login(@Query("id") id: String, @Query("type") type: String): Call<User>

    @GET("user")
    fun duplicateCheck(@Query("nickname") nickname: String): Call<Boolean>

    @POST("user")
    fun languageSetting(@Query("language") language: Int, @Query("uid") uid: Int)

    @POST("user/register")
    fun register(@Body user: User): Call<User>
}