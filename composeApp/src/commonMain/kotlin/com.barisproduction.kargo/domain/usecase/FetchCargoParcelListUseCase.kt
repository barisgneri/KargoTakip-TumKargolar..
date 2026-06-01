package com.barisproduction.kargo.domain.usecase

import com.barisproduction.kargo.domain.repository.CargoRepository

class FetchCargoParcelListUseCase(private val repository: CargoRepository) {
    suspend operator fun invoke(countryCode: String? = null) {
        repository.getCargoParcelList(countryCode)
    }
}
