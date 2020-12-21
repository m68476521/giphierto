package com.m68476521.giphierto.data

import android.app.Application

class GiphApplication : Application() {
        // Using by lazy so the database and the repository are only created when they're needed
        // rather than when the application starts
        val database by lazy { AppDatabase.getDatabase(this) }
        val repository by lazy { GiphRepository(database.imageDao()) }
    }

