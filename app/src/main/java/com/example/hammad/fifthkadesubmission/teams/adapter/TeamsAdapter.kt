package com.example.hammad.fifthkadesubmission.teams.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.hammad.fifthkadesubmission.R
import com.example.hammad.fifthkadesubmission.teams.model.Teams

class TeamsAdapter(var context: Context?, internal var items: List<Teams>, private val listener: (Teams) -> Unit) :
        RecyclerView.Adapter<TeamsViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): TeamsViewHolder {
        val itemView = LayoutInflater.from(p0.context)
                .inflate(R.layout.list_teams, p0, false)
        return TeamsViewHolder(itemView)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: TeamsViewHolder, position: Int) {
        holder.bindItem(context, items[position], listener)
    }

    fun filter(text: String): List<Teams> {
        var text = text
        val item = mutableListOf<Teams>()
        item.clear()
        text = text.toLowerCase()
        for (i in 0..items.size - 1) {
            if (items[i].strTeam.toLowerCase().contains(text)) {
                item.add(items[i])
            }
        }
        return item
    }
}



