package com.edu.mf.repository.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.edu.mf.repository.model.study.Problem

@Database(entities = [Problem::class],version=1)
abstract class ProblemDatabase : RoomDatabase(){
    abstract val problemDao : ProblemDao

    companion object{
        @Volatile
        private var INSTANCE : ProblemDatabase? = null
        fun getInstance(context: Context) : ProblemDatabase{
            synchronized(this){
                var instance:ProblemDatabase? = INSTANCE
                if(instance==null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ProblemDatabase::class.java,
                        "subscriber_data_database"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}