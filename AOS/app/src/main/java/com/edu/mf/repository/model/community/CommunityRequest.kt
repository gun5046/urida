package com.edu.mf.repository.model.community

import com.google.gson.annotations.SerializedName

data class CreateBoardData(
    val title: String,
    val content: String,
    @SerializedName("category_id")
    val categoryId: Int,
    val uid: Int
)

data class CreateCommentData(
    val content: String,
    val dateTime: String,
    @SerializedName("board_id")
    val boardId: Int,
    val uid: Int
)

data class UpdateBoardData(
    val title: String,
    val content: String,
    val dateTime: String
)
