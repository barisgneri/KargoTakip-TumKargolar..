package com.barisproduction.kargo.domain.usecase

import com.barisproduction.kargo.domain.model.CargoModel
import com.barisproduction.kargo.domain.repository.AppConfigRepository
import com.barisproduction.kargo.domain.repository.LocalRepository

class InsertCargoUseCase(
    private val localRepository: LocalRepository,
    private val appConfigRepository: AppConfigRepository
) {
    suspend operator fun invoke(cargo: CargoModel) {
        val currentCountry = appConfigRepository.currentCountry.value ?: appConfigRepository.systemCountryCode
        val cargoWithCountry = cargo.copy(companyCountryCode = currentCountry)
        localRepository.insertCargo(cargoWithCountry)
    }
}
