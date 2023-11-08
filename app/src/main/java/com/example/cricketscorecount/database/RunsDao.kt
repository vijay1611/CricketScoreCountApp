package com.example.cricketscorecount.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cricketscorecount.models.Runs

@Dao
interface RunsDao {
    @Insert
    fun insertRuns(vararg runs : Runs)

    @Query("SELECT * FROM `runs_table`")
    fun fetchAllDates(): Runs

   @Query("SELECT * FROM `runs_table` where battingTeam= :team1 and fieldingTeam= :team2")
    fun batsmanRun(team1:String,team2:String):List<Runs>
}