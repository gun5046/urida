package com.edu.mf.repository.api

import com.edu.mf.repository.model.resolve.ResolveRequest
import com.edu.mf.repository.model.resolve.ResolveResponse
import retrofit2.Response
import retrofit2.http.*

interface ResolveService {

    @GET("problem/list")
    suspend fun getResolves(@Query("userId") userId : Int) : Response<List<ResolveResponse>>

    @POST("problem/save")
    suspend fun insertResolve(@Body resolveRequest : ResolveRequest) : Response<Boolean>

    @DELETE("problem/delete")
    suspend fun deleteResolve(@Query("proId") id:Int)

    @PUT("problem/update")
    suspend fun updateResolve(@Query("proId") id:Int)
}