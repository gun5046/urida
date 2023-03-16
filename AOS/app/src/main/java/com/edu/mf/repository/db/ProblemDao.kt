package com.edu.mf.repository.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.edu.mf.repository.model.study.Problem

@Dao
interface ProblemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProblem(problem:Problem)


}