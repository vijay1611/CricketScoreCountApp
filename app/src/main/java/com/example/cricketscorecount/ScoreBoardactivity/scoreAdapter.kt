package com.example.cricketscorecount.ScoreBoardactivity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import com.example.cricketscorecount.R
import com.example.cricketscorecount.history.historyAdapter
import com.example.cricketscorecount.models.Runs
import com.example.cricketscorecount.models.Team

class scoreAdapter(private var mScore : ArrayList<Runs>):RecyclerView.Adapter<scoreAdapter.scoreViewModel>() {

    fun setItem( Score : ArrayList<Runs>){
        mScore.clear()
        mScore.addAll(Score.filter { it.over==Score[Score.size-1].over})
        if (mScore[mScore.size-1].ballCount==6){
            mScore.clear()
        }
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): scoreViewModel {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_runs, parent, false)

        return scoreViewModel(view)
    }

    override fun onBindViewHolder(holder: scoreViewModel, position: Int) {
        val ItemsViewModel = mScore[position]
        if(mScore[position].isWide){
            holder.runs.text="wd"
        }else {
            holder.runs.text = ItemsViewModel.runs.toString()
        }
    }

    override fun getItemCount(): Int {
        return mScore.size
    }

    /*fun addRun(num: Runs) {
        if(num.ballCount==1 ) mScore.clear()
        mScore.add(num)
        notifyDataSetChanged()
    }*/

    class  scoreViewModel(itemView:View) : RecyclerView.ViewHolder(itemView){

        var runs = itemView.findViewById<TextView>(R.id.run)
    }

}