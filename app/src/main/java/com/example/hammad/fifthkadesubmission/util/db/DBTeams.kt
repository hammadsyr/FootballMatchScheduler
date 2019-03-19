package com.example.hammad.fifthkadesubmission.util.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.hammad.fifthkadesubmission.favorites.model.FavTeams
import org.jetbrains.anko.db.*

class DBTeams(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "teams.db", null, 1) {
    companion object {
        private var instance: DBTeams? = null

        @Synchronized
        fun getInstance(ctx: Context): DBTeams {
            if (instance == null) {
                instance = DBTeams(ctx.applicationContext)
            }
            return instance as DBTeams
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Here you create tables
        db.createTable(
            FavTeams.TABLE_NAME, true,
            FavTeams.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            FavTeams.ID_TEAM to TEXT + UNIQUE,
            FavTeams.TEAM_NAME to TEXT,
            FavTeams.TEAM_BADGE to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Here you can upgrade tables, as usual
        db.dropTable(FavTeams.TABLE_NAME, true)
    }
}

val Context.teamsDB: DBTeams
    get() = DBTeams.getInstance(applicationContext)