package com.example.cricketscorecount.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "team_table")
data class Team(
    @PrimaryKey(autoGenerate = true)
    var id : Int=0,
    var team1:String?=null,
    var team2 :String?=null,
    var overs: Int?=null,
    var battingBy : String?=null
)
