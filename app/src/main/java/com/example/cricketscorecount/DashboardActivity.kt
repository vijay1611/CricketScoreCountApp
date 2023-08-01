package com.example.cricketscorecount

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class DashboardActivity : AppCompatActivity() {

    lateinit var btnNewGame: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        btnNewGame = findViewById(R.id.newGame  )

        btnNewGame.setOnClickListener{
            startActivity(Intent(this,TeamDetailsActivity::class.java))
        }
    }
}