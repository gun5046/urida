package com.edu.mf.repository.model.community

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody

data class CreateBoardData(
    val title: String,
    val content: String,
    val image: MultipartBody.Part?,
    @SerializedName("category_id")
    val categoryId: Int,
    val uid: Int
){
    constructor(title: String, content: String, categoryId: Int, uid: Int) : this(title, content, null, categoryId, uid)
}

data class CreateCommentData(
    val content: String,
    val dateTime: String,
    @SerializedName("board_id")
    val boardId: Int,
    val uid: Int
)
