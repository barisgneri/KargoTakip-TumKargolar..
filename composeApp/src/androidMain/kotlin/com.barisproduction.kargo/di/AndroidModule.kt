package com.barisproduction.kargo.di

import com.barisproduction.kargo.AndroidPlatform
import com.barisproduction.kargo.Platform
import com.barisproduction.kargo.common.service.ClipboardService
import com.barisproduction.kargo.data.preferences.ReviewPreferenceStore
import org.koin.dsl.module
import com.barisproduction.kargo.common.service.AndroidClipboardService
import com.barisproduction.kargo.data.local.getDatabaseBuilder
import com.barisproduction.kargo.data.local.getRoomDatabase

val androidModule = module {
    single<ClipboardService> { AndroidClipboardService(context = get()) }
    single { getRoomDatabase(getDatabaseBuilder(get())) }
    single { ReviewPreferenceStore(get()) }

    single<Platform> { AndroidPlatform(context = get()) }
}
