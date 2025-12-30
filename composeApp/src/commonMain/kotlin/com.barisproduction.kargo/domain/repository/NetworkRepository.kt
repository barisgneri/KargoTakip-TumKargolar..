package com.barisproduction.kargo.domain.repository

interface NetworkRepository {
    suspend fun isInternetAvailable(): Boolean
}
