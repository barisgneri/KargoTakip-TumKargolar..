package com.barisproduction.kargo.domain.usecase

import com.barisproduction.kargo.common.Resource
import com.barisproduction.kargo.domain.model.Parcels
import com.barisproduction.kargo.domain.repository.CargoRepository
import kotlinx.coroutines.flow.Flow

class GetCargoParcelListUseCase(private val repository: CargoRepository) {
    operator fun invoke(): Flow<Resource<List<Parcels>>> {
        return repository.getCargoParcelListState()
    }
}
