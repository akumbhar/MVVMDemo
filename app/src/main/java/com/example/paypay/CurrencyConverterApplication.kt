package com.example.paypay

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import timber.log.Timber.DebugTree


@HiltAndroidApp
class CurrencyConverterApplication : Application() {

    companion object {
        lateinit var instance: CurrencyConverterApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        Timber.plant(DebugTree())
    }
}