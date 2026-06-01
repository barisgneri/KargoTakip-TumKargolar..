package com.barisproduction.kargo.di

import com.barisproduction.kargo.data.repository.AppConfigRepositoryImpl
import com.barisproduction.kargo.data.repository.CargoRepositoryImpl
import com.barisproduction.kargo.data.repository.LocalRepositoryImpl
import com.barisproduction.kargo.data.repository.ReviewPromptRepositoryImpl
import com.barisproduction.kargo.domain.repository.AppConfigRepository
import com.barisproduction.kargo.domain.repository.CargoRepository
import com.barisproduction.kargo.domain.repository.LocalRepository
import com.barisproduction.kargo.domain.repository.ReviewPromptRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<LocalRepository> { LocalRepositoryImpl(get()) }
    single<CargoRepository> { CargoRepositoryImpl(get(),get()) }
    single<ReviewPromptRepository> { ReviewPromptRepositoryImpl(get()) }
    single<AppConfigRepository> { AppConfigRepositoryImpl(get(), get(), get()) }
}
