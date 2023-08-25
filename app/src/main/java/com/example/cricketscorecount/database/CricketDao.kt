package com.example.cricketscorecount.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cricketscorecount.models.Team
import kotlinx.coroutines.flow.Flow

@Dao
interface CricketDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(team : Team)

    @Query("SELECT * FROM `team_table`")
    fun fetchAllDates(): List<Team>


}