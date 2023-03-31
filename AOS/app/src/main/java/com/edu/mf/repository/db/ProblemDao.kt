package com.edu.mf.repository.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.edu.mf.repository.model.study.Problem

@Dao
interface ProblemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProblem(problem:Problem) : Long

    @Query("SELECT * FROM problem_table")
    suspend fun selectAllProlbem(): List<Problem>

    @Query("SELECT * FROM problem_table WHERE category_id = :ids")
    suspend fun selectProblem(ids:Int) : List<Problem>

    @Query("SELECT * FROM problem_table WHERE id = :id")
    suspend fun selectProblemById(id:Int) : Problem
}