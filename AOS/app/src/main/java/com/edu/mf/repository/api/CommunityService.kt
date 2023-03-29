package com.edu.mf.repository.api

import com.edu.mf.repository.model.community.BoardListItem
import com.edu.mf.repository.model.community.CreateBoardData
import com.edu.mf.repository.model.community.CreateBoardResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface CommunityService {
    @POST("board/create")
    fun createBoard(@Body createBoardData: CreateBoardData): Call<CreateBoardResponse>

    @GET("board/{category_id}/list")
    fun getBoardList(@Path("category_id") categoryId: Int): Call<List<BoardListItem>>

    @GET("board/{board_id}/{uid}")
    fun getLikeState(@Path("board_id") boardId: Int, @Path("uid") uid: Int): Call<Boolean>

    @PUT("board/like/{board_id}/{uid}")
    fun updateLikeState(
        @Path("board_id") boardId: Int,
        @Path("uid") uid: Int,
        @Body clickLike: Boolean
    ): Call<Boolean>
}