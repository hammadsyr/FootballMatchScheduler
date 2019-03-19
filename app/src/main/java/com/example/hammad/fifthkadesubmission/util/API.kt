package com.example.hammad.fifthkadesubmission.util

import retrofit2.http.GET
import io.reactivex.Observable
import retrofit2.http.Query

interface API {
    @GET("eventspastleague.php")
    fun lastMatch(@Query("id")id:String):Observable<APIResponse.Matches>

    @GET("eventsnextleague.php")
    fun nextMatch(@Query("id")id:String):Observable<APIResponse.Matches>

    @GET("lookupevent.php")
    fun detailMatch(@Query("id")id:String):Observable<APIResponse.Matches>

    @GET("lookup_all_teams.php")
    fun listTeam(@Query("id")id:String):Observable<APIResponse.ListTeams>

    @GET("lookupteam.php")
    fun detailTeam(@Query("id")id:String):Observable<APIResponse.ListTeams>

    @GET("lookup_all_players.php")
    fun listPlayer(@Query("id")id:String):Observable<APIResponse.ListPlayer>

    @GET("lookupplayer.php")
    fun detailPlayer(@Query("id")id:String):Observable<APIResponse.DetailPlayer>


}