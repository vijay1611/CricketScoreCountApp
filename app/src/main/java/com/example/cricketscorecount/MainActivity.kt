package com.example.cricketscorecount

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    private var exitDialogFragment: ExitDialogFragment? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_details)
    }

    override fun onBackPressed() {
        if (exitDialogFragment == null) {
            exitDialogFragment = ExitDialogFragment()
            exitDialogFragment?.show(supportFragmentManager, "exit_dialog")
        } else {
            super.onBackPressed()
        }
    }


}