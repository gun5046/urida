package com.edu.mf.repository.model.community

import com.edu.mf.repository.model.User
import com.google.gson.annotations.SerializedName

data class CreateBoardResponse(
    @SerializedName("board_id")
    val boardId: Int,
    val title: String,
    val content: String,
    val image: String?,
    val view: Int,
    val time: String,
    @SerializedName("category_id")
    val categoryId: Int,
    val user: User,
    val comment: ArrayList<CommentResponse>
)

data class CommentResponse(
    @SerializedName("comment_id")
    val commentId: Int,
    val content: String,
    val dateTime: String,
    val board: String,
    val user: User
)

data class BoardListItem(
    @SerializedName("board_id")
    val boardId: Int,
    val title: String,
    val content: String,
    @SerializedName("category_id")
    val categoryId: Int,
    val view: Int,
    val image: String?,
    val dateTime: String,
    val likeCnt: Int,
    val commentCnt: Int,
    val nickname: String
)

data class CommentListItem(
    @SerializedName("comment_id")
    val commentId: Int,
    val content: String,
    val dateTime: String,
    @SerializedName("board_id")
    val boardId: Int,
    val uid: Int,
    val nickname: String
)

data class BoardInfo(
    @SerializedName("board_id")
    val boardId: Int,
    val title: String,
    val content: String,
    val image: String,
    val view: Int,
    val dateTime: String,
    val likeCnt: Int,
    val commentCnt: Int,
    val nickname: String,
    val comment: ArrayList<CommentListItem>
)

data class UpdateBoardResponse(
    @SerializedName("board_id")
    val boardId: Int,
    val title: String,
    val content: String,
    val image: String,
    val view: Int,
    val dateTime: String,
    val likeCnt: Int,
    val nickname: String,
    val comment: ArrayList<CommentResponse>,
    val commentCnt: Int
)

data class MyCommentResponse(
    @SerializedName("comment_id")
    val commentId: Int,
    val content: String,
    val dateTime: String,
    @SerializedName("board_id")
    val boardId: Int,
    @SerializedName("board_title")
    val boardTitle: String,
    val uid: Int,
    val nickname: String
)