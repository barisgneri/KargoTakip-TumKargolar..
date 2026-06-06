package com.barisproduction.kargo.di

import com.barisproduction.kargo.data.remote.AppConfigApiService
import com.barisproduction.kargo.data.remote.CargoRemoteDataSource
import com.barisproduction.kargo.data.remote.FirebaseCargoDataSource
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val networkModule = module {
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
    single { Firebase.firestore }
    single<CargoRemoteDataSource> { FirebaseCargoDataSource(get()) }
}
