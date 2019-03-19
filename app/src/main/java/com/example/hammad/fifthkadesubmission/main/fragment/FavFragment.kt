package com.example.hammad.fifthkadesubmission.main.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hammad.fifthkadesubmission.R
import com.example.hammad.fifthkadesubmission.favorites.fragment.FavMatchesFragment
import com.example.hammad.fifthkadesubmission.favorites.fragment.FavTeamsFragment
import com.example.hammad.fifthkadesubmission.matches.adapter.MatchesTabAdapter
import kotlinx.android.synthetic.main.fragment_favorites.view.*

class FavFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_favorites, container, false)
        setupViewPager(view.vp_favorites)
        view.tab_favorites.setupWithViewPager(view.vp_favorites)
        setHasOptionsMenu(false)
        return view
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = MatchesTabAdapter(childFragmentManager)
        adapter.addFragment(FavMatchesFragment(), resources.getString(R.string.matches))
        adapter.addFragment(FavTeamsFragment(), resources.getString(R.string.teams))
        viewPager.adapter = adapter
    }

}
