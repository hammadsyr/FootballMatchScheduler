package com.example.hammad.fifthkadesubmission.matches.view

import com.example.hammad.fifthkadesubmission.util.APIResponse

interface DetailMatchCallback {
    fun onDetailMatch(data : APIResponse.Matches)
    fun onDataError(message : String)
}