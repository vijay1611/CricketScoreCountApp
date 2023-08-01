package com.example.cricketscorecount.ScoreBoardactivity


    import androidx.room.Dao
    import androidx.room.Insert
    import androidx.room.OnConflictStrategy
    import androidx.room.Query
    import com.example.cricketscorecount.matchDetails

@Dao
    interface ScoreboardDao {
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insertScoreboard(matchDetails: matchDetails)

        @Query("SELECT * FROM scoreboard WHERE batsmanName = :name")
        suspend fun getBatsmanNameByName(name: String): matchDetails?
    }

