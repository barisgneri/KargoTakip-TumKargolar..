package com.barisproduction.kargo

import android.app.Application
import androidx.room.Room
import com.barisproduction.kargo.data.local.AppDatabase
import com.barisproduction.kargo.data.local.getDatabaseBuilder
import com.barisproduction.kargo.data.local.getRoomDatabase
import com.barisproduction.kargo.di.androidModule
import com.barisproduction.kargo.di.initKoin
import org.koin.android.ext.koin.androidContext

class MainApp: Application() {
    override fun onCreate() {
        super.onCreate()

        val database = getRoomDatabase(getDatabaseBuilder(
            applicationContext
        ))

        initKoin(database = database) {
            androidContext(this@MainApp)
            modules(androidModule)
        }
    }
}