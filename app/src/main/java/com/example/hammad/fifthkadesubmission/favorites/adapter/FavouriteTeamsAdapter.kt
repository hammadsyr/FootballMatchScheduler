package com.example.hammad.fifthkadesubmission.favorites.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.hammad.fifthkadesubmission.R
import com.example.hammad.fifthkadesubmission.favorites.model.FavTeams

class FavouriteTeamsAdapter(var context: Context?, private val favorite: List<FavTeams>, private val listener: (FavTeams?) -> Unit)
    : RecyclerView.Adapter<FavouriteTeamsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteTeamsViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_teams, parent, false)
        return FavouriteTeamsViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavouriteTeamsViewHolder, position: Int) {
        holder.bindItem(context, favorite[position], listener)
    }

    override fun getItemCount(): Int = favorite.size

}