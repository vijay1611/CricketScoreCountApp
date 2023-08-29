package com.example.cricketscorecount.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "batsman_table")
data class Batsman(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    val name: String
)
