package com.barisproduction.kargo.domain.usecase

import com.barisproduction.kargo.data.local.CargoEntity
import com.barisproduction.kargo.domain.model.CargoModel
import com.barisproduction.kargo.domain.repository.LocalRepository
import kotlinx.coroutines.flow.Flow

class GetCargosUseCase(
    private val localRepository: LocalRepository
) {
    operator fun invoke(): Flow<List<CargoModel>> {
        return localRepository.getAllCargos()
    }
}
