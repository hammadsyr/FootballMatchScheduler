package com.example.hammad.fifthkadesubmission.favorites.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.hammad.fifthkadesubmission.R
import com.example.hammad.fifthkadesubmission.favorites.model.FavMatches

class FavouriteMatchesAdapter(var context: Context?, private val favorite: List<FavMatches>, private val listener: (FavMatches?) -> Unit)
    : RecyclerView.Adapter<FavoriteMatchesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMatchesViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_prev, parent, false)
        return FavoriteMatchesViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteMatchesViewHolder, position: Int) {
        holder.bindItem(context, favorite[position], listener)
    }

    override fun getItemCount(): Int = favorite.size

}

