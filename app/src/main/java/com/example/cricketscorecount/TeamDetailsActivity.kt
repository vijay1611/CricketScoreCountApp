package com.example.cricketscorecount

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cricketscorecount.ScoreBoardactivity.ScoreBoardActivity
import com.example.cricketscorecount.databinding.ActivityTeamDetailsBinding

class TeamDetailsActivity : AppCompatActivity() {

    lateinit var btnSubmit: Button

    lateinit var binding : ActivityTeamDetailsBinding


    var isAllFieldsChecked = false
    var firstTeamName: EditText? = null
    var lastTeamName: EditText? = null
    var over: EditText? = null
    var radioGroup:RadioGroup?=null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_team_details)
        binding = ActivityTeamDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firstTeamName = findViewById(R.id.teamname1)
        lastTeamName = findViewById(R.id.teamname2)
        over = findViewById(R.id.overs)
        radioGroup=findViewById(R.id.radioGroup1)
        btnSubmit = findViewById(R.id.teamDetails_submitBtn)
        btnSubmit.setOnClickListener{
            val team1 = binding.teamname1.text
            val team2 = binding.teamname2.text
            val overs = binding.overs.text
            val battingBy = binding.

            startActivity(Intent(this,ScoreBoardActivity::class.java))
        btnSubmit.setOnClickListener {
            isAllFieldsChecked = CheckAllFields()
            if (isAllFieldsChecked) {
                startActivity(Intent(this, ScoreBoardActivity::class.java))
            }
        }

    }

    private fun CheckAllFields(): Boolean {
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