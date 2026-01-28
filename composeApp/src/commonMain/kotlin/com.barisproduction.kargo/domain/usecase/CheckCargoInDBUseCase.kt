package com.barisproduction.kargo.domain.usecase

import com.barisproduction.kargo.domain.repository.LocalRepository

class CheckCargoInDBUseCase(private val localRepository: LocalRepository) {
    suspend operator fun invoke(trackingNumber: String): Boolean {
        return localRepository.getCargoByTrackingNumber(trackingNumber) != null
    }
}