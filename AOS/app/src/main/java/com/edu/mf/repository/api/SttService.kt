package com.edu.mf.repository.api

import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface SttService {
    @Multipart
    @POST("stt/audiopredict/")
    suspend fun sttResult(@Part audioFile : MultipartBody.Part): Response<String>
}