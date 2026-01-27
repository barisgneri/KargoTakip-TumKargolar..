package com.barisproduction.kargo.domain.usecase

import com.barisproduction.kargo.domain.model.CargoModel
import com.barisproduction.kargo.domain.repository.LocalRepository

class InsertCargoUseCase(
    private val localRepository: LocalRepository
) {
    suspend operator fun invoke(cargo: CargoModel) {
        localRepository.insertCargo(cargo)
    }
}
