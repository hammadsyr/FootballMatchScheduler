package com.example.hammad.fifthkadesubmission.matches.fragment

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
import com.example.hammad.fifthkadesubmission.matches.MatchDetail
import com.example.hammad.fifthkadesubmission.matches.adapter.NextAdapter
import com.example.hammad.fifthkadesubmission.matches.view.MatchView
import com.example.hammad.fifthkadesubmission.presenter.Presenter
import com.example.hammad.fifthkadesubmission.presenter.Repository
import com.example.hammad.fifthkadesubmission.util.APIResponse
import kotlinx.android.synthetic.main.fragment_next.view.*
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast

class NextFragment : Fragment(), AdapterView.OnItemSelectedListener, MatchView {

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var spinner: AppCompatSpinner
    private lateinit var spinnerAdapter: ArrayAdapter<String>
    private lateinit var views: View
    private lateinit var presenter: Presenter<MatchView>
    private lateinit var leagueId: String
    private lateinit var recyclerViewAdapter: NextAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = Presenter(
                this,
                Repository()
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        views = inflater.inflate(R.layout.fragment_next, container, false)
        spinner = views.next_spinner
        swipeRefreshLayout = views.next_swipe_refresh
        recyclerView = views.next_rv

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
            presenter.getNextMatch(leagueId)
        }

        presenter = Presenter(
                this,
                Repository()
        )
        presenter.getNextMatch(resources.getStringArray(R.array.league_id)[0].toString())
        setHasOptionsMenu(true)
        return views
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.search, menu)
        val searchActionMenuItem = menu?.findItem(R.id.menu_search)
        val searchView = searchActionMenuItem?.actionView as android.support.v7.widget.SearchView
        searchView.queryHint = "Search Event"
        searchView.isSubmitButtonEnabled = true
        searchView.setOnQueryTextListener(object : android.support.v7.widget.SearchView.OnQueryTextListener {
            var leagueId = resources.getStringArray(R.array.league_id)
                    .get(resources.getStringArray(R.array.league).indexOf(spinner.selectedItem.toString()))
            override fun onQueryTextSubmit(p0: String): Boolean {
                if (p0 != "") {
                    recyclerViewAdapter = NextAdapter(context, recyclerViewAdapter.filter(p0)) {
                        startActivity<MatchDetail>("id" to it?.idEvent)
                    }
                    recyclerView.adapter = recyclerViewAdapter
                } else
                    presenter.getNextMatch(leagueId)
                return true
            }

            override fun onQueryTextChange(p0: String): Boolean {
                if (p0 != "") {
                    recyclerViewAdapter = NextAdapter(context, recyclerViewAdapter.filter(p0)) {
                        startActivity<MatchDetail>("id" to it?.idEvent)
                    }
                    recyclerView.adapter = recyclerViewAdapter
                } else
                    presenter.getNextMatch(leagueId)
                return true
            }
        })
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        presenter.getNextMatch(resources.getStringArray(R.array.league_id)[position].toString())
    }

    override fun onShowLoading() {
        swipeRefreshLayout.isRefreshing = true
    }

    override fun onListMatch(data: APIResponse.Matches) {
        swipeRefreshLayout.isRefreshing = false
        recyclerViewAdapter = NextAdapter(context, data.events) {
            startActivity<MatchDetail>("id" to it?.idEvent)
        }
        recyclerView.adapter = recyclerViewAdapter
    }

    override fun onDataError(message: String) {
        swipeRefreshLayout.isRefreshing = false
        toast(message)
    }
}
