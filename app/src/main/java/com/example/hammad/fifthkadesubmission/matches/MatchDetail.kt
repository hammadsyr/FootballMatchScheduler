package com.example.hammad.fifthkadesubmission.matches

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.bumptech.glide.Glide
import com.example.hammad.fifthkadesubmission.R
import com.example.hammad.fifthkadesubmission.favorites.model.FavMatches
import com.example.hammad.fifthkadesubmission.matches.model.Match
import com.example.hammad.fifthkadesubmission.matches.view.DetailMatchView
import com.example.hammad.fifthkadesubmission.presenter.Presenter
import com.example.hammad.fifthkadesubmission.presenter.Repository
import com.example.hammad.fifthkadesubmission.teams.view.DetailTeamsView
import com.example.hammad.fifthkadesubmission.util.APIResponse
import com.example.hammad.fifthkadesubmission.util.ConvertDate
import com.example.hammad.fifthkadesubmission.util.db.detailDB
import kotlinx.android.synthetic.main.activity_match_detail.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.toast


class MatchDetail : AppCompatActivity(), DetailMatchView, DetailTeamsView {
    private var isFavorite: Boolean = false
    private var menuItem: Menu? = null
    private var toolbar : ActionBar? = null
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var matchPresenter : Presenter<DetailMatchView>
    private lateinit var teamsPresenter : Presenter<DetailTeamsView>
    private lateinit var eventId : String
    private lateinit var eventData : Match

    companion object {
        var i = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_detail)

        swipeRefreshLayout = match_detail_swipe_refresh
        toolbar = supportActionBar
        toolbar?.setTitle("Match Detail")
        toolbar?.setDisplayHomeAsUpEnabled(true);

        eventId = intent.getStringExtra("id")

        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_red_light,
                android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light
        )

        swipeRefreshLayout.onRefresh {
            matchPresenter.getDetailMatch(eventId)
        }

        matchPresenter = Presenter(this, Repository())
        matchPresenter.getDetailMatch(eventId)
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
            detailDB.use {
                val result = select(FavMatches.TABLE_NAME)
                    .whereArgs("${FavMatches.ID_EVENT} = $eventId")
                val favorite = result.parseList(classParser<FavMatches>())
                if (!favorite.isEmpty()) isFavorite = true
                setFavorite()
            }
        } catch (e: SQLiteConstraintException) {
            swipeRefreshLayout.snackbar("$e")
        }
    }

    private fun setFavorite() {
        if(isFavorite){
            i = 1
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_added_to_favorites)
        }
        else{
            i = 0
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_add_to_favorites)
        }
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
            detailDB.use {
                insert(FavMatches.TABLE_NAME,
                    FavMatches.ID_EVENT to eventId,
                    FavMatches.EVENT_DATE to tv_detail_date.text.toString(),
                    FavMatches.EVENT_TIME to tv_detail_time.text.toString(),
                    FavMatches.HOME_TEAM to tv_detail_team_home.text.toString(),
                    FavMatches.AWAY_TEAM to tv_detail_team_away.text.toString(),
                    FavMatches.HOME_SCORE to tv_detail_score_home.text.toString(),
                    FavMatches.AWAY_SCORE to tv_detail_score_away.text.toString())
            }
            swipeRefreshLayout.snackbar(getString(R.string.added_to_favorite))
        } catch (e: SQLiteConstraintException){
            swipeRefreshLayout.snackbar("Error : $e")
        }
    }

    private fun removeFromFavorite(){
        try {
            detailDB.use {
                delete(FavMatches.TABLE_NAME, "${FavMatches.ID_EVENT} = $eventId")
            }
            swipeRefreshLayout.snackbar(getString(R.string.removed_from_favorite))
        }catch (e: SQLiteConstraintException){
            swipeRefreshLayout.snackbar("The Error : $e")
        }
    }

    override fun onShowLoading() {
        swipeRefreshLayout.isRefreshing = true
    }

    override fun onDataError(message: String) {
        swipeRefreshLayout.isRefreshing = false
        toast(message)
    }

    override fun onDetailMatch(data: APIResponse.Matches) {
        swipeRefreshLayout.isRefreshing = false
        eventData = data.events[0]
        tv_detail_date.text = ConvertDate().convertDate(eventData.dateEvent!!, eventData.strTime!!)
        tv_detail_time.text = ConvertDate().convertTime(eventData.dateEvent!!, eventData.strTime!!)
        tv_detail_team_home.text = eventData.strHomeTeam
        tv_detail_team_away.text = eventData.strAwayTeam
        if(eventData.intAwayScore!=null){
            tv_detail_score_home.text = eventData.intHomeScore.toString()
            tv_detail_score_away.text = eventData.intAwayScore.toString()
            tv_detail_goals_home.text = eventData.strHomeGoalDetails.toString()
            tv_detail_goals_away.text = eventData.strAwayGoalDetails.toString()
            tv_detail_shots_home.text = eventData.intHomeShots.toString()
            tv_detail_shots_away.text = eventData.intAwayShots.toString()
            tv_detail_gk_home.text = eventData.strHomeLineupGoalkeeper.toString()
            tv_detail_gk_away.text = eventData.strAwayLineupGoalkeeper.toString()
            tv_detail_def_home.text = eventData.strHomeLineupDefense.toString()
            tv_detail_def_away.text = eventData.strAwayLineupDefense.toString()
            tv_detail_mid_home.text = eventData.strHomeLineupMidfield.toString()
            tv_detail_mid_away.text = eventData.strAwayLineupMidfield.toString()
            tv_detail_fwd_home.text = eventData.strHomeLineupForward.toString()
            tv_detail_fwd_away.text = eventData.strAwayLineupForward.toString()
            tv_detail_sub_home.text = eventData.strHomeLineupSubstitutes.toString()
            tv_detail_sub_away.text = eventData.strAwayLineupSubstitutes.toString()
        } else {
            tv_detail_score_home.visibility = View.GONE
            tv_detail_score_away.visibility = View.GONE
            ll_detail.visibility = View.GONE
        }
        teamsPresenter = Presenter(this, Repository())
        teamsPresenter.getDetailTeams(eventData.idHomeTeam.toString())
        teamsPresenter.getDetailTeams(eventData.idAwayTeam.toString())
    }

    override fun onDetailTeams(data: APIResponse.ListTeams) {
        swipeRefreshLayout.isRefreshing = false
        val dataTeams = data.teams[0]
        if (dataTeams.idTeam.equals(eventData.idHomeTeam)) {
            Glide.with(this).load(dataTeams.strTeamBadge).into(iv_detail_home)
        } else if (dataTeams.idTeam.equals(eventData.idAwayTeam)) {
            Glide.with(this).load(dataTeams.strTeamBadge).into(iv_detail_away)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}