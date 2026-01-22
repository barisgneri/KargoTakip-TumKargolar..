package com.barisproduction.kargo

import android.app.Application
import com.barisproduction.kargo.di.androidModule
import com.barisproduction.kargo.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApp: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@MainApp)
            modules(androidModule)
        }
    }
}