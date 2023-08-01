package com.example.cricketscorecount.models

import androidx.room.Entity

@Entity(tableName = "team_table")
data class Team(
    val team1:String,
    val team2 :String,
    val overs: Int,
    val battingBy : String
)
