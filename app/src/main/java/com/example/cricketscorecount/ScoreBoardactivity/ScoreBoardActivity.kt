package com.example.cricketscorecount.ScoreBoardactivity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.widget.Adapter
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cricketscorecount.R
import com.example.cricketscorecount.database.BatsmanDAO
import com.example.cricketscorecount.database.CricketDao
import com.example.cricketscorecount.database.CricketDatabase
import com.example.cricketscorecount.database.RunsDao
import com.example.cricketscorecount.databinding.ActivityHistoryBinding
import com.example.cricketscorecount.databinding.ActivityScoreBoardBinding
import com.example.cricketscorecount.databinding.ActivityTeamDetailsBinding
import com.example.cricketscorecount.history.historyAdapter
import com.example.cricketscorecount.models.Batsman
import com.example.cricketscorecount.models.Runs
import com.example.cricketscorecount.models.Team
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

class ScoreBoardActivity : AppCompatActivity(),OnClickListener {
    lateinit var binding: ActivityScoreBoardBinding
    lateinit var adapter: scoreAdapter
    private lateinit var database: CricketDatabase
    private lateinit var dao1: BatsmanDAO
    private lateinit var dao2: RunsDao
    var balls=0
    var overs=0
     var count1:Int =0
    var count2:Int =0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityScoreBoardBinding.inflate(layoutInflater)
        //setContentView(R.layout.activity_score_board)
        setContentView(binding.root)
        val recyclerview =  binding.rvScoreBoard
       // recyclerview.layoutManager = LinearLayoutManager(this)

        database = CricketDatabase.getDatabase(context = this)
        //adapter = scoreAdapter()
        dao2 = database.runsDao()
        /*adapter = scoreAdapter(ArrayList())
        binding.rvScoreBoard.adapter = adapter*/
        scoreperballs()

        binding.teamHeader.setText(intent.getStringExtra("team1")+" ")
       dao1 = CricketDatabase.getDatabase(this).batsmanDao()

        binding.sbBat1.setOnClickListener {
            showAddUserDialog(it)
        }
        binding.sbBat2.setOnClickListener {
            showAddUserDialog(it)
        }
        binding.sbBowl1.setOnClickListener {
            showAddUserDialog(it)
        }

        binding.score1.setOnClickListener(this)
        binding.score2.setOnClickListener(this)
        binding.score3.setOnClickListener(this)
        binding.score4.setOnClickListener(this)
        binding.score5.setOnClickListener(this)
        binding.score6.setOnClickListener(this)

    }

    @SuppressLint("MissingInflatedId")
    private fun showAddUserDialog(view: View) {
        var i=1
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_batsman, null)

        val alertDialog = AlertDialog.Builder(this)
            .setTitle("Add Batsman "+i)
            .setView(dialogView)
            .setPositiveButton("Save") { dialog, _ ->
              val  name= dialogView.findViewById<EditText>(R.id.batman).text.toString()
                (view as TextView).text=name

                val newUser = Batsman(name = name)
               // insertUser(newUser)
                CoroutineScope(Dispatchers.IO).launch {
                    dao1.insert(Batsman(name = name))

                    Log.e("batting","inserted")
                }

                dialog.dismiss()



            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        alertDialog.show()
        i++


    }


    private fun scoreperballs(){
        CoroutineScope(Dispatchers.IO).launch {
           // val hisData = dao2.fetchAllDates()

            runOnUiThread{

                adapter = scoreAdapter(ArrayList())
                adapter.notifyDataSetChanged()
               binding.rvScoreBoard.adapter = adapter
            }

        }
    }

    private fun insertRun(runs: Runs) {
        CoroutineScope(Dispatchers.IO).launch {
            dao2.insert(runs)

        }
    }

    override fun onClick(v: View?) {
        if(CheckAllFields()){
        when(v){
            binding.score1->runScore(1)
            binding.score2->runScore(2)
            binding.score3->runScore(3)
            binding.score4->runScore(4)
            binding.score5->runScore(5)
            binding.score6->runScore(6)
    }}

}

        private fun runScore(num :Int){
            //binding.sbBat1Score1.visibility=View.VISIBLE
            if(num%2!=0){
                val bat1=binding.sbBat1.text
                val bat2=binding.sbBat2.text
                val score1 = (binding.sbBat1Score.text.toString()).toInt()+num
                val score2 = (binding.sbBat2Score.text.toString()).toInt()
                binding.sbBat1.text=bat2
                binding.sbBat2.text=bat1
                binding.sbBat2Score.text=score1.toString()
                binding.sbBat1Score.text=score2.toString()
                balls += 1

                val run:Runs= Runs(runs = num,ballCount = balls)
                adapter.addRun(run)
                insertRun(Runs(runs=num, ballCount = balls))
                binding.oversValue.text=overs.toString()+"."+balls.toString()
                binding.teaHeaderScore.text=(score1+score2).toString()+"/"+"0"
                if(overs==2&&balls==6){
                   Toast.makeText(this,"First innings completed",Toast.LENGTH_SHORT).show()
                }
                if (balls==6){
                    balls=0
                    overs+=1

                }


              //  scoreperballs()

            }else{
                val score1 = (binding.sbBat1Score.text.toString()).toInt()+num
                val score2 = (binding.sbBat2Score.text.toString()).toInt()
                binding.sbBat1Score.text=score1.toString()
                balls += 1
                val run:Runs=Runs(runs = num,ballCount = balls)
                adapter.addRun(run)
                insertRun(Runs(runs=num,ballCount = balls))
                binding.oversValue.text=overs.toString()+"."+balls.toString()
                binding.teaHeaderScore.text=(score1+score2).toString()+"/"+"0"
                if (balls==6){
                    balls=0
                    overs+=1
                }

            }

        }
    fun CheckAllFields(): Boolean {
        if (binding.sbBat1.length() == 0) {
            binding.sbBat1!!.setError("Please enter the Team1")
            return false
        }
        if (binding.sbBat2!!.length() === 0) {
            binding.sbBat1!!.setError("please enter the team2")
            return false
        }
        if (binding.sbBowl1!!.length() === 0) {
            binding.sbBowl1!!.setError("Email is required")
            return false
        }


        // after all validation return true.
        return true
    }

}



