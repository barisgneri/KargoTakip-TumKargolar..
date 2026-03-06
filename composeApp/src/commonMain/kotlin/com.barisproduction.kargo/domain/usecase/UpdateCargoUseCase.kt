package com.barisproduction.kargo.domain.usecase

import com.barisproduction.kargo.domain.model.CargoModel
import com.barisproduction.kargo.domain.repository.LocalRepository

class UpdateCargoUseCase(private val repository: LocalRepository) {
    suspend operator fun invoke(cargo: CargoModel) {
        repository.updateCargo(cargo)
    }
}
