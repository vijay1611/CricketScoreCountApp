package com.example.cricketscorecount.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.cricketscorecount.models.Batsman

@Dao
interface BatsmanDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insert(batsman: Batsman)
}


