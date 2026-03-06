package com.barisproduction.kargo.domain.repository

import com.barisproduction.kargo.common.Resource
import kotlinx.coroutines.flow.Flow

interface NetworkRepository {
    suspend fun isInternetAvailable(): Flow<Resource<Boolean>>
}
