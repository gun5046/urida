package com.edu.mf.repository.model

data class User(
    var language: Int,
    var nickname: String?,
    var social_id: String?,
    var type: String?,
    val uid: Int?
)