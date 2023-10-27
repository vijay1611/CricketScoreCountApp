package com.example.cricketscorecount.ScoreBoardactivity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cricketscorecount.R
import com.example.cricketscorecount.models.Runs

class ScoreAdapter(private var mScore : ArrayList<Runs>):RecyclerView.Adapter<ScoreAdapter.ScoreViewModel>() {

    fun setItem( Score : ArrayList<Runs>){
        mScore.clear()
        mScore.addAll(Score.filter { it.over==Score[Score.size-1].over})
        if (mScore.isNotEmpty()&&mScore[mScore.size-1].ballCount==6){
            mScore.clear()
        }
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreViewModel {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_runs, parent, false)

        return ScoreViewModel(view)
    }

    override fun onBindViewHolder(holder: ScoreViewModel, position: Int) {
        val ItemsViewModel = mScore[position]
        if(mScore[position].isWide){
            holder.runs.text="wd"
        }else if(mScore[position].wicket){
            holder.runs.text="W"
        }
        else if(mScore[position].isNoBall){
            holder.runs.text="NB"
        }
        else if(mScore[position].isLegBy){
            holder.runs.text="LB"
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

    class  ScoreViewModel(itemView:View) : RecyclerView.ViewHolder(itemView){

        var runs = itemView.findViewById<TextView>(R.id.run)
    }

}