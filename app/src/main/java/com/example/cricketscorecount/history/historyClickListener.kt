package com.example.cricketscorecount.history

import com.example.cricketscorecount.models.Team

interface historyClickListener {
    fun showSummary(team: Team)
}