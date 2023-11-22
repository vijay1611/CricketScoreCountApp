package com.example.cricketscorecount

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.cricketscorecount.database.CricketDatabase
import com.example.cricketscorecount.models.Runs
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SplashScreen : AppCompatActivity() {
    //var handler : Handler = Handler()
    private lateinit var database: CricketDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        database = CricketDatabase.getDatabase(context = this)




        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
        },3000)


    }

}