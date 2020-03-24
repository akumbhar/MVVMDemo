package com.example.mvvmdiapplication

import android.app.Application
import com.example.mvvmdiapplication.common.doLogE
import com.example.mvvmdiapplication.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication() : Application() {

    override fun onCreate() {
        super.onCreate()

        doLogE("Starting koin..")
        startKoin {
            androidContext(this@MainApplication)
            modules(appModule)

        }
    }
}