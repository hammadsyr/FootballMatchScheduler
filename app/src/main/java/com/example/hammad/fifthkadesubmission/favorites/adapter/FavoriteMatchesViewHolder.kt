package com.example.hammad.fifthkadesubmission.favorites.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import com.example.hammad.fifthkadesubmission.favorites.model.FavMatches
import kotlinx.android.synthetic.main.list_prev.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class FavoriteMatchesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val tvFavDate = itemView.tv_prev_date
    val tvFavTime = itemView.tv_prev_time
    val tvFavHome = itemView.tv_prev_home
    val tvFavAway = itemView.tv_prev_away
    val tvFavScrHome = itemView.tv_prev_score_home
    val tvFavScrAway = itemView.tv_prev_score_away
    val ivFavBell = itemView.iv_prev_bell

    fun bindItem(context: Context?, match: FavMatches?, listener: (FavMatches?) -> Unit) {
        tvFavDate.text = match?.dateEvent
        tvFavTime.text = match?.strTime
        tvFavHome.text = match?.strHomeTeam
        tvFavAway.text = match?.strAwayTeam
        tvFavScrHome.text = match?.intHomeScore.toString()
        tvFavScrAway.text = match?.intAwayScore.toString()
        ivFavBell.setOnClickListener {
            Toast.makeText(context, "bell pressed", Toast.LENGTH_SHORT).show()
        }
        itemView.onClick { listener(match) }
    }
}