package com.barisproduction.kargo.domain.usecase

import com.barisproduction.kargo.common.Resource
import com.barisproduction.kargo.domain.repository.NetworkRepository
import kotlinx.coroutines.flow.Flow

class CheckNetworkUseCase(private val networkRepository: NetworkRepository) {
    suspend operator fun invoke(): Flow<Resource<Boolean>>{
        return networkRepository.isInternetAvailable()
    }
}
