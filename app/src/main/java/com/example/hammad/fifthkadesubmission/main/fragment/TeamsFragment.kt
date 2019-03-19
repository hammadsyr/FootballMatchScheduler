package com.example.hammad.fifthkadesubmission.main.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.AppCompatSpinner
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.hammad.fifthkadesubmission.R
import com.example.hammad.fifthkadesubmission.presenter.Presenter
import com.example.hammad.fifthkadesubmission.presenter.Repository
import com.example.hammad.fifthkadesubmission.teams.TeamsDetail
import com.example.hammad.fifthkadesubmission.teams.adapter.TeamsAdapter
import com.example.hammad.fifthkadesubmission.teams.view.TeamsView
import com.example.hammad.fifthkadesubmission.util.APIResponse
import kotlinx.android.synthetic.main.fragment_teams.view.*
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast

class TeamsFragment : Fragment(), AdapterView.OnItemSelectedListener, TeamsView {
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var spinner: AppCompatSpinner
    private lateinit var spinnerAdapter: ArrayAdapter<String>
    private lateinit var views: View
    private lateinit var teamsPresenter: Presenter<TeamsView>
    private lateinit var leagueId: String
    private lateinit var recyclerViewAdapter: TeamsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        teamsPresenter = Presenter(this, Repository())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        views = inflater.inflate(R.layout.fragment_teams, container, false)
        spinner = views.teams_spinner
        swipeRefreshLayout = views.teams_swipe_refresh
        recyclerView = views.teams_rv

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
        spinnerAdapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_dropdown_item, resources.getStringArray(R.array.league))
        spinner.adapter = spinnerAdapter
        spinner.onItemSelectedListener = this

        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_red_light,
                android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light
        )

        swipeRefreshLayout.onRefresh {
            leagueId = resources.getStringArray(R.array.league_id).get(resources.getStringArray(R.array.league).indexOf(spinner.selectedItem.toString()))
            teamsPresenter.getListTeams(leagueId)
        }

        teamsPresenter = Presenter(this, Repository())
        teamsPresenter.getListTeams(resources.getStringArray(R.array.league_id)[0].toString())
        setHasOptionsMenu(true)
        return views
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.search, menu)
        val searchActionMenuItem = menu?.findItem(R.id.menu_search)
        val searchView = searchActionMenuItem?.actionView as android.support.v7.widget.SearchView
        searchView.queryHint = "Search Team"
        searchView.isSubmitButtonEnabled = true
        searchView.setOnQueryTextListener(object : android.support.v7.widget.SearchView.OnQueryTextListener {
            var leagueId = resources.getStringArray(R.array.league_id)
                    .get(resources.getStringArray(R.array.league).indexOf(spinner.selectedItem.toString()))
            override fun onQueryTextSubmit(p0: String): Boolean {
                if (p0 != "") {
                    recyclerViewAdapter = TeamsAdapter(context, recyclerViewAdapter.filter(p0)) {
                        startActivity<TeamsDetail>("id" to it.idTeam)
                    }
                    recyclerView.adapter = recyclerViewAdapter
                } else
                    teamsPresenter.getListTeams(leagueId)

                return true
            }

            override fun onQueryTextChange(p0: String): Boolean {
                if (p0 != "") {
                    recyclerViewAdapter = TeamsAdapter(context, recyclerViewAdapter.filter(p0)) {
                        startActivity<TeamsDetail>("id" to it.idTeam)
                    }
                    recyclerView.adapter = recyclerViewAdapter
                } else
                    teamsPresenter.getListTeams(leagueId)
                return true
            }
        })
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        teamsPresenter.getListTeams(resources.getStringArray(R.array.league_id)[position].toString())
    }

    override fun onShowLoading() {
        swipeRefreshLayout.isRefreshing = true
    }

    override fun onListTeams(data: APIResponse.ListTeams) {
        swipeRefreshLayout.isRefreshing = false
        recyclerViewAdapter = TeamsAdapter(context, data.teams) {
            startActivity<TeamsDetail>("id" to it.idTeam)
        }
        recyclerView.adapter = recyclerViewAdapter
    }

    override fun onDataError(message: String) {
        swipeRefreshLayout.isRefreshing = false
        toast(message)
    }
}
