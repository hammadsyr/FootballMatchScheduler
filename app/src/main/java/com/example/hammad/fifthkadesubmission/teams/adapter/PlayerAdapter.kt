package com.example.hammad.fifthkadesubmission.teams.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.hammad.fifthkadesubmission.R
import com.example.hammad.fifthkadesubmission.teams.model.TeamsPlayer

class PlayerAdapter(var context: Context?, internal var items: List<TeamsPlayer?>, private val listener: (TeamsPlayer?) -> Unit) :
        RecyclerView.Adapter<PlayerViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): PlayerViewHolder {
        val itemView = LayoutInflater.from(p0.context)
                .inflate(R.layout.list_player, p0, false)
        return PlayerViewHolder(itemView)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        holder.bindItem(context, items[position], listener)
    }
}



