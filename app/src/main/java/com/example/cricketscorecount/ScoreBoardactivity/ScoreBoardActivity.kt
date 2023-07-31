package com.example.cricketscorecount.ScoreBoardactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import com.example.cricketscorecount.R

class ScoreBoardActivity : AppCompatActivity() {

    companion object {
        lateinit var database: AppDatabase
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score_board)

        database = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "my-database")
            .build()

        val userDao = database.userDao()
        val u: List<User> = userDao.getAll()

    }
}