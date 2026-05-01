package com.barisproduction.kargo

import androidx.compose.ui.window.ComposeUIViewController
import androidx.room.Room
import com.barisproduction.kargo.data.local.AppDatabase
import com.barisproduction.kargo.data.local.AppDatabaseConstructor
import com.barisproduction.kargo.data.local.getRoomDatabase
import com.barisproduction.kargo.di.initKoin
import com.barisproduction.kargo.di.iosModule
import platform.Foundation.NSHomeDirectory

fun MainViewController() = ComposeUIViewController {

    val dbFilePath = NSHomeDirectory() + "/kargo.db"

    val builder = Room.databaseBuilder<AppDatabase>(
        name = dbFilePath,
        factory = { AppDatabaseConstructor.initialize() }
    )

    val database = getRoomDatabase(builder)

    initKoin(database = database) {
        modules(iosModule)
    }
    App()
}