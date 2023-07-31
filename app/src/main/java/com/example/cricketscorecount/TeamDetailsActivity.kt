package com.example.cricketscorecount

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.cricketscorecount.ScoreBoardactivity.ScoreBoardActivity

class TeamDetailsActivity : AppCompatActivity() {

    lateinit var btnSubmit: Button
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_details)

        btnSubmit = findViewById(R.id.teamDetails_submitBtn)
        btnSubmit.setOnClickListener{
            startActivity(Intent(this,ScoreBoardActivity::class.java))
        }
    }
}