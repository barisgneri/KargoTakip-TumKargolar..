package com.barisproduction.kargo

import android.app.Application
import com.barisproduction.kargo.data.local.getDatabaseBuilder
import com.barisproduction.kargo.data.local.getRoomDatabase
import com.barisproduction.kargo.di.androidModule
import com.barisproduction.kargo.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.mp.KoinPlatform

class MainApp: Application() {
    override fun onCreate() {
        super.onCreate()

        val database = getRoomDatabase(getDatabaseBuilder(
            applicationContext
        ))

        initKoin(database = database) {
            // Android Context'ini Koin'e tanıtıyoruz
            androidContext(this@MainApp)
            modules(androidModule)
        }
    }
}
actual fun getPlatform(): Platform {
    return KoinPlatform.getKoin().get<Platform>()
}
