package com.example.hammad.fifthkadesubmission.matches.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import com.example.hammad.fifthkadesubmission.matches.model.Match
import com.example.hammad.fifthkadesubmission.util.CalendarUtil
import com.example.hammad.fifthkadesubmission.util.ConvertDate
import kotlinx.android.synthetic.main.list_next.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class NextViewHolder (itemView: View): RecyclerView.ViewHolder(itemView){
    val tvNextDate = itemView.tv_next_date
    val tvNextTime = itemView.tv_next_time
    val tvNextHome = itemView.tv_next_home
    val tvNextAway = itemView.tv_next_away
    val ivNextBell = itemView.iv_next_bell
    val calendar = CalendarUtil()

    fun bindItem(context: Context?, match: Match?, listener: (Match?) -> Unit) {
        tvNextDate.text = ConvertDate().convertDate(match?.dateEvent!!, match.strTime!!)
        tvNextTime.text = ConvertDate().convertTime(match.dateEvent!!, match.strTime!!)
        tvNextHome.text = match.strHomeTeam
        tvNextAway.text = match.strAwayTeam
        ivNextBell.setOnClickListener {
            Toast.makeText(context, "bell pressed", Toast.LENGTH_SHORT).show()
            calendar.calendar(context!!,"${match.strHomeTeam} vs ${match.strAwayTeam}",
                match.dateEvent!! ,match.strTime!!)
        }
        itemView.onClick { listener(match) }
    }
}