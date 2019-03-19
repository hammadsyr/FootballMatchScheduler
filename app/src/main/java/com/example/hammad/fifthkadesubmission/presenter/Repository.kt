package com.example.hammad.fifthkadesubmission.presenter

import com.example.hammad.fifthkadesubmission.matches.view.DetailMatchCallback
import com.example.hammad.fifthkadesubmission.matches.view.MatchCallback
import com.example.hammad.fifthkadesubmission.teams.view.*
import com.example.hammad.fifthkadesubmission.util.API
import com.example.hammad.fifthkadesubmission.util.APIResponse
import com.example.hammad.fifthkadesubmission.util.RetrofitClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit

class Repository {
    private lateinit var retrofit: Retrofit
    private lateinit var jsonApi: API
    private lateinit var compositeDisposable: CompositeDisposable

    fun setup() {
        retrofit = RetrofitClient.instance
        jsonApi = retrofit.create(API::class.java)
        compositeDisposable = CompositeDisposable()
    }

    fun getPrevMatch(id: String, callback: MatchCallback<APIResponse.Matches>) {
        setup()
        compositeDisposable.add(jsonApi.lastMatch(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ data ->
                    data.let {
                        callback.onListMatch(it)
                    }
                }, {
                    callback.onDataError(it.message!!)
                }))
    }

    fun getNextMatch(id: String, callback: MatchCallback<APIResponse.Matches>) {
        setup()
        compositeDisposable.add(jsonApi.nextMatch(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ data ->
                    data.let {
                        callback.onListMatch(it)
                    }
                }, {
                    callback.onDataError(it.message!!)
                }))
    }

    fun getDetailMatch(id: String, callback : DetailMatchCallback) {
        setup()
        compositeDisposable.add(jsonApi.detailMatch(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ data ->
                    data.let {
                        callback.onDetailMatch(it)
                    }
                }, {
                    callback.onDataError(it.message!!)
                }))
    }

    fun getListTeams(id: String, callback: TeamsCallback<APIResponse.ListTeams>) {
        setup()
        compositeDisposable.add(jsonApi.listTeam(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ data ->
                    data.let {
                        callback.onListTeams(it)
                    }
                }, {
                    callback.onDataError(it.message!!)
                }))
    }

    fun getDetailTeam(id: String, callback: DetailTeamsCallback<APIResponse.ListTeams>) {
        setup()
        compositeDisposable.add(jsonApi.detailTeam(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ data ->
                    data.let {
                        callback.onDetailTeams(it)
                    }
                }, {
                    callback.onDataError(it.message!!)
                }))
    }

    fun getListPlayer(id: String, callback: PlayersCallback<APIResponse.ListPlayer>) {
        setup()
        compositeDisposable.add(jsonApi.listPlayer(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ data ->
                data.let {
                    callback.onListPlayers(it)
                }
            }, {
                callback.onDataError(it.message!!)
            }))
    }

    fun getDetailPlayer(id: String, callback: DetailPlayersCallback<APIResponse.DetailPlayer>) {
        setup()
        compositeDisposable.add(jsonApi.detailPlayer(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ data ->
                data.let {
                    callback.onDetailPlayers(it)
                }
            }, {
                callback.onDataError(it.message!!)
            }))
    }
}