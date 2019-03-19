package com.example.hammad.fifthkadesubmission.teams.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hammad.fifthkadesubmission.R
import com.example.hammad.fifthkadesubmission.presenter.Presenter
import com.example.hammad.fifthkadesubmission.presenter.Repository
import com.example.hammad.fifthkadesubmission.teams.PlayerDetail
import com.example.hammad.fifthkadesubmission.teams.TeamsDetail
import com.example.hammad.fifthkadesubmission.teams.adapter.PlayerAdapter
import com.example.hammad.fifthkadesubmission.teams.view.PlayersView
import com.example.hammad.fifthkadesubmission.util.APIResponse
import kotlinx.android.synthetic.main.fragment_player.view.*
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast

class PlayerFragment : Fragment(), PlayersView{
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var views : View
    private lateinit var presenter : Presenter<PlayersView>
    private lateinit var teamsId : String
    private lateinit var recyclerViewAdapter: PlayerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = Presenter(this, Repository())
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        views = inflater.inflate(R.layout.fragment_player, container, false)
        swipeRefreshLayout = views.player_swipe_refresh
        recyclerView = views.player_rv

        val detail = activity as TeamsDetail
        teamsId = detail.teamId()
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)

        swipeRefreshLayout.setColorSchemeResources(
            android.R.color.holo_red_light,
            android.R.color.holo_blue_light,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light
        )

        swipeRefreshLayout.onRefresh {
            presenter.getListPlayers(teamsId)
        }

        presenter.getListPlayers(teamsId)

        return views
    }

    override fun onShowLoading() {
        swipeRefreshLayout.isRefreshing = true
    }

    override fun onListPlayers(data: APIResponse.ListPlayer) {
        swipeRefreshLayout.isRefreshing = false
        recyclerViewAdapter = PlayerAdapter(context,data.player){
            startActivity<PlayerDetail>("id" to it?.idPlayer, "name" to it?.strPlayer)
        }
        recyclerView.adapter = recyclerViewAdapter
    }

    override fun onDataError(message: String) {
        swipeRefreshLayout.isRefreshing = false
        toast(message)
    }
}
