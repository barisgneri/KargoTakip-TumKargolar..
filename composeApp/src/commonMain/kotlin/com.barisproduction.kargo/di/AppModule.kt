package com.barisproduction.kargo.di

import com.barisproduction.kargo.ui.splash.SplashViewModel
import com.barisproduction.kargo.ui.cargoList.CargoListViewModel
import com.barisproduction.kargo.ui.addCargo.AddCargoViewModel
import com.barisproduction.kargo.ui.tracking.TrackingViewModel
import com.barisproduction.kargo.ui.saveDialog.CargoDialogViewModel
import com.barisproduction.kargo.ui.settings.SettingsViewModel
import org.koin.core.context.startKoin
import com.barisproduction.kargo.data.local.AppDatabase
import com.barisproduction.kargo.data.remote.AppConfigApiService
import com.barisproduction.kargo.data.remote.CargoRemoteDataSource
import com.barisproduction.kargo.data.remote.FirebaseCargoDataSource
import com.barisproduction.kargo.data.repository.CargoRepositoryImpl
import com.barisproduction.kargo.data.repository.LocalRepositoryImpl
import com.barisproduction.kargo.data.repository.ReviewPromptRepositoryImpl
import com.barisproduction.kargo.data.repository.AppConfigRepositoryImpl
import com.barisproduction.kargo.data.preferences.ReviewPreferenceStore
import com.barisproduction.kargo.data.preferences.AppPreferenceStore
import com.barisproduction.kargo.domain.repository.CargoRepository
import com.barisproduction.kargo.domain.repository.LocalRepository
import com.barisproduction.kargo.domain.repository.ReviewPromptRepository
import com.barisproduction.kargo.domain.repository.AppConfigRepository
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
import com.barisproduction.kargo.domain.usecase.GetCountriesUseCase
import com.barisproduction.kargo.domain.usecase.GetLanguagesUseCase
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

val dataModule = module {
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    isLenient = true
                }, contentType = ContentType.Any)
            }
        }
    }
    singleOf(::AppConfigApiService)
    single<LocalRepository> { LocalRepositoryImpl(get()) }
    single { get<AppDatabase>().cargoDao() }
    single { Firebase.firestore }
    single<CargoRemoteDataSource> { FirebaseCargoDataSource(get()) }
    single<CargoRepository> { CargoRepositoryImpl(get()) }
    single<ReviewPromptRepository> { ReviewPromptRepositoryImpl(get<ReviewPreferenceStore>()) }
    single<AppConfigRepository> { AppConfigRepositoryImpl(get(), get()) }

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
    factoryOf(::GetCountriesUseCase)
    factoryOf(::GetLanguagesUseCase)

}

val viewModelModule = module {
    viewModelOf(::SplashViewModel)
    viewModelOf(::CargoListViewModel)
    viewModelOf(::AddCargoViewModel)
    viewModelOf(::TrackingViewModel)
    viewModelOf(::CargoDialogViewModel)
    viewModelOf(::SettingsViewModel)
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
