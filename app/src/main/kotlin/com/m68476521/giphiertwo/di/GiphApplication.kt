package com.m68476521.giphiertwo.di

import android.app.Application
import com.morozco.data.local.AppDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GiphApplication : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
}
