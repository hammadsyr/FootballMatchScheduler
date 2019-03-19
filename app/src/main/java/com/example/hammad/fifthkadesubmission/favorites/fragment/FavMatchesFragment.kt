package com.example.hammad.fifthkadesubmission.favorites.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hammad.fifthkadesubmission.R
import com.example.hammad.fifthkadesubmission.favorites.adapter.FavouriteMatchesAdapter
import com.example.hammad.fifthkadesubmission.favorites.model.FavMatches
import com.example.hammad.fifthkadesubmission.matches.MatchDetail
import com.example.hammad.fifthkadesubmission.util.db.DBMatches
import kotlinx.android.synthetic.main.fragment_fav_matches.view.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.support.v4.startActivity

class FavMatchesFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private var itemList = mutableListOf<FavMatches>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_fav_matches, container, false)
        recyclerView = view.fav_matches_rv
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
        DBMatches.getInstance(activity!!.applicationContext).use {
            val queryResult = select(FavMatches.TABLE_NAME)
            val favoriteEvent = queryResult.parseList(classParser<FavMatches>())
            itemList.clear()
            itemList.addAll(favoriteEvent)
        }
    }

    private fun setAdapter() : FavouriteMatchesAdapter{
        val favAdapter = FavouriteMatchesAdapter(activity?.applicationContext, itemList) {
            startActivity<MatchDetail>("id" to it?.idEvent)
        }
        return favAdapter
    }
}
