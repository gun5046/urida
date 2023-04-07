package com.edu.mf.repository.api

import com.edu.mf.repository.model.community.*
import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface CommunityService {
    // 게시글 작성
    @Multipart
    @POST("board/create")
    fun createBoard(
        @Part image: MultipartBody.Part?,
        @Part("articleRequestDto") createBoardData: CreateBoardData
    ): Call<CreateBoardResponse>

    // 게시글 리스트 보기(자유/그림)
    @GET("board/list/{category_id}")
    fun getBoardList(@Path("category_id") categoryId: Int): Call<List<BoardListItem>>

    // 좋아요 상태 받아오기
    @GET("board/{board_id}/{uid}")
    fun getLikeState(@Path("board_id") boardId: Int, @Path("uid") uid: Int): Call<Boolean>

    // 좋아요 업데이트
    @PUT("board/like/{board_id}/{uid}")
    fun updateLikeState(
        @Path("board_id") boardId: Int,
        @Path("uid") uid: Int,
        @Body clickLike: Boolean
    ): Call<Boolean>

    // 댓글 목록 받아오기
    @GET("board/comment/{board_id}")
    fun getCommentList(@Path("board_id") boardId: Int): Call<List<CommentListItem>>

    // 댓글 작성
    @POST("board/comment")
    fun createComment(@Body createCommentData: CreateCommentData): Call<CommentListItem>

    // 게시글 정보
    @GET("board/{id}")
    fun getBoardInfo(@Path("id") boardId: Int): Call<BoardInfo>

    // 댓글 수정
    @PUT("board/comment/{id}")
    fun updateComment(
        @Path("id") commentId: Int,
        @Body createCommentData: CreateCommentData
    ): Call<CommentListItem>

    // 댓글 삭제
    @DELETE("board/comment/{id}")
    fun deleteComment(@Path("id") commentId: Int): Call<Int>

    // 내가 쓴 게시글 목록 받기
    @GET("board/list/{category_id}/{uid}")
    fun getMyBoardList(
        @Path("category_id") categoryId: Int,
        @Path("uid") uid:Int
    ): Call<List<BoardListItem>>

    // 좋아요 게시글 목록 받기
    @GET("board/list/liked/{category_id}/{uid}")
    fun getLikeBoardList(
        @Path("category_id") categoryId: Int,
        @Path("uid") uid:Int
    ): Call<List<BoardListItem>>

    // 내가 작성한 댓글 목록 받기
    @GET("board/comment/list/{category_id}/{uid}")
    fun getMyCommentList(
        @Path("category_id") categoryId: Int,
        @Path("uid") uid:Int
    ): Call<List<MyCommentResponse>>

    // 게시글 수정
    @PUT("board/{id}")
    fun updateBoard(
        @Path("id") boardId: Int,
        @Body updateBoardData: UpdateBoardData
    ): Call<UpdateBoardResponse>

    // 게시글 삭제
    @DELETE("board/{id}")
    fun deleteBoard(@Path("id") boardId: Int): Call<Void>
}