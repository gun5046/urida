package com.edu.mf.repository.model

import androidx.fragment.app.Fragment

data class Category(
    val title : String,
    val description : String,
    val src : Int,
    val fragment:Fragment
)
