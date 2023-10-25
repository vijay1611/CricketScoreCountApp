package com.example.cricketscorecount.ScoreBoardactivity

import android.annotation.SuppressLint
import android.content.Intent
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
import com.example.cricketscorecount.DashboardActivity
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
    var overs:Int=0
     var count1:Int =0
    var count2:Int =0
    var totalOvers:Int=0
    var targetBat1:Int =0
    var team1:String=""
    var team2:String=""
    var battingBy:String =""
    var firstBatting:String=""
    var secondBatting:String=""
    var batting:Int=1
    var headScore : Int=0
    var wicket : Int =0
     var bowler : String = ""
    var batsman : String = ""
    var over:Double=0.0

    var runsList:ArrayList<Runs> = ArrayList<Runs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityScoreBoardBinding.inflate(layoutInflater)
        //setContentView(R.layout.activity_score_board)
        setContentView(binding.root)
        val recyclerview =  binding.rvScoreBoard
       // recyclerview.layoutManager = LinearLayoutManager(this)

        database = CricketDatabase.getDatabase(context = this)
        adapter = scoreAdapter(runsList)
        dao2 = database.runsDao()
        /*adapter = scoreAdapter(ArrayList())
        binding.rvScoreBoard.adapter = adapter*/
        scoreperballs()


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
        binding.swap.setOnClickListener {
            swapFunction()
        }
        binding.stop.setOnClickListener{
            exitFunction()
        }
        binding.undo.setOnClickListener{
            undoFunction()
        }
        totalOvers = intent.getIntExtra("overs",0)
        battingBy=intent.getStringExtra("battingBy")!!
        binding.teamHeader.text = battingBy




        binding.score1.setOnClickListener(this)
        binding.score2.setOnClickListener(this)
        binding.score3.setOnClickListener(this)
        binding.score4.setOnClickListener(this)
        binding.score5.setOnClickListener(this)
        binding.score6.setOnClickListener(this)
        binding.wide.setOnClickListener(this)

    }


    fun swapFunction(){
        val bat2:String=binding.sbBat2.text.toString()
        binding.sbBat1.text=bat2
        binding.sbBat2.text=batsman
        binding.sbBat2Score.text= runsList.filter { it-> it.batsman==batsman}.sumOf { it.runs }.toString()
        binding.sbBat1Score.text= runsList.filter { it-> it.batsman==bat2}.sumOf { it.runs }.toString()
        batsman=bat2
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
                when(view){
                    binding.sbBat1->  batsman = name
                    binding.sbBowl1 -> bowler = name
                }

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
      //  if(CheckAllFields()){
        when(v){
            binding.score1->runScore(1)
            binding.score2->runScore(2)
            binding.score3->runScore(3)
            binding.score4->runScore(4)
            binding.score5->runScore(5)
            binding.score6->runScore(6)
            binding.wide->wideFunction()



    }//}

}

    private fun wideFunction() {
        val run= Runs(runs = 1,ballCount = balls,isWide = true, wicket = false, isNoBall =false, isLegBy = false, batsman = batsman, bowler = bowler, battingTeam = team1, fieldingTeam = team2, over = overs)
        runsList.add(run)
        binding.oversValue.text=overs.toString()+"."+balls.toString()
        adapter.setItem(runsList)
        headScore=  runsList.filter { it-> it.battingTeam==team1}.sumOf { it.runs }
        binding.teaHeaderScore.text=headScore.toString()+"/"+ wicket
        totalOvers = intent.getIntExtra("overs",0)
    }

    fun undoFunction(){
        runsList.removeAt(runsList.size-1)
        if(balls==0){
            balls=6
            overs-=1
        }
        balls-=1

        binding.oversValue.text=overs.toString()+"."+balls.toString()
        adapter.setItem(runsList)
        headScore=  runsList.filter { it-> it.battingTeam==team1}.sumOf { it.runs }
        binding.sbBat2Score.text= runsList.filter { it-> it.batsman==binding.sbBat2Score.text.toString()}.sumOf { it.runs }.toString()
        binding.sbBat1Score.text= runsList.filter { it-> it.batsman==binding.sbBat1Score.text.toString()}.sumOf { it.runs }.toString()
        binding.teaHeaderScore.text=headScore.toString()+"/"+ wicket
        totalOvers = intent.getIntExtra("overs",0)
    }

    private fun runScore(num:Int){
            //binding.sbBat1Score1.visibility=View.VISIBLE

            if(num%2!=0){
                val run= Runs(runs = num,ballCount = balls,isWide = false, wicket = false, isNoBall =false, isLegBy = false, batsman = batsman, bowler = bowler, battingTeam = team1, fieldingTeam = team2, over = overs)
                runsList.add(run)
                val bat2:String=binding.sbBat2.text.toString()
                binding.sbBat1.text=bat2
                binding.sbBat2.text=batsman
                binding.sbBat2Score.text= runsList.filter { it-> it.batsman==batsman}.sumOf { it.runs }.toString()
                binding.sbBat1Score.text= runsList.filter { it-> it.batsman==bat2}.sumOf { it.runs }.toString()
                batsman=bat2
                balls += 1
                if(balls==6) {
                    overs+=1
                    balls=0
                }

            }else{
                val run= Runs(runs = num,ballCount = balls,isWide = false, wicket = false, isNoBall =false, isLegBy = false, batsman = batsman, bowler = bowler, battingTeam = team1, fieldingTeam = team2, over = overs)
                runsList.add(run)
                adapter.setItem( runsList)
                binding.sbBat1Score.text= runsList.filter { it-> it.batsman==batsman}.sumOf { it.runs }.toString()
                balls += 1
                if(balls==6) {
                    overs+=1
                    balls=0
                }
            }
        binding.oversValue.text=overs.toString()+"."+balls.toString()
        adapter.setItem(runsList)
        headScore=  runsList.filter { it-> it.battingTeam==team1}.sumOf { it.runs }
        binding.teaHeaderScore.text=headScore.toString()+"/"+ wicket
        totalOvers = intent.getIntExtra("overs",0)
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
    fun winningStatus(res:String){
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_batsman, null)
        val alertDialog = AlertDialog.Builder(this)
            .setTitle("Result")
            .setMessage(res)
            .setPositiveButton("ok") { dialog, _ ->
                startActivity(Intent(ScoreBoardActivity@this,DashboardActivity::class.java))
                dialog.dismiss()
            }
            .setNegativeButton("wait") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
        alertDialog.show()
    }
    fun exitFunction(){
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_batsman, null)
        val alertDialog = AlertDialog.Builder(this)
            .setTitle("Do you want to stop the match")
            .setPositiveButton("Yes") { dialog, _ ->
                startActivity(Intent(ScoreBoardActivity@this,DashboardActivity::class.java))
                dialog.dismiss()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
        alertDialog.show()
    }
}



