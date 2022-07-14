package com.example.assignment

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import timber.log.Timber.DebugTree


@HiltAndroidApp
class SongsApplication : Application() {

    companion object {
        lateinit var instance: SongsApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        Timber.plant(DebugTree())
    }
}