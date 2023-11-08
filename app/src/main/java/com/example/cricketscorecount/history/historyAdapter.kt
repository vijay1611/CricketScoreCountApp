package com.example.cricketscorecount.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cricketscorecount.R
import com.example.cricketscorecount.models.Team

class historyAdapter( private var mList: List<Team>, val clickListener: historyClickListener): RecyclerView.Adapter<historyAdapter.historyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): historyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.itemhistory, parent, false)

        return historyViewHolder(view)

    }


    override fun onBindViewHolder(holder: historyViewHolder, position: Int) {
        val ItemsViewModel = mList[position]
        holder.team1.text=ItemsViewModel.team1
        holder.team2.text=ItemsViewModel.team2
        holder.overs.text=ItemsViewModel.overs.toString()
        holder.itemView.setOnClickListener{
            clickListener.showSummary(ItemsViewModel)
        }

    }

    override fun getItemCount(): Int {
       // return minOf( mList.size,5)
        return mList.size
    }

    fun setvalue(hisData: List<Team>) {
        mList=hisData
        notifyDataSetChanged()
    }


    class historyViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView){
        val team1: TextView = ItemView.findViewById(R.id.team1)
        val team2: TextView = ItemView.findViewById(R.id.team2)
        val overs: TextView = ItemView.findViewById(R.id.overs1)
    }


}
