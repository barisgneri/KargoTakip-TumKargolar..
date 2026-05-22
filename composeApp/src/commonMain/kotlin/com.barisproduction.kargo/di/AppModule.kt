package com.barisproduction.kargo.di

import com.barisproduction.kargo.data.local.AppDatabase
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

val appModule = module {
    single { get<AppDatabase>().cargoDao() }
}

fun initKoin(database: AppDatabase, appDeclaration: KoinAppDeclaration = {}) {
    startKoin {
        appDeclaration()
        val databaseModule = module {
            single { database }
        }
        modules(
            databaseModule,
            appModule,
            networkModule,
            repositoryModule,
            useCaseModule,
            viewModelModule
        )
    }
}
