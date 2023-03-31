package com.edu.mf.repository.model.community

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Part

//data class CreateBoardData(
//    val title: String,
//    val content: String,
//    @SerializedName("category_id")
//    val categoryId: Int,
//    val uid: Int
//)
//{
//    constructor(title: String, content: String, categoryId: Int, uid: Int) : this(title, content, null, categoryId, uid)
//}

data class CreateBoardData(
    val multipart: MultipartBody.Part?,
    val title: RequestBody,
    val content: RequestBody,
    val category_id: RequestBody,
    val uid: RequestBody
)

data class CreateCommentData(
    val content: String,
    val dateTime: String,
    @SerializedName("board_id")
    val boardId: Int,
    val uid: Int
)

data class UpdateBoardData(
    val content: String,
    val dateTime: String
)
