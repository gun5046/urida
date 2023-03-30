package com.edu.mf.repository.model.resolve

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ResolveResponse(

    @SerializedName("answer_id")
    var answer_id : Int,
    @SerializedName("category_id")
    var category_id : Int,
    @SerializedName("pro_id")
    var pro_id : Int,
    @SerializedName("sentence_id")
    var sentence_id : Int,
    @SerializedName("type")
    var type : Int,
    @SerializedName("wrong_cnt")
    var wrong_cnt : Int,
    @SerializedName("choices")
    var choices : List<Int>,
    @SerializedName("examples")
    var examples : List<Int>,
    @SerializedName("titles")
    var titles : ArrayList<Int>

){

}
