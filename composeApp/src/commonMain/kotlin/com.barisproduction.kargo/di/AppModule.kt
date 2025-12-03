package com.barisproduction.kargo.di

import androidx.lifecycle.SavedStateHandle
import com.barisproduction.kargo.data.repository.MainRepositoryImpl
import com.barisproduction.kargo.domain.repository.MainRepository
import com.barisproduction.kargo.ui.splash.SplashViewModel
import com.barisproduction.kargo.ui.cargoList.CargoListViewModel
import com.barisproduction.kargo.ui.savedCargo.SavedCargoViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val dataModule = module {
    single<MainRepository> { MainRepositoryImpl() }
    single { SavedStateHandle() }
}

val viewModelModule = module {
    factoryOf(::SplashViewModel)
    factoryOf(::CargoListViewModel)
    factoryOf(::SavedCargoViewModel)
}

fun initKoin() {
    startKoin {
        modules(
            dataModule,
            viewModelModule,
        )
    }
}