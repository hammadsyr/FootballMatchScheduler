package com.example.hammad.fifthkadesubmission.main.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hammad.fifthkadesubmission.R
import com.example.hammad.fifthkadesubmission.matches.adapter.MatchesTabAdapter
import com.example.hammad.fifthkadesubmission.matches.fragment.NextFragment
import com.example.hammad.fifthkadesubmission.matches.fragment.PrevFragment
import kotlinx.android.synthetic.main.fragment_matches.view.*


class MatchesFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_matches, container, false)
        setupViewPager(view.vp_matches)
        view.tab_matches.setupWithViewPager(view.vp_matches)
        return view
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = MatchesTabAdapter(childFragmentManager)
        adapter.addFragment(PrevFragment(), resources.getString(R.string.last))
        adapter.addFragment(NextFragment(), resources.getString(R.string.next))
        viewPager.adapter = adapter
    }
}
