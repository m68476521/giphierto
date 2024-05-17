package com.m68476521.giphiertwo.di

import android.app.Application
import com.m68476521.giphiertwo.data.AppDatabase
import com.m68476521.giphiertwo.data.GiphRepository
import dagger.hilt.android.HiltAndroidApp

// Description:
// Container that is attached to the app's lifecycle
@HiltAndroidApp
class GiphApplication : Application() {
    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { GiphRepository(database.imageDao()) }
}
