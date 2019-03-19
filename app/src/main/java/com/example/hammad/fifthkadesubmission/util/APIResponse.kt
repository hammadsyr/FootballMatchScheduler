package com.example.hammad.fifthkadesubmission.util

import com.example.hammad.fifthkadesubmission.matches.model.Match
import com.example.hammad.fifthkadesubmission.teams.model.Teams
import com.example.hammad.fifthkadesubmission.teams.model.TeamsPlayer
import com.google.gson.annotations.SerializedName

object APIResponse {
    data class Matches(@SerializedName("events") val events: List<Match>)
    data class ListTeams(@SerializedName("teams") val teams: List<Teams>)
    data class ListPlayer(@SerializedName("player") val player: List<TeamsPlayer>)
    data class DetailPlayer(@SerializedName("players") val players: List<TeamsPlayer>)
}