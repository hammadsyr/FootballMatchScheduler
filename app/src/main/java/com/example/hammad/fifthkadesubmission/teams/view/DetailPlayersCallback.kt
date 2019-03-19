package com.example.hammad.fifthkadesubmission.teams.view

interface DetailPlayersCallback<T> {
    fun onDetailPlayers(data : T)
    fun onDataError(message : String)
}