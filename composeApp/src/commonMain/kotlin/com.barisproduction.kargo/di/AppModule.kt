package com.barisproduction.kargo.di

import com.barisproduction.kargo.data.repository.MainRepositoryImpl
import com.barisproduction.kargo.domain.repository.MainRepository
import com.barisproduction.kargo.ui.splash.SplashViewModel
import com.barisproduction.kargo.ui.cargoList.CargoListViewModel
import com.barisproduction.kargo.ui.addCargo.AddCargoViewModel
import com.barisproduction.kargo.ui.tracking.TrackingViewModel
import org.koin.core.context.startKoin
import com.barisproduction.kargo.data.repository.NetworkRepositoryImpl
import com.barisproduction.kargo.domain.repository.NetworkRepository
import com.barisproduction.kargo.domain.usecase.CheckNetworkUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import org.koin.core.module.dsl.viewModelOf

val dataModule = module {
    single { io.ktor.client.HttpClient() }
    single<MainRepository> { MainRepositoryImpl() }
    single<NetworkRepository> { NetworkRepositoryImpl(get()) }
}

val viewModelModule = module {
    viewModelOf(::SplashViewModel)
    viewModelOf(::CargoListViewModel)
    viewModelOf(::AddCargoViewModel)
    viewModelOf(::TrackingViewModel)
    factoryOf(::CheckNetworkUseCase)
}

fun initKoin(appDeclaration: KoinAppDeclaration = {}) {
    startKoin {
        appDeclaration()
        modules(
            dataModule,
            viewModelModule
        )
    }
}
