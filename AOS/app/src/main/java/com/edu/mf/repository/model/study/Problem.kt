package com.edu.mf.repository.model.study

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "problem_table")
data class Problem(
    @SerializedName("category_id")
    var category_id : Int,
    @SerializedName("order_id")
    var order_id : Int,
    @SerializedName("problem_category_id")
    var problem_category_id : Int,
    @SerializedName("false_count")
    var false_count : Int = 1
){
    @PrimaryKey(autoGenerate = true)
    @SerializedName("problem_id")
    var problem_id : Int = -1

    constructor(problem_id:Int,category_id: Int,order_id: Int,problem_category_id: Int,false_count: Int) : this(category_id, order_id, problem_category_id, false_count){
        this.problem_id = problem_id
    }
}
