package com.example.hammad.fifthkadesubmission.matches.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import com.example.hammad.fifthkadesubmission.matches.model.Match
import com.example.hammad.fifthkadesubmission.util.ConvertDate
import kotlinx.android.synthetic.main.list_prev.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class PreviousViewHolder (itemView: View): RecyclerView.ViewHolder(itemView){
    val tvPrevDate = itemView.tv_prev_date
    val tvPrevTime = itemView.tv_prev_time
    val tvPrevHome = itemView.tv_prev_home
    val tvPrevAway = itemView.tv_prev_away
    val tvPrevScrHome = itemView.tv_prev_score_home
    val tvPrevScrAway = itemView.tv_prev_score_away
    val ivPrevBell = itemView.iv_prev_bell


    fun bindItem(context: Context?, match: Match?, listener: (Match?) -> Unit) {
        tvPrevDate.text = ConvertDate().convertDate(match?.dateEvent!!, match.strTime!!)
        tvPrevTime.text = ConvertDate().convertTime(match.dateEvent!!, match.strTime!!)
        tvPrevHome.text = match.strHomeTeam
        tvPrevAway.text = match.strAwayTeam
        tvPrevScrHome.text = match.intHomeScore.toString()
        tvPrevScrAway.text = match.intAwayScore.toString()
        ivPrevBell.setOnClickListener {
            Toast.makeText(context, "bell pressed", Toast.LENGTH_SHORT).show()
        }
        itemView.onClick { listener(match) }
    }
}