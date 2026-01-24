package com.barisproduction.kargo.data.repository

import com.barisproduction.kargo.domain.repository.NetworkRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.head

class NetworkRepositoryImpl(
    private val client: HttpClient
) : NetworkRepository {
    override suspend fun isInternetAvailable(): Boolean {
        return try {
            //head getten hafif sadece başık okur
            val response = client.head("https://8.8.8.8")
            response.status.value in 200..299
        } catch (e: Exception) {
            false
        }
    }
}
