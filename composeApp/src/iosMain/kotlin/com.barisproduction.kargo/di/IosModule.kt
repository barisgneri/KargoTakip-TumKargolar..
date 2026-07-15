package com.barisproduction.kargo.di

import com.barisproduction.kargo.IOSPlatform
import com.barisproduction.kargo.Platform
import com.barisproduction.kargo.data.local.getDatabaseBuilder
import com.barisproduction.kargo.data.local.getRoomDatabase
import com.barisproduction.kargo.data.preferences.IosReviewPreferenceStore
import com.barisproduction.kargo.data.preferences.ReviewPreferenceStore
import com.barisproduction.kargo.data.preferences.AppPreferenceStore
import com.barisproduction.kargo.data.preferences.IosAppPreferenceStore
import com.barisproduction.kargo.common.service.BarcodeScannerService
import com.barisproduction.kargo.common.service.IosBarcodeScannerService
import org.koin.dsl.module

val iosModule = module {
    single<BarcodeScannerService> { IosBarcodeScannerService() }
    single { getRoomDatabase(getDatabaseBuilder()) }
    single<ReviewPreferenceStore> { IosReviewPreferenceStore() }
    single<AppPreferenceStore> { IosAppPreferenceStore() }
    single<Platform> { IOSPlatform() }
}
