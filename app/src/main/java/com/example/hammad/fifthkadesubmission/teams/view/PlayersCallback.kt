package com.example.hammad.fifthkadesubmission.teams.view

interface PlayersCallback <T> {
    fun onListPlayers(data: T)
    fun onDataError(message : String)
}