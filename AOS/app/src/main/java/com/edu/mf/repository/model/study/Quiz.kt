package com.edu.mf.repository.model.study

data class Quiz(
    var answer_fi:Int,
    var answer_i : Int,
){
    var problems = ArrayList<String>()
    var problems_i = ArrayList<Int>()
    var answer_s : String = ""

    constructor(answer_fi:Int,answer_i:Int,answer_s : String,problems: ArrayList<String>) : this(answer_fi, answer_i){
        this.answer_s = answer_s
        this.problems = problems
    }

    constructor(answer_fi:Int,answer_i:Int,problems_i:ArrayList<Int>) : this(answer_fi, answer_i){
        this.problems_i = problems_i
    }

    override fun toString(): String {
        return "Quiz(answer_fi=$answer_fi, answer_i=$answer_i, problems=$problems, problems_i=$problems_i, answer_s='$answer_s')"
    }


}
