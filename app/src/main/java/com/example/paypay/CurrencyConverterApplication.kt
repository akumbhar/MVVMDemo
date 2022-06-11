package com.example.paypay

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CurrencyConverterApplication : Application() {

    companion object {
        lateinit var instance: CurrencyConverterApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}