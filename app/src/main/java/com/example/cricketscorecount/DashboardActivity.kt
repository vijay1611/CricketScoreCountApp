package com.example.cricketscorecount

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.cricketscorecount.history.HistoryActivity

class DashboardActivity : AppCompatActivity() {
    private var exitDialogFragment: ExitDialogFragment? = null
    lateinit var btnNewGame: Button
    lateinit var btnHistory: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        btnNewGame = findViewById(R.id.newGame)
        btnHistory = findViewById(R.id.history)
        btnNewGame.setOnClickListener{
            startActivity(Intent(this,TeamDetailsActivity::class.java))
        }
        btnHistory.setOnClickListener{
            startActivity(Intent(this,HistoryActivity::class.java))
        }
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