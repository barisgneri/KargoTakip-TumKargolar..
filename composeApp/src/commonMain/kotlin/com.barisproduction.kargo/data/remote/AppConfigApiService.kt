package com.barisproduction.kargo.data.remote

import com.barisproduction.kargo.common.Constants
import com.barisproduction.kargo.data.remote.model.CountryResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class AppConfigApiService(private val client: HttpClient) {
    suspend fun getCountries(): List<CountryResponse> {
        return client.get(Constants.CountryListUrl).body()
    }
}
