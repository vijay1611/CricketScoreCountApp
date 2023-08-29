package com.example.cricketscorecount

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cricketscorecount.ScoreBoardactivity.ScoreBoardActivity
import com.example.cricketscorecount.database.CricketDao
import com.example.cricketscorecount.database.CricketDatabase
import com.example.cricketscorecount.databinding.ActivityTeamDetailsBinding
import com.example.cricketscorecount.models.Team
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TeamDetailsActivity : AppCompatActivity() {

    lateinit var btnSubmit: Button

    lateinit var binding : ActivityTeamDetailsBinding
    private lateinit var database : CricketDatabase
    private lateinit var dao : CricketDao


    var isAllFieldsChecked = false
    var firstTeamName: EditText? = null
    var lastTeamName: EditText? = null
    var over: EditText? = null
    var radioGroup:RadioGroup?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_team_details)
       // getSupportActionBar()?.hide();
        binding = ActivityTeamDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = CricketDatabase.getDatabase(context = this)
        dao = database.getCricketDao()

        firstTeamName = binding.teamname1
        lastTeamName = binding.teamname2
        over = binding.overs
        radioGroup = binding.radioGroup1
        btnSubmit = binding.teamDetailsSubmitBtn
        btnSubmit.setOnClickListener {
            val team1 = binding.teamname1.text.toString()
            val team2 = binding.teamname2.text.toString()
            val overs:String = binding.overs.text.toString()
            val battingBy = binding.radioGroup1

            isAllFieldsChecked = CheckAllFields()
           // if (isAllFieldsChecked) {
                val battingTeam=findViewById<RadioButton>(binding.radioGroup1.checkedRadioButtonId).text.toString()
                CoroutineScope(Dispatchers.IO).launch {
                    dao.insert(Team(team1 = team1, team2 = team2, overs = overs.toInt(), battingBy = battingTeam))

                    Log.e("*****","Inserted")
                }
                    binding.teamname1.text.clear()
                    binding.teamname2.text.clear()
                    binding.overs.text.clear()
                    binding.radioGroup1.clearCheck()
                    val intent = Intent(this, ScoreBoardActivity::class.java)
                    intent.putExtra("team1",team1)
                    intent.putExtra("team2",team2)
                    startActivity(intent)
                finish()










            }

        }

    //}



    fun CheckAllFields(): Boolean {
        if (firstTeamName!!.length() == 0) {
            firstTeamName!!.setError("Please enter the Team1")
            return false
        }
        if (lastTeamName!!.length() === 0) {
            lastTeamName!!.setError("please enter the team2")
            return false
        }
        if (over!!.length() === 0) {
            over!!.setError("Email is required")
            return false
        }
        if (radioGroup!!.getCheckedRadioButtonId() == -1){
            Toast.makeText(this,"Please select batting team" , Toast.LENGTH_SHORT).show()
            return false
        }

        // after all validation return true.
        return true
    }
}