package com.edu.mf.repository.api

import com.edu.mf.repository.model.User
import org.intellij.lang.annotations.Language
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserService {

    @GET("user/info")
    fun info(@Query("uid") uid: Int): Response<User>

    @GET("user/login")
    suspend fun login(@Query("id") id: String, @Query("type") type: String): Response<User>

    @GET("user")
    suspend fun duplicateCheck(@Query("nickname") nickname: String): Response<Boolean>

    @POST("user")
    suspend fun languageSetting(@Query("language") language: Int, @Query("uid") uid: Int)

    @POST("user/register")
    suspend fun register(@Body user: User): Response<User>
}