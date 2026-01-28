package com.barisproduction.kargo.di

import com.barisproduction.kargo.ui.splash.SplashViewModel
import com.barisproduction.kargo.ui.cargoList.CargoListViewModel
import com.barisproduction.kargo.ui.addCargo.AddCargoViewModel
import com.barisproduction.kargo.ui.tracking.TrackingViewModel
import org.koin.core.context.startKoin
import com.barisproduction.kargo.data.repository.NetworkRepositoryImpl
import com.barisproduction.kargo.domain.repository.NetworkRepository
import com.barisproduction.kargo.domain.usecase.CheckNetworkUseCase
import com.barisproduction.kargo.data.local.AppDatabase
import com.barisproduction.kargo.data.repository.LocalRepositoryImpl
import com.barisproduction.kargo.domain.repository.LocalRepository
import com.barisproduction.kargo.domain.usecase.GetCargosUseCase
import com.barisproduction.kargo.domain.usecase.InsertCargoUseCase
import com.barisproduction.kargo.domain.usecase.CheckCargoInDBUseCase
import com.barisproduction.kargo.domain.usecase.DeleteCargoUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import org.koin.core.module.dsl.viewModelOf

val dataModule = module {
    single { io.ktor.client.HttpClient() }
    single<NetworkRepository> { NetworkRepositoryImpl(get()) }
    single<LocalRepository> { LocalRepositoryImpl(get()) }
    single { get<AppDatabase>().cargoDao() }
    
    // UseCases
    factoryOf(::InsertCargoUseCase)
    factoryOf(::GetCargosUseCase)
    factoryOf(::CheckNetworkUseCase)
    factoryOf(::DeleteCargoUseCase)
    factoryOf(::CheckCargoInDBUseCase)
}

val viewModelModule = module {
    viewModelOf(::SplashViewModel)
    viewModelOf(::CargoListViewModel)
    viewModelOf(::AddCargoViewModel)
    viewModelOf(::TrackingViewModel)
}

fun initKoin(database: AppDatabase, appDeclaration: KoinAppDeclaration = {}) {
    startKoin {
        appDeclaration()
        val databaseModule = module {
            single { database }
        }
        modules(
            databaseModule,
            dataModule,
            viewModelModule
        )
    }
}
