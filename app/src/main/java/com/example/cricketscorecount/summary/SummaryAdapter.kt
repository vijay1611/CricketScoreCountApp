package com.example.cricketscorecount.summary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cricketscorecount.R
import com.example.cricketscorecount.ScoreBoardactivity.PlayersDetails

class SummaryAdapter(private var mSummary : ArrayList<PlayersDetails>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.sub_summary, parent, false)

        return SummaryViewHolder(view)
    }



    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val ItemsViewModel = mSummary[position]

            holder.itemView.findViewById<TextView>(R.id.batmanName).text = ItemsViewModel.batsmanName
        holder.itemView.findViewById<TextView>(R.id.score).text = ItemsViewModel.runs.toString()


    }
    override fun getItemCount(): Int {
        return mSummary.size
    }
    fun setvalue(hisData: ArrayList<PlayersDetails>) {
         mSummary = hisData
        notifyDataSetChanged()
    }
}



class  SummaryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    var batsmanName = itemView.findViewById<TextView>(R.id.batmanName)
    var runs = itemView.findViewById<TextView>(R.id.run)

}