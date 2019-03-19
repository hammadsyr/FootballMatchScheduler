package com.example.hammad.fifthkadesubmission.teams.view

interface DetailTeamsCallback<T> {
    fun onDetailTeams(data : T)
    fun onDataError(message : String)
}