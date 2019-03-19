package com.example.hammad.fifthkadesubmission.teams.view

interface TeamsCallback<T> {
    fun onListTeams(data: T)
    fun onDataError(message : String)
}