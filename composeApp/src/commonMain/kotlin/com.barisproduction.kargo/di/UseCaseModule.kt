package com.barisproduction.kargo.di

import com.barisproduction.kargo.domain.usecase.*
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val useCaseModule = module {
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
    factoryOf(::SetCountryUseCase)
    factoryOf(::GetCurrentCountryUseCase)
    factoryOf(::GetSelectedCountryUseCase)
    factoryOf(::GetSelectedLanguageUseCase)
    factoryOf(::GetThemeUseCase)
    factoryOf(::SetThemeUseCase)
    factoryOf(::SetLanguageUseCase)
    factoryOf(::GetSystemCountryCodeUseCase)
}
