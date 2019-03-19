package com.example.hammad.fifthkadesubmission.teams.view

import com.example.hammad.fifthkadesubmission.util.APIResponse

interface TeamsView : TeamsCallback<APIResponse.ListTeams> {
    fun onShowLoading()
}
