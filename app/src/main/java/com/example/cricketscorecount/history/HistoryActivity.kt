package com.example.cricketscorecount.history

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cricketscorecount.R
import com.example.cricketscorecount.ScoreBoardactivity.ScoreBoardActivity
import com.example.cricketscorecount.database.CricketDao
import com.example.cricketscorecount.database.CricketDatabase
import com.example.cricketscorecount.databinding.ActivityHistoryBinding
import com.example.cricketscorecount.models.Team
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity(),historyClickListener {
    private lateinit var database: CricketDatabase
    private lateinit var dao: CricketDao
    lateinit var binding: ActivityHistoryBinding
    private lateinit var adapter: historyAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        //setContentView(R.layout.activity_history)
        setContentView(binding.root)
        val recyclerview = binding.hisRecycle
        recyclerview.layoutManager = LinearLayoutManager(this)
        database = CricketDatabase.getDatabase(context = this)
        dao = database.getCricketDao()

        CoroutineScope(Dispatchers.IO).launch {
            val hisData = dao.fetchAllDates()

            runOnUiThread{
                val lastFive = hisData.reversed()
                adapter = historyAdapter(lastFive,this@HistoryActivity)
                recyclerview.adapter = adapter
            }

        }

    }

    override fun showSummary(team1: String, team2: String) {

    }
}