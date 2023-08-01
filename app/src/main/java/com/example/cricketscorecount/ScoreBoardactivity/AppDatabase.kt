package com.example.cricketscorecount.ScoreBoardactivity

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cricketscorecount.matchDetails

@Database(entities = arrayOf( matchDetails::class), version = 1,exportSchema=false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getScoreboardDao(): ScoreboardDao

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context):AppDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
