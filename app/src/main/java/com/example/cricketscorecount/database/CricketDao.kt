package com.example.cricketscorecount.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.cricketscorecount.models.Team

@Dao
interface CricketDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(team : Team)


}