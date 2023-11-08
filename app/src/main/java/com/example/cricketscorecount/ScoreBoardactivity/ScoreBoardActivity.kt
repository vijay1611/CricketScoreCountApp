package com.example.cricketscorecount.ScoreBoardactivity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.cricketscorecount.DashboardActivity
import com.example.cricketscorecount.R
import com.example.cricketscorecount.database.BatsmanDAO
import com.example.cricketscorecount.database.CricketDatabase
import com.example.cricketscorecount.database.RunsDao
import com.example.cricketscorecount.databinding.ActivityScoreBoardBinding
import com.example.cricketscorecount.models.Batsman
import com.example.cricketscorecount.models.Runs
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ScoreBoardActivity : AppCompatActivity(),OnClickListener {
    lateinit var binding: ActivityScoreBoardBinding
    lateinit var adapter: ScoreAdapter
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
    var isNextInnings:Boolean=true
    var firstInningsScore : Int =0
    var runsList:ArrayList<Runs> = arrayListOf<Runs>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityScoreBoardBinding.inflate(layoutInflater)
        //setContentView(R.layout.activity_score_board)
        setContentView(binding.root)
        val recyclerview =  binding.rvScoreBoard
       // recyclerview.layoutManager = LinearLayoutManager(this)
        totalOvers = intent.getIntExtra("overs", 0)
        team1 = intent.getStringExtra("team1")!!
        team2 = intent.getStringExtra("team2")!!
        database = CricketDatabase.getDatabase(context = this)
        adapter = ScoreAdapter(runsList)
        dao2 = database.runsDao()
        /*adapter = scoreAdapter(ArrayList())
        binding.rvScoreBoard.adapter = adapter*/
        scoreperballs()

       // nextInnings()

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
        binding.wicket.setOnClickListener{
            showAddUserDialog(binding.sbBat1,true)
        }
        totalOvers = intent.getIntExtra("overs",0)
        battingBy=intent.getStringExtra("battingBy")!!
        binding.teamHeader.text = battingBy+" : "




        binding.score0.setOnClickListener(this)
        binding.score1.setOnClickListener(this)
        binding.score2.setOnClickListener(this)
        binding.score3.setOnClickListener(this)
        binding.score4.setOnClickListener(this)
        binding.score5.setOnClickListener(this)
        binding.score6.setOnClickListener(this)
        binding.wide.setOnClickListener(this)
        binding.noBall.setOnClickListener(this)
        binding.legByes.setOnClickListener(this)



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
    private fun showAddUserDialog(view: View,isWicket: Boolean =false) {
        var i=1
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_batsman, null)

        val alertDialog = AlertDialog.Builder(this)
            .setTitle("Add new batsman")
            .setView(dialogView)
            .setPositiveButton("Save") { dialog, _ ->
                val  name= dialogView.findViewById<EditText>(R.id.batman).text.toString()
                if(isWicket){
                    wicketFunction(name)
                }else{

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
                adapter = ScoreAdapter(ArrayList())
                adapter.notifyDataSetChanged()
               binding.rvScoreBoard.adapter = adapter
            }
        }
    }

//    private fun insertRun(runs: Runs) {
//        CoroutineScope(Dispatchers.IO).launch {
//            dao2.insert(runs)
//        }
//    }

    override fun onClick(v: View?) {
        if(checkAllFields()){
        when(v){
            binding.score0->runScore(0)
            binding.score1->runScore(1)
            binding.score2->runScore(2)
            binding.score3->runScore(3)
            binding.score4->runScore(4)
            binding.score5->runScore(5)
            binding.score6->runScore(6)
            binding.wide->wideFunction()
            binding.noBall->noBallFunction()
            binding.legByes->legByFunction()



    }
            nextInnings()

        }

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
    private fun noBallFunction() {
        val run= Runs(runs = 1,ballCount = balls,isWide = false, wicket = false, isNoBall =true, isLegBy = false, batsman = batsman, bowler = bowler, battingTeam = team1, fieldingTeam = team2, over = overs)
        runsList.add(run)
        binding.oversValue.text=overs.toString()+"."+balls.toString()
        adapter.setItem(runsList)
        headScore=  runsList.filter { it-> it.battingTeam==team1}.sumOf { it.runs }
        binding.teaHeaderScore.text=headScore.toString()+"/"+ wicket
        totalOvers = intent.getIntExtra("overs",0)
    }
    private fun legByFunction() {
        val run= Runs(runs = 1,ballCount = balls,isWide = false, wicket = false, isNoBall =false, isLegBy = true, batsman = batsman, bowler = bowler, battingTeam = team1, fieldingTeam = team2, over = overs)
        runsList.add(run)
        balls += 1
        if(balls==6){
           // binding.sbBat1.text=binding.sbBat2.text
            batsman=binding.sbBat2.text.toString()
            binding.sbBat2.text=binding.sbBat1.text

         //   binding.sbBat2Score.text= runsList.filter { it-> it.batsman==binding.sbBat2.text.toString()}.sumOf { it.runs }.toString()
         //   binding.sbBat1Score.text= runsList.filter { it-> it.batsman==binding.sbBat1.text.toString()}.sumOf { it.runs }.toString()
        }else{
            binding.sbBat1.text=binding.sbBat2.text
            binding.sbBat2.text=batsman
           // binding.sbBat2Score.text= runsList.filter { it-> it.batsman==binding.sbBat2.text.toString()}.sumOf { it.runs }.toString()
           // binding.sbBat1Score.text= runsList.filter { it-> it.batsman==binding.sbBat1.text.toString()}.sumOf { it.runs }.toString()
        }
        if(balls==6) {
            overs+=1
            balls=0

        }
        binding.oversValue.text=overs.toString()+"."+balls.toString()
        adapter.setItem(runsList)
        headScore=  runsList.filter { it-> it.battingTeam==team1}.sumOf { it.runs }
        binding.teaHeaderScore.text=headScore.toString()+"/"+ wicket
        totalOvers = intent.getIntExtra("overs",0)
    }
    private fun wicketFunction(name:String) {
        val run= Runs(runs = 0,ballCount = balls,isWide =false, wicket = true, isNoBall =false, isLegBy = false, batsman = batsman, bowler = bowler, battingTeam = team1, fieldingTeam = team2, over = overs)
        runsList.add(run)
        balls += 1

        if(balls==6){
            binding.sbBat1.text=binding.sbBat2.text
            batsman=binding.sbBat2.text.toString()
            binding.sbBat2.text=name
            batsman=name
            binding.sbBat2Score.text= runsList.filter { it-> it.batsman==binding.sbBat2.text.toString()}.sumOf { it.runs }.toString()
            binding.sbBat1Score.text= runsList.filter { it-> it.batsman==binding.sbBat1.text.toString()}.sumOf { it.runs }.toString()
        }else{
            binding.sbBat1.text=name
            batsman=name
            binding.sbBat2Score.text= runsList.filter { it-> it.batsman==binding.sbBat2.text.toString()}.sumOf { it.runs }.toString()
            binding.sbBat1Score.text= runsList.filter { it-> it.batsman==binding.sbBat1.text.toString()}.sumOf { it.runs }.toString()
        }
        if(balls==6) {
            overs+=1
            balls=0
        }
        binding.oversValue.text=overs.toString()+"."+balls.toString()
        adapter.setItem(runsList)
        headScore=  runsList.filter { it-> it.battingTeam==team1}.sumOf { it.runs }
        wicket=runsList.filter { it.wicket }.size
        binding.teaHeaderScore.text=headScore.toString()+"/"+ wicket
        totalOvers = intent.getIntExtra("overs",0)

    }

    private fun undoFunction(){
        if(runsList[runsList.size-1].wicket){
            runsList.removeAt(runsList.size - 1)
            binding.sbBat1.text=runsList[runsList.size-1].batsman
            binding.sbBat1Score.text= runsList.filter { it-> it.batsman==binding.sbBat1.text.toString()}.sumOf { it.runs }.toString()
        }else {
            var manChange: Boolean = false
            if (runsList[runsList.size - 1].runs % 2 != 0) {
                manChange = true
            }
            runsList.removeAt(runsList.size - 1)
            if (balls == 0) {
                balls = 6
                overs -= 1
            }
            balls -= 1

            binding.oversValue.text = overs.toString() + "." + balls.toString()
            adapter.setItem(runsList)
            headScore = runsList.filter { it -> it.battingTeam == team1 }.sumOf { it.runs }
            if (manChange) {
                val bat2: String = binding.sbBat2.text.toString()
                binding.sbBat1.text = bat2
                binding.sbBat2.text = batsman
                binding.sbBat2Score.text =
                    runsList.filter { it -> it.batsman == batsman }.sumOf { it.runs }.toString()
                binding.sbBat1Score.text =
                    runsList.filter { it -> it.batsman == bat2 }.sumOf { it.runs }.toString()
                batsman = bat2
            } else {
                binding.sbBat2Score.text =
                    runsList.filter { it -> it.batsman == binding.sbBat2.text.toString() }
                        .sumOf { it.runs }.toString()
                binding.sbBat1Score.text =
                    runsList.filter { it -> it.batsman == binding.sbBat1.text.toString() }
                        .sumOf { it.runs }.toString()
            }
            binding.teaHeaderScore.text = headScore.toString() + "/" + wicket

        }
    }

    private fun nextInnings(){
        Log.e("nextScreen","nextscreen success "+overs+"  "+balls +" total overs: "+totalOvers)
        Log.e("nextScreen","nextscreen success "+(overs==totalOvers&&balls==5))
        if(overs==(totalOvers)&&balls==0&&isNextInnings){
            Log.e("****","nextscreen success "+(overs==totalOvers&&balls==5))
            winningStatus("First Innings Over...!!!!")
            Toast.makeText(applicationContext,"First innings completed",Toast.LENGTH_SHORT).show()
            firstInningsScore=runsList.sumOf { it.runs }
//            val itemList = arrayListOf(
//                Runs(),
//                Runs(),
//                Runs()
//            )
          CoroutineScope(Dispatchers.IO).launch {
              Log.e("Data stored","stored successfully111111")
              val hisData = dao2.insertRuns(*(runsList.toList()).toTypedArray())
              Log.e("Data stored", "stored successfully222222    $hisData")

           }.invokeOnCompletion {
              runsList.clear()
          }
            binding.sbBat1.text=""
            binding.sbBat2.text=""
            binding.sbBat2Score.text= ""
            binding.sbBat1Score.text= ""
            binding.overs.text=""
        overs=0
            isNextInnings=false
            balls==0
            var temp=team1
            team1=team2
            team2=temp
        }

        else if(overs==(totalOvers)&&balls==0 && !isNextInnings){
            if(firstInningsScore<= runsList.sumOf { it.runs }) {
                Toast.makeText(applicationContext,"Match Over...$team2 won",Toast.LENGTH_SHORT).show()
                winningStatus("$team2 won the match")
                CoroutineScope(Dispatchers.IO).launch {
                    Log.e("Data stored","stored successfully111111")
                    val hisData = dao2.insertRuns(*(runsList.toList()).toTypedArray())
                    Log.e("Data stored", "stored successfully222222    $hisData")

                }.invokeOnCompletion {
                    runsList.clear()
                }
            }
            CoroutineScope(Dispatchers.IO).launch {
                Log.e("Data stored","stored successfully111111")
                val hisData = dao2.insertRuns(*(runsList.toList()).toTypedArray())
                Log.e("Data stored", "stored successfully222222    $hisData")

            }.invokeOnCompletion {
                runsList.clear()
            }
            Toast.makeText(applicationContext,"Match Over",Toast.LENGTH_SHORT).show()
            winningStatus("$team1 won the match")
        }
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
               // nextInnings()

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
    fun checkAllFields(): Boolean {
        if (binding.sbBat1!!.length()===0) {
            binding.sbBat1!!.setError("Please enter the Team1")
            return false
        }
        else if (binding.sbBat2!!.length() === 0) {
            binding.sbBat1!!.setError("please enter the team2")
            return false
        }
        else if (binding.sbBowl1!!.length() === 0) {
            binding.sbBowl1!!.setError("Email is required")
            return false
        }else {
            // after all validation return true.
            binding.sbBat1!!.error=null
            binding.sbBat2!!.error=null
            binding.sbBowl1!!.error=null
            return true

        }
    }
    fun winningStatus(res:String){
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_batsman, null)
        val alertDialog = AlertDialog.Builder(this)
            .setTitle("Result")
            .setMessage(res)
            .setPositiveButton("ok") { dialog, _ ->

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



