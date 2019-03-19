package com.example.hammad.fifthkadesubmission.teams.view

import com.example.hammad.fifthkadesubmission.util.APIResponse

interface PlayersView : PlayersCallback<APIResponse.ListPlayer> {
    fun onShowLoading()
}