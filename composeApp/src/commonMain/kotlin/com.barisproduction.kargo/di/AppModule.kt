package com.barisproduction.kargo.di

import com.barisproduction.kargo.ui.splash.SplashViewModel
import com.barisproduction.kargo.ui.cargoList.CargoListViewModel
import com.barisproduction.kargo.ui.addCargo.AddCargoViewModel
import com.barisproduction.kargo.ui.tracking.TrackingViewModel
import com.barisproduction.kargo.ui.saveDialog.CargoDialogViewModel
import org.koin.core.context.startKoin
import com.barisproduction.kargo.data.local.AppDatabase
import com.barisproduction.kargo.data.remote.CargoRemoteDataSource
import com.barisproduction.kargo.data.remote.FirebaseCargoDataSource
import com.barisproduction.kargo.data.repository.CargoRepositoryImpl
import com.barisproduction.kargo.data.repository.LocalRepositoryImpl
import com.barisproduction.kargo.data.repository.ReviewPromptRepositoryImpl
import com.barisproduction.kargo.data.preferences.ReviewPreferenceStore
import com.barisproduction.kargo.domain.repository.CargoRepository
import com.barisproduction.kargo.domain.repository.LocalRepository
import com.barisproduction.kargo.domain.repository.ReviewPromptRepository
import com.barisproduction.kargo.domain.usecase.GetCargosUseCase
import com.barisproduction.kargo.domain.usecase.InsertCargoUseCase
import com.barisproduction.kargo.domain.usecase.CheckCargoInDBUseCase
import com.barisproduction.kargo.domain.usecase.CheckForceUpdateUseCase
import com.barisproduction.kargo.domain.usecase.DeleteCargoUseCase
import com.barisproduction.kargo.domain.usecase.FindCargoInfoUseCase
import com.barisproduction.kargo.domain.usecase.FetchCargoParcelListUseCase
import com.barisproduction.kargo.domain.usecase.GetReviewCompletedUseCase
import com.barisproduction.kargo.domain.usecase.GetCargoParcelListUseCase
import com.barisproduction.kargo.domain.usecase.SetReviewCompletedUseCase
import com.barisproduction.kargo.domain.usecase.UpdateCargoUseCase
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import io.ktor.client.HttpClient
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

val dataModule = module {
    single<LocalRepository> { LocalRepositoryImpl(get()) }
    single { get<AppDatabase>().cargoDao() }
    single { Firebase.firestore }
    single<CargoRemoteDataSource> { FirebaseCargoDataSource(get()) }
    single<CargoRepository> { CargoRepositoryImpl(get()) }
    single<ReviewPromptRepository> { ReviewPromptRepositoryImpl(get<ReviewPreferenceStore>()) }

    // UseCases
    factoryOf(::InsertCargoUseCase)
    factoryOf(::GetCargosUseCase)
    factoryOf(::DeleteCargoUseCase)
    factoryOf(::CheckCargoInDBUseCase)
    factoryOf(::CheckForceUpdateUseCase)
    factoryOf(::FindCargoInfoUseCase)
    factoryOf(::FetchCargoParcelListUseCase)
    factoryOf(::GetCargoParcelListUseCase)
    factoryOf(::UpdateCargoUseCase)
    factoryOf(::GetReviewCompletedUseCase)
    factoryOf(::SetReviewCompletedUseCase)

}

val viewModelModule = module {
    viewModelOf(::SplashViewModel)
    viewModelOf(::CargoListViewModel)
    viewModelOf(::AddCargoViewModel)
    viewModelOf(::TrackingViewModel)
    viewModelOf(::CargoDialogViewModel)
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
