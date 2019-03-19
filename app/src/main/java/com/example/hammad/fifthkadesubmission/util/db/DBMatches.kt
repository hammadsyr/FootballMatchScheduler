package com.example.hammad.fifthkadesubmission.util.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.hammad.fifthkadesubmission.favorites.model.FavMatches
import org.jetbrains.anko.db.*

class DBMatches(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "matches.db", null, 1) {
    companion object {
        private var instance: DBMatches? = null

        @Synchronized
        fun getInstance(ctx: Context): DBMatches {
            if (instance == null) {
                instance = DBMatches(ctx.applicationContext)
            }
            return instance as DBMatches
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Here you create tables
        db.createTable(
            FavMatches.TABLE_NAME, true,
            FavMatches.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            FavMatches.ID_EVENT to TEXT + UNIQUE,
            FavMatches.EVENT_DATE to TEXT,
            FavMatches.EVENT_TIME to TEXT,
            FavMatches.HOME_TEAM to TEXT,
            FavMatches.AWAY_TEAM to TEXT,
            FavMatches.HOME_SCORE to TEXT,
            FavMatches.AWAY_SCORE to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Here you can upgrade tables, as usual
        db.dropTable(FavMatches.TABLE_NAME, true)
    }
}

val Context.detailDB: DBMatches
    get() = DBMatches.getInstance(applicationContext)