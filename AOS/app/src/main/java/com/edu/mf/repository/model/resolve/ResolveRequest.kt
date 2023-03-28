package com.edu.mf.repository.model.resolve

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ResolveRequest(
    @Expose @SerializedName("answer_id")
    var answer_id : Int,

    @Expose @SerializedName("category_id")
    var category_id : Int,

    @Expose @SerializedName("sentence_id")
    var sentence_id : Int,

    @Expose @SerializedName("type")
    var type : Int,

    @Expose @SerializedName("wrong_cnt")
    var wrong_cnt : Int,

    @Expose @SerializedName("uid")
    var uid : Int,

    @Expose @SerializedName("examples")
    var examples : List<Int>,

    @Expose @SerializedName("choices")
    var choices : ArrayList<Int>
){

}
