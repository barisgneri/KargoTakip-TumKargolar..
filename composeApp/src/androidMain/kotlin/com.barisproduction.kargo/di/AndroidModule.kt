package com.barisproduction.kargo.di

import com.barisproduction.kargo.common.service.ClipboardService
import org.koin.dsl.module
import com.barisproduction.kargo.service.AndroidClipboardService
import com.barisproduction.kargo.data.local.getDatabaseBuilder
import com.barisproduction.kargo.data.local.getRoomDatabase

val androidModule = module {
    single<ClipboardService> { AndroidClipboardService(context = get()) }
    single { getRoomDatabase(getDatabaseBuilder(get())) }
}
