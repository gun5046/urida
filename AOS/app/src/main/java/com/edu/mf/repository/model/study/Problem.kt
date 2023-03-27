package com.edu.mf.repository.model.study

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "problem_table")
data class Problem(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    var id : Int,

    @ColumnInfo(name = "content")
    var content : String,
    @ColumnInfo(name="category_id")
    var category_id : Int,
    @ColumnInfo(name = "order_id")
    var order_id : Int
){

}
