package com.example.hammad.fifthkadesubmission.favorites.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import com.bumptech.glide.Glide
import com.example.hammad.fifthkadesubmission.favorites.model.FavTeams
import kotlinx.android.synthetic.main.list_teams.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class FavouriteTeamsViewHolder(view: View) : RecyclerView.ViewHolder(view){

    val ivFavTeam = itemView.iv_teams
    val tvFavTeam = itemView.tv_teams

    fun bindItem(context: Context?, teams: FavTeams?, listener: (FavTeams?) -> Unit) {
        Glide.with(context!!).load(teams?.strTeamBadge).into(ivFavTeam)
        tvFavTeam.text = teams?.strTeam
        itemView.onClick { listener(teams) }
    }
}