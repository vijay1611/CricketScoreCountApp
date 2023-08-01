package com.example.cricketscorecount

import androidx.room.*
import androidx.room.Entity

@Entity
data class matchDetails(
     @PrimaryKey(autoGenerate = true) var id:Int,
     @ColumnInfo(name="batsmanName")
    var batsmanName:String,
     @ColumnInfo(name="bowlerName")
    var bowlerName:String,
     @ColumnInfo(name="run")
    var run:Int,
     @ColumnInfo(name="over")
    var over:Int,

)



data class RunDetails(
    var over: Int,
    var ballInOver:Int,
    var run: Int,
    var wicket:Boolean,
    var wide:Boolean,
    var noBall:Boolean
)
