package com.example.hammad.fifthkadesubmission.teams.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import com.bumptech.glide.Glide
import com.example.hammad.fifthkadesubmission.teams.model.Teams
import kotlinx.android.synthetic.main.list_teams.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class TeamsViewHolder (itemView: View): RecyclerView.ViewHolder(itemView){
    val ivTeams = itemView.iv_teams
    val tvTeams = itemView.tv_teams

    fun bindItem(context: Context?, teams : Teams, listener: (Teams) -> Unit) {
        Glide.with(context!!)
                .load(teams.strTeamBadge)
                .into(ivTeams)
        tvTeams.text = teams.strTeam
        itemView.onClick { listener(teams) }
    }
}