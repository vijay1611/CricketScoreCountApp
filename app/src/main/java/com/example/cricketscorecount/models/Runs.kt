package com.example.cricketscorecount.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "runs_table")
data class Runs (
    @PrimaryKey(autoGenerate = true)
    var sno:Int=1,
    var ballCount:Int,
    var runs:Int,
    var batsman:String,
    var bowler:String,
    var isWide:Boolean,
    var isNoBall:Boolean,
    var isLegBy : Boolean,
    var wicket : Boolean,
    var over:Int,
    var battingTeam:String,
    var fieldingTeam:String
//    var batsman:String
)


