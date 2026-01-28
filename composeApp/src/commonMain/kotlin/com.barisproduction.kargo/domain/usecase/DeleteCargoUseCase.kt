package com.barisproduction.kargo.domain.usecase

import com.barisproduction.kargo.domain.repository.LocalRepository

class DeleteCargoUseCase(private val localRepository: LocalRepository) {
    suspend operator fun invoke(trackNo: String) {
        localRepository.deleteCargo(trackNo)
    }
}