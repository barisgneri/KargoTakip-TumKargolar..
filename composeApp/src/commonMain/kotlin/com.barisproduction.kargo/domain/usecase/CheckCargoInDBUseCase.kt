package com.barisproduction.kargo.domain.usecase

import com.barisproduction.kargo.domain.repository.LocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CheckCargoInDBUseCase(private val localRepository: LocalRepository) {
    operator fun invoke(trackingNumber: String): Flow<Boolean> {
        return localRepository.observeCargoByTrackingNumber(trackingNumber).map { it != null }
    }
}