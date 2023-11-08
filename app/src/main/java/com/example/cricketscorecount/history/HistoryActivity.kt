package com.example.cricketscorecount.history

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cricketscorecount.database.CricketDao
import com.example.cricketscorecount.database.CricketDatabase
import com.example.cricketscorecount.databinding.ActivityHistoryBinding
import com.example.cricketscorecount.models.Team
import com.example.cricketscorecount.summary.SummaryActivity
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
        val hisData = listOf<Team>()
        val adapter = historyAdapter( hisData,this)
        CoroutineScope(Dispatchers.IO).launch {

            val hisData = dao.fetchAllDates()
            runOnUiThread{
                val lastFive = hisData.reversed()
                recyclerview.adapter = adapter
                adapter.setvalue(lastFive)

            }

        }

    }

    override fun showSummary(team: Team) {
        val intent= Intent(this, SummaryActivity::class.java)
        intent.putExtra("team1",team.team1)
        intent.putExtra("team2",team.team2)
        intent.putExtra("totalOvers",team.overs)
        startActivity(intent)
    }
}