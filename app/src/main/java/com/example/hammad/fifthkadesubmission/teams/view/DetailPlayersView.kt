package com.example.hammad.fifthkadesubmission.teams.view

import com.example.hammad.fifthkadesubmission.util.APIResponse

interface DetailPlayersView : DetailPlayersCallback<APIResponse.DetailPlayer> {
    fun onShowLoading()
}