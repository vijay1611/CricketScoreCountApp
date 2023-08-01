package com.example.cricketscorecount

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.cricketscorecount.ScoreBoardactivity.ScoreBoardActivity

class SplashScreen : AppCompatActivity() {
    //var handler : Handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
        },3000)


    }

}