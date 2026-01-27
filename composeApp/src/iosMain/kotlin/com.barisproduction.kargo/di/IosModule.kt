package com.barisproduction.kargo.di

import com.barisproduction.kargo.data.local.getDatabaseBuilder
import com.barisproduction.kargo.data.local.getRoomDatabase
import org.koin.dsl.module

val iosModule = module {
    single { getRoomDatabase(getDatabaseBuilder()) }
}
