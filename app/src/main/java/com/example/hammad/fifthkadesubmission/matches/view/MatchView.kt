package com.example.hammad.fifthkadesubmission.matches.view

import com.example.hammad.fifthkadesubmission.util.APIResponse

interface MatchView : MatchCallback<APIResponse.Matches>{
    fun onShowLoading()
}