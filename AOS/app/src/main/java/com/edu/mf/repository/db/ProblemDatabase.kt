package com.edu.mf.repository.db

import androidx.room.Database
import androidx.room.RoomDatabase

abstract class ProblemDatabase : RoomDatabase(){

    abstract fun problemDao() : ProblemDao
}