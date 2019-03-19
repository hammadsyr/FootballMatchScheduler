package com.example.hammad.fifthkadesubmission.presenter

import android.util.Log
import com.example.hammad.fifthkadesubmission.matches.view.DetailMatchCallback
import com.example.hammad.fifthkadesubmission.matches.view.DetailMatchView
import com.example.hammad.fifthkadesubmission.matches.view.MatchCallback
import com.example.hammad.fifthkadesubmission.matches.view.MatchView
import com.example.hammad.fifthkadesubmission.teams.view.*
import com.example.hammad.fifthkadesubmission.util.APIResponse

class Presenter<T>(val view: T, val repository: Repository) {
    fun getPrevMatch(id: String) {
        if (view is MatchView){
            view.onShowLoading()
            repository.getPrevMatch(id, object : MatchCallback<APIResponse.Matches> {
                override fun onListMatch(data: APIResponse.Matches) {
                    view.onListMatch(data)
                }

                override fun onDataError(message : String) {
                    view.onDataError(message)
                }
            })
        }


    }
    fun getNextMatch(id: String) {
        if (view is MatchView){
            view.onShowLoading()
            repository.getNextMatch(id, object : MatchCallback<APIResponse.Matches> {
                override fun onListMatch(data: APIResponse.Matches) {
                    view.onListMatch(data)
                }

                override fun onDataError(message : String) {
                    view.onDataError(message)
                }
            })
        }


    }
    fun getDetailMatch(id: String) {
        if (view is DetailMatchView){
            view.onShowLoading()
            repository.getDetailMatch(id, object : DetailMatchCallback {
                override fun onDetailMatch(data: APIResponse.Matches) {
                    view.onDetailMatch(data)
                }

                override fun onDataError(message : String) {
                    view.onDataError(message)
                }
            })
        }

    }

    fun getListTeams(id: String) {
        if (view is TeamsView){
            view.onShowLoading()
            repository.getListTeams(id, object : TeamsCallback<APIResponse.ListTeams> {
                override fun onListTeams(data: APIResponse.ListTeams) {
                    view.onListTeams(data)
                }

                override fun onDataError(message: String) {
                    view.onDataError(message)
                }
            })

        }

    }

    fun getDetailTeams(id: String) {
        if (view is DetailTeamsView){
            view.onShowLoading()
            repository.getDetailTeam(id, object : DetailTeamsCallback<APIResponse.ListTeams> {
                override fun onDetailTeams(data: APIResponse.ListTeams) {
                    Log.e("datanya ", "$data")
                    view.onDetailTeams(data)
                }

                override fun onDataError(message: String) {
                    view.onDataError(message)
                }
            })
        }

    }

    fun getListPlayers(id: String) {
        if (view is PlayersView){
            view.onShowLoading()
            repository.getListPlayer(id, object : PlayersCallback<APIResponse.ListPlayer> {
                override fun onListPlayers(data: APIResponse.ListPlayer) {
                    view.onListPlayers(data)
                }

                override fun onDataError(message: String) {
                    view.onDataError(message)
                }
            })
        }

    }

    fun getDetailPlayer(id: String) {
        if(view is DetailPlayersView){
            view.onShowLoading()
            repository.getDetailPlayer(id, object : DetailPlayersCallback<APIResponse.DetailPlayer> {
                override fun onDetailPlayers(data: APIResponse.DetailPlayer) {
                    view.onDetailPlayers(data)
                }

                override fun onDataError(message: String) {
                    view.onDataError(message)
                }
            })

        }

    }
}