package com.example.hammad.fifthkadesubmission.teams

import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.webkit.WebView
import android.webkit.WebViewClient
import com.bumptech.glide.Glide
import com.example.hammad.fifthkadesubmission.R
import com.example.hammad.fifthkadesubmission.presenter.Presenter
import com.example.hammad.fifthkadesubmission.presenter.Repository
import com.example.hammad.fifthkadesubmission.teams.view.DetailPlayersView
import com.example.hammad.fifthkadesubmission.util.APIResponse
import kotlinx.android.synthetic.main.activity_player_detail.*
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.toast


class PlayerDetail : AppCompatActivity(), DetailPlayersView {

    private var toolbar: ActionBar? = null
    private lateinit var presenter: Presenter<DetailPlayersView>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_detail)

        val playerId = intent.getStringExtra("id")
        val name = intent.getStringExtra("name")
        toolbar = supportActionBar
        toolbar?.setTitle(name)
        toolbar?.setDisplayHomeAsUpEnabled(true);

        presenter = Presenter(this, Repository())

        detail_player_swipe.setColorSchemeResources(
                android.R.color.holo_red_light,
                android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light
        )

        detail_player_swipe.onRefresh {
            presenter.getDetailPlayer(playerId)
        }

        presenter.getDetailPlayer(playerId)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onShowLoading() {
        detail_player_swipe.isRefreshing = true
    }

    override fun onDataError(message: String) {
        detail_player_swipe.isRefreshing = false
        toast(message)
    }

    override fun onDetailPlayers(data: APIResponse.DetailPlayer) {
        detail_player_swipe.isRefreshing = false
        val playerData = data.players[0]
        Glide.with(this).load(playerData.strFanart2).into(iv_detail_player)
        tv_detail_player_weight.text = playerData.strWeight
        tv_detail_player_height.text = playerData.strHeight
        tv_detail_player_position.text = playerData.strPosition
        wv_detail_player_description.getSettings().setJavaScriptEnabled(true)
        wv_detail_player_description.setWebViewClient(object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }

            override fun onReceivedError(view: WebView, errorCode: Int, description: String, failingUrl: String) {
                toast(description)
            }

        })
        wv_detail_player_description.loadData(playerData.strDescriptionEN, "text/html", "UTF-8")
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}