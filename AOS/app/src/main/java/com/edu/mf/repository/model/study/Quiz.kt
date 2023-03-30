package com.edu.mf.repository.model.study

data class Quiz(
    var answer_fi:Int,
    var answer_i : Int,
){
    var problems = ArrayList<String>()
    var problems_i = ArrayList<Int>()
    var relate_categories = ArrayList<Int>()
    var answer_s : String = ""
    var problem = Problem(-1,"",-1,-1)

    //그림보고 낱말 맞추기
    constructor(answer_fi:Int,answer_i:Int,answer_s : String,problems: ArrayList<String>) : this(answer_fi, answer_i){
        this.answer_s = answer_s
        this.problems = problems
    }

    //낱말보고 그림 맞추기
    constructor(answer_fi:Int,answer_i:Int,problems_i:ArrayList<Int>) : this(answer_fi, answer_i){
        this.problems_i = problems_i
    }

    //연관된 단어 맞추기
    constructor(answer_fi:Int, answer_i: Int, problems_i: ArrayList<Int>, relate_categories:ArrayList<Int>) : this(answer_fi, answer_i){
        this.problems_i = problems_i
        this.relate_categories = relate_categories
    }

    //빈칸 넣기
    constructor(answer_fi:Int,answer_i:Int,problems_i:ArrayList<Int>,problem:Problem) : this(answer_fi, answer_i){
        this.problems_i = problems_i
        this.problem = problem
    }
    override fun toString(): String {
        return "Quiz(answer_fi=$answer_fi, answer_i=$answer_i, problems=$problems, problems_i=$problems_i, relate_categories=$relate_categories, answer_s='$answer_s')"
    }


}
