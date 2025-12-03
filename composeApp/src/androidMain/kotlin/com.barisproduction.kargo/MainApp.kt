package com.barisproduction.kargo

import android.app.Application
import com.barisproduction.kargo.di.initKoin

class MainApp: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }
}