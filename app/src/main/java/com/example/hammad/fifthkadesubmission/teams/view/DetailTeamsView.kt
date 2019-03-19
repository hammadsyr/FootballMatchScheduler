package com.example.hammad.fifthkadesubmission.teams.view

import com.example.hammad.fifthkadesubmission.util.APIResponse

interface DetailTeamsView : DetailTeamsCallback<APIResponse.ListTeams> {
    fun onShowLoading()
}