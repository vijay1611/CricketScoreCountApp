package com.example.cricketscorecount.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "runs_table")
data class Runs (
    @PrimaryKey(autoGenerate = true)
    var ballCount:Int=1,
    var runs:Int
)


