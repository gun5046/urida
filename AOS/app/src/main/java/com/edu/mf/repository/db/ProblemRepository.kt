package com.edu.mf.repository.db

import androidx.room.Insert
import androidx.room.Query
import com.edu.mf.repository.model.study.Problem

class ProblemRepository(private val problemDao: ProblemDao) {


    @Insert
    suspend fun insert(problem : Problem) : Long = problemDao.insertProblem(problem)
    suspend fun select(idx : Int) : List<Problem> = problemDao.selectProblem(idx)
    suspend fun selectAll() : List<Problem> = problemDao.selectAllProlbem()
    suspend fun selectProblemById(id:Int) : Problem  = problemDao.selectProblemById(id)

}