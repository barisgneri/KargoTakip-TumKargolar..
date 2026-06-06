package com.barisproduction.kargo.domain.usecase

import com.barisproduction.kargo.domain.model.CargoModel
import com.barisproduction.kargo.domain.repository.AppConfigRepository
import com.barisproduction.kargo.domain.repository.LocalRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest

class GetCargosUseCase(
    private val localRepository: LocalRepository,
    private val appConfigRepository: AppConfigRepository
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<List<CargoModel>> {
        return appConfigRepository.currentCountry.flatMapLatest { countryCode ->
            val effectiveCode = countryCode ?: appConfigRepository.systemCountryCode
            localRepository.getCargosByCountry(effectiveCode)
        }
    }
}
