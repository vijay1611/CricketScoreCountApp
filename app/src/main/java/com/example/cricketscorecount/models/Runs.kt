package com.example.cricketscorecount.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "runs_table")
data class Runs (
    @PrimaryKey(autoGenerate = true)
    var sno:Long=0,
    var ballCount:Int=0,
    var runs:Int=0,
    var batsman:String="",
    var bowler:String="",
    var isWide:Boolean=false,
    var isNoBall:Boolean=false,
    var isLegBy : Boolean=false,
    var wicket : Boolean=false,
    var over:Int=0,
    var battingTeam:String= "",
    var fieldingTeam:String="",
    var totalOvers:Int=0,
//    var batsman:String
)


