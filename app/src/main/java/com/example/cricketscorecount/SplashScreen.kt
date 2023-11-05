package com.example.cricketscorecount

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.cricketscorecount.database.CricketDatabase
import com.example.cricketscorecount.database.Item
import com.example.cricketscorecount.database.ItemDao
import com.example.cricketscorecount.models.Runs
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SplashScreen : AppCompatActivity() {
    //var handler : Handler = Handler()
    private lateinit var database: CricketDatabase
    private lateinit var dao: ItemDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        database = CricketDatabase.getDatabase(context = this)
        dao = database.itemDao()

        val itemList = arrayListOf(
            Item(name = "Item 1"),
            Item(name = "Item 2"),
            Item(name = "Item 3")
        )

        CoroutineScope(Dispatchers.IO).launch {

            val hisData = dao.insertItems(*itemList.toList().toTypedArray())



        }

        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
        },3000)


    }

}