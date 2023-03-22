package com.edu.mf.repository.api

import com.edu.mf.repository.model.resolve.ResolveRequest
import com.edu.mf.repository.model.resolve.ResolveResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ResolveService {

    @GET("problem/list")
    suspend fun getResolves(@Query("userId") userId : String) : Response<List<ResolveResponse>>

    @POST("problem/save")
    suspend fun insertResolve(@Body resolveRequest : ResolveRequest) : Response<Boolean>
}