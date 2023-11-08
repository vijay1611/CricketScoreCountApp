package com.example.cricketscorecount.summary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cricketscorecount.R
import com.example.cricketscorecount.ScoreBoardactivity.PlayersDetails
import com.example.cricketscorecount.database.CricketDao
import com.example.cricketscorecount.database.CricketDatabase
import com.example.cricketscorecount.database.RunsDao
import com.example.cricketscorecount.databinding.ActivityHistoryBinding
import com.example.cricketscorecount.databinding.ActivitySummaryBinding
import com.example.cricketscorecount.history.historyAdapter
import com.example.cricketscorecount.models.Runs
import com.example.cricketscorecount.models.Team
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.w3c.dom.Text

class SummaryActivity : AppCompatActivity() {
    private lateinit var database: CricketDatabase
    private lateinit var dao: RunsDao
    lateinit var binding: ActivitySummaryBinding
    private lateinit var adapter1: SummaryAdapter
    private lateinit var adapter2: SummaryAdapter
    lateinit var team1:TextView
    lateinit var team2:TextView
    lateinit var rv1 : RecyclerView
    lateinit var rv2 : RecyclerView
    lateinit var team1Score : TextView
    lateinit var team2Score : TextView
    var btName1: ArrayList<PlayersDetails> = arrayListOf<PlayersDetails>()
    var btName2: ArrayList<PlayersDetails> = arrayListOf<PlayersDetails>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySummaryBinding.inflate(layoutInflater)
        //setContentView(R.layout.activity_history)
        setContentView(binding.root)
        val recyclerview1 = binding.rvSummary1
        val recyclerview2 = binding.rvSummary2
        recyclerview1.layoutManager = LinearLayoutManager(this)
        recyclerview2.layoutManager = LinearLayoutManager(this)
        database = CricketDatabase.getDatabase(context = this)
        dao = database.runsDao()
        var hisData = ArrayList<PlayersDetails>()
        var hisData2 = ArrayList<PlayersDetails>()
        adapter1 = SummaryAdapter(hisData)
        adapter2 = SummaryAdapter(hisData2)
        team1 = binding.summaryTeam1
        team2 = binding.summaryTeam2
        team1Score=binding.summaryTeam1Score
        team2Score=binding.summaryTeam2Score

        var runsModel : ArrayList<Runs> = ArrayList()
        var playersDetails : ArrayList<PlayersDetails> = ArrayList()


        var team1Name=intent.getStringExtra("team1")
        var team2Name=intent.getStringExtra("team2")
        team1.setText(team1Name)
        team2.setText(team2Name)


        recyclerview1.adapter = adapter1
        recyclerview2.adapter = adapter2



        CoroutineScope(Dispatchers.IO).launch {
            val batRun1 = dao.batsmanRun(team1Name.toString(),team2Name.toString())
            val batRun2 = dao.batsmanRun(team2Name.toString(),team1Name.toString())

            Log.e("***********",batRun2.toString())
            val team1Final=calculateRun(batRun1)
            val team2Final=calculateRun(batRun2)
            Log.e("***********",team2Final.toString())
          //  val sumRuns = batRun.batsman

            runOnUiThread{
               // val lastFive = hisData.reversed()
               // adapter = SummaryAdapte(lastFive,this@HistoryActivity)

                team1Score.text = batRun1.sumOf { it.runs }.toString()
                team2Score.text = batRun2.sumOf { it.runs }.toString()
                adapter1.setvalue(team1Final)
               adapter2.setvalue(team2Final)

            }

        }



    }
    fun calculateRun(btRun:List<Runs>):ArrayList<PlayersDetails> {
        var btName: ArrayList<String> = arrayListOf<String>()
        var list: ArrayList<PlayersDetails> = arrayListOf<PlayersDetails>()
        btRun.distinctBy { it.batsman }.forEach { itFor ->
            if (!btName.contains(itFor.batsman)) {
                btName.add(itFor.batsman)
                val score = btRun.filter { it1 -> it1.batsman == itFor.batsman }.sumOf {
                    it.runs
                }
                list.add(PlayersDetails(batsmanName = itFor.batsman, score))
            }
        }
        return list
    }
}