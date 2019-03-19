package com.example.hammad.fifthkadesubmission.teams

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.example.hammad.fifthkadesubmission.R
import com.example.hammad.fifthkadesubmission.favorites.model.FavTeams
import com.example.hammad.fifthkadesubmission.favorites.model.Team
import com.example.hammad.fifthkadesubmission.matches.adapter.MatchesTabAdapter
import com.example.hammad.fifthkadesubmission.presenter.Presenter
import com.example.hammad.fifthkadesubmission.presenter.Repository
import com.example.hammad.fifthkadesubmission.teams.fragment.OverviewFragment
import com.example.hammad.fifthkadesubmission.teams.fragment.PlayerFragment
import com.example.hammad.fifthkadesubmission.teams.view.DetailTeamsView
import com.example.hammad.fifthkadesubmission.util.APIResponse
import com.example.hammad.fifthkadesubmission.util.db.teamsDB
import kotlinx.android.synthetic.main.activity_teams_detail.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.toast


class TeamsDetail : AppCompatActivity(), DetailTeamsView {
    private var isFavorite: Boolean = false
    private lateinit var teamsPresenter : Presenter<DetailTeamsView>
    private var menuItem: Menu? = null
    private var teamId : String = ""
    private lateinit var teams : Team
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teams_detail)
        setSupportActionBar(toolbar_teams)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        appbar_layout.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            var isShow = true
            var scrollRange = -1

            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsing_toolbar.title = "Team Detail"
                    isShow = true
                } else if (isShow) {
                    collapsing_toolbar.title =
                        " "//carefull there should a space between double quote otherwise it wont work
                    isShow = false
                }
            }

        })
        setupViewPager(vp_teams)
        tab_teams.setupWithViewPager(vp_teams)
        teamId = intent.getStringExtra("id")
        teamsPresenter = Presenter(this, Repository())
        teamsPresenter.getDetailTeams(teamId)
        favoriteState()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    private fun favoriteState(){
        try {
            teamsDB.use {
                val result = select(FavTeams.TABLE_NAME)
                        .whereArgs("${FavTeams.ID_TEAM} = $teamId")
                val favorite = result.parseList(classParser<FavTeams>())
                if (!favorite.isEmpty()) isFavorite = true
                setFavorite()
            }
        } catch (e: SQLiteConstraintException) {
            toast("$e")
        }
    }

    private fun setFavorite() {
        if(isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_add_to_favorites)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.add_to_favourite -> {
                if(isFavorite)removeFromFavorite() else addToFavorite()
                isFavorite = !isFavorite
                setFavorite()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addToFavorite(){
        try {
            teamsDB.use {
                insert(FavTeams.TABLE_NAME,
                        FavTeams.ID_TEAM to teamId,
                        FavTeams.TEAM_NAME to teams.strTeam,
                        FavTeams.TEAM_BADGE to teams.strTeamBadge)
            }
            toast(getString(R.string.added_to_favorite))
        } catch (e: SQLiteConstraintException){
            toast("Error : $e")
        }
    }

    private fun removeFromFavorite(){
        try {
            teamsDB.use {
                delete(FavTeams.TABLE_NAME, "${FavTeams.ID_TEAM} = $teamId")
            }
            toast(getString(R.string.removed_from_favorite))
        }catch (e: SQLiteConstraintException){
            toast("The Error : $e")
        }
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = MatchesTabAdapter(supportFragmentManager)
        adapter.addFragment(OverviewFragment(), resources.getString(R.string.overview))
        adapter.addFragment(PlayerFragment(), resources.getString(R.string.player))
        viewPager.adapter = adapter
    }

    override fun onShowLoading() {
        return
    }

    override fun onDetailTeams(data: APIResponse.ListTeams) {
        val teamsData = data.teams[0]
        teams = Team(teamsData.strTeam,
                teamsData.strTeamBadge)
        Glide.with(this).load(teamsData.strTeamBadge).into(iv_detail_teams)
        tv_detail_teams.text = teamsData.strTeam
        tv_detail_year.text = teamsData.intFormedYear
        tv_detail_stadium.text = teamsData.strStadium
    }

    override fun onDataError(message: String) {
        toast(message)
    }

    fun teamId() : String = teamId

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}