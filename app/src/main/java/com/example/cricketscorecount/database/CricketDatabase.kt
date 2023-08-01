package com.example.cricketscorecount.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cricketscorecount.ScoreBoardactivity.DATABASE_NAME
import com.example.cricketscorecount.models.Team

@Database(entities = arrayOf(Team::class),version=1, exportSchema = false)
abstract class CricketDatabase : RoomDatabase(){

    abstract fun getNoteDao(): CricketDao

    companion object{
        @Volatile
        private var INSTANCE: CricketDatabase? = null

        fun getDatabase(context: Context):CricketDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CricketDatabase::class.java,
                    DATABASE_NAME
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}