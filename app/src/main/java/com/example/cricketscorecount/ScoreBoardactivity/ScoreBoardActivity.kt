package com.example.cricketscorecount.ScoreBoardactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import com.example.cricketscorecount.R
import com.example.cricketscorecount.databinding.ActivityScoreBoardBinding
import com.example.cricketscorecount.databinding.ActivityTeamDetailsBinding

class ScoreBoardActivity : AppCompatActivity() {
    lateinit var binding : ActivityScoreBoardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScoreBoardBinding.inflate(layoutInflater)
        //setContentView(R.layout.activity_score_board)
        setContentView(binding.root)

        binding.teamHeader.setText(intent.getStringExtra("team1"))

        binding.swap.setOnClickListener{

        }

    }
}