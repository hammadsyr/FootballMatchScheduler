package com.example.hammad.fifthkadesubmission.matches.view

interface MatchCallback<T> {
    fun onListMatch(data : T)
    fun onDataError(message : String)
}