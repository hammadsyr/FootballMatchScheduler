package com.example.hammad.fifthkadesubmission.teams.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import com.bumptech.glide.Glide
import com.example.hammad.fifthkadesubmission.teams.model.TeamsPlayer
import kotlinx.android.synthetic.main.list_player.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class PlayerViewHolder (itemView: View): RecyclerView.ViewHolder(itemView){
    val tvPlayerName = itemView.tv_player_name
    val tvPlayerPosition = itemView.tv_player_position
    val ivPlayer = itemView.iv_player

    fun bindItem(context: Context?, player : TeamsPlayer?, listener: (TeamsPlayer?) -> Unit) {
        tvPlayerName.text = player?.strPlayer
        tvPlayerPosition.text = player?.strPosition
        Glide.with(context!!).load(player?.strCutout).into(ivPlayer)
        itemView.onClick { listener(player) }
    }
}