package com.example.hammad.fifthkadesubmission.teams.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.hammad.fifthkadesubmission.R
import com.example.hammad.fifthkadesubmission.presenter.Presenter
import com.example.hammad.fifthkadesubmission.presenter.Repository
import com.example.hammad.fifthkadesubmission.teams.TeamsDetail
import com.example.hammad.fifthkadesubmission.teams.view.DetailTeamsView
import com.example.hammad.fifthkadesubmission.util.APIResponse
import kotlinx.android.synthetic.main.fragment_overview.*
import kotlinx.android.synthetic.main.fragment_overview.view.*
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.toast

class OverviewFragment : Fragment(), DetailTeamsView {
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var views: View
    private lateinit var presenter: Presenter<DetailTeamsView>
    private lateinit var teamsId : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = Presenter(this, Repository())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        views = inflater.inflate(R.layout.fragment_overview, container, false)
        swipeRefreshLayout = views.overview_swipe_refresh
        val detail = activity as TeamsDetail
        teamsId = detail.teamId()
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_red_light,
                android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light
        )

        swipeRefreshLayout.onRefresh {
            presenter.getDetailTeams(teamsId)
        }

        presenter.getDetailTeams(teamsId)
        return views
    }

    override fun onShowLoading() {
        swipeRefreshLayout.isRefreshing = true
    }

    override fun onDetailTeams(data: APIResponse.ListTeams) {
        swipeRefreshLayout.isRefreshing = false
        wv_teams_description.getSettings().setJavaScriptEnabled(true)
        wv_teams_description.setWebViewClient(object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }

            override fun onReceivedError(view: WebView, errorCode: Int, description: String, failingUrl: String) {
                toast(description)
            }

        })
        wv_teams_description.loadData(data.teams[0].strDescriptionEN, "text/html", "UTF-8")
    }

    override fun onDataError(message: String) {
        swipeRefreshLayout.isRefreshing = false
        toast(message)
    }
}
