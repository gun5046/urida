package com.edu.mf.repository.api

import com.edu.mf.repository.model.community.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface CommunityService {
    @POST("board/create")
    fun createBoard(@Body createBoardData: CreateBoardData): Call<CreateBoardResponse>

    @GET("board/list/{category_id}")
    fun getBoardList(@Path("category_id") categoryId: Int): Call<List<BoardListItem>>

    @GET("board/{board_id}/{uid}")
    fun getLikeState(@Path("board_id") boardId: Int, @Path("uid") uid: Int): Call<Boolean>

    @PUT("board/like/{board_id}/{uid}")
    fun updateLikeState(
        @Path("board_id") boardId: Int,
        @Path("uid") uid: Int,
        @Body clickLike: Boolean
    ): Call<Boolean>

    @GET("board/comment/{board_id}")
    fun getCommentList(@Path("board_id") boardId: Int): Call<List<CommentListItem>>

    @POST("board/comment")
    fun createComment(@Body createCommentData: CreateCommentData): Call<CommentListItem>

    @GET("board/{id}")
    fun getBoardInfo(@Path("id") boardId: Int): Call<BoardInfo>

    @PUT("board/comment/{id}")
    fun updateComment(
        @Path("id") commentId: Int,
        @Body createCommentData: CreateCommentData
    ): Call<CommentListItem>

    @DELETE("board/comment/{id}")
    fun deleteComment(@Path("id") commentId: Int): Call<Int>
}