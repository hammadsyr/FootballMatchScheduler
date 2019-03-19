package com.example.hammad.fifthkadesubmission.favorites.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hammad.fifthkadesubmission.R
import com.example.hammad.fifthkadesubmission.favorites.adapter.FavouriteTeamsAdapter
import com.example.hammad.fifthkadesubmission.favorites.model.FavTeams
import com.example.hammad.fifthkadesubmission.teams.TeamsDetail
import com.example.hammad.fifthkadesubmission.util.db.DBTeams
import kotlinx.android.synthetic.main.fragment_fav_teams.view.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.support.v4.startActivity

class FavTeamsFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private var itemList = mutableListOf<FavTeams>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_fav_teams, container, false)
        recyclerView = view.fav_teams_rv
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
        getFavoriteEvent()
        recyclerView.adapter = setAdapter()
        return view
    }

    override fun onResume() {
        super.onResume()
        getFavoriteEvent()
        recyclerView.adapter = setAdapter()
    }

    private fun getFavoriteEvent() {
        DBTeams.getInstance(activity!!.applicationContext).use {
            val queryResult = select(FavTeams.TABLE_NAME)
            val favoriteEvent = queryResult.parseList(classParser<FavTeams>())
            itemList.clear()
            itemList.addAll(favoriteEvent)
        }
    }

    private fun setAdapter(): FavouriteTeamsAdapter {
        val favAdapter = FavouriteTeamsAdapter(activity?.applicationContext, itemList) {
            startActivity<TeamsDetail>("id" to it?.idTeam)
        }
        return favAdapter
    }
}
