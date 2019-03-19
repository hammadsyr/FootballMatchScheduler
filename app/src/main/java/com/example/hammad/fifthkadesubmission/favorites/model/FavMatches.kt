package com.example.hammad.fifthkadesubmission.favorites.model

data class FavMatches(val id: Long,
                      val idEvent: String,
                      val dateEvent: String, val strTime : String,
                      val strHomeTeam: String, val strAwayTeam: String,
                      val intHomeScore: String?, val intAwayScore: String?) {

    companion object {
        const val TABLE_NAME = "MATCH_FAVORITE"
        const val ID = "ID_"
        const val ID_EVENT = "ID_EVENT"
        const val EVENT_DATE = "EVENT_DATE"
        const val EVENT_TIME = "EVENT_TIME"
        const val HOME_TEAM = "HOME_TEAM"
        const val AWAY_TEAM = "AWAY_TEAM"
        const val HOME_SCORE = "HOME_SCORE"
        const val AWAY_SCORE = "AWAY_SCORE"
    }
}