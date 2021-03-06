package com.example.hammad.fifthkadesubmission.matches.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.hammad.fifthkadesubmission.R
import com.example.hammad.fifthkadesubmission.matches.model.Match


class PreviousAdapter(var context: Context?, internal var items: List<Match?>, private val listener: (Match?) -> Unit) :
        RecyclerView.Adapter<PreviousViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): PreviousViewHolder {
        val itemView = LayoutInflater.from(p0.context)
                .inflate(R.layout.list_prev, p0, false)
        return PreviousViewHolder(itemView)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: PreviousViewHolder, position: Int) {
        holder.bindItem(context, items[position], listener)
    }

    fun filter(text: String): List<Match?> {
        var text = text
        val item = mutableListOf<Match?>()
        item.clear()
        text = text.toLowerCase()
        for (i in 0..items.size - 1) {
            if (items[i]?.strHomeTeam?.toLowerCase()?.contains(text)!! || items[i]?.strAwayTeam?.toLowerCase()?.contains(text)!!) {
                item.add(items[i])
            }
        }

        return item
    }
}



