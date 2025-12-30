package com.barisproduction.kargo.domain.usecase

import com.barisproduction.kargo.domain.repository.NetworkRepository

class CheckNetworkUseCase(private val networkRepository: NetworkRepository) {
    suspend operator fun invoke(): Boolean {
        return networkRepository.isInternetAvailable()
    }
}
