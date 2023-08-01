package com.example.cricketscorecount

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.cricketscorecount.ScoreBoardactivity.ScoreBoardActivity
import com.example.cricketscorecount.databinding.ActivityTeamDetailsBinding

class TeamDetailsActivity : AppCompatActivity() {

    lateinit var btnSubmit: Button

    lateinit var binding : ActivityTeamDetailsBinding


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_team_details)
        binding = ActivityTeamDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        btnSubmit = findViewById(R.id.teamDetails_submitBtn)
        btnSubmit.setOnClickListener{
            val team1 = binding.teamname1.text
            val team2 = binding.teamname2.text
            val overs = binding.overs.text
            val battingBy = binding.

            startActivity(Intent(this,ScoreBoardActivity::class.java))
        }
    }
}