package com.edu.mf.repository.api

import com.edu.mf.repository.model.community.CreateBoardData
import com.edu.mf.repository.model.community.CreateBoardResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface CommunityService {
    @POST("board/create")
    fun createBoard(@Body createBoardData: CreateBoardData): Call<CreateBoardResponse>
}