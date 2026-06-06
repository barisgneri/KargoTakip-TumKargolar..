package com.barisproduction.kargo.di

import com.barisproduction.kargo.ui.addCargo.AddCargoViewModel
import com.barisproduction.kargo.ui.cargoList.CargoListViewModel
import com.barisproduction.kargo.ui.saveDialog.CargoDialogViewModel
import com.barisproduction.kargo.ui.settings.SettingsViewModel
import com.barisproduction.kargo.ui.splash.SplashViewModel
import com.barisproduction.kargo.ui.tracking.TrackingViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::SplashViewModel)
    viewModelOf(::CargoListViewModel)
    viewModelOf(::AddCargoViewModel)
    viewModelOf(::TrackingViewModel)
    viewModelOf(::CargoDialogViewModel)
    viewModelOf(::SettingsViewModel)
}
