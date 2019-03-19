package com.example.hammad.fifthkadesubmission.favorites.model

data class FavTeams(val id: Long,
                    val idTeam: String,
                    val strTeam: String, val strTeamBadge: String) {

    companion object {
        const val TABLE_NAME = "TEAM_FAVORITE"
        const val ID = "ID_"
        const val ID_TEAM = "ID_TEAM"
        const val TEAM_NAME = "TEAM_NAME"
        const val TEAM_BADGE = "TEAM_BADGE"
    }
}