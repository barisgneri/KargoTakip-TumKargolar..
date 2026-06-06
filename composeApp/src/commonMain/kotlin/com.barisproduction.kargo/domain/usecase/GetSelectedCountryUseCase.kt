package com.barisproduction.kargo.domain.usecase

import com.barisproduction.kargo.domain.model.CountryModel
import com.barisproduction.kargo.domain.repository.AppConfigRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetSelectedCountryUseCase(private val repository: AppConfigRepository) {
    operator fun invoke(countriesFlow: Flow<List<CountryModel>>): Flow<CountryModel?> {
        return combine(
            repository.currentCountry,
            countriesFlow
        ) { selectedCode, countries ->
            if (countries.isEmpty()) return@combine null

            val effectiveCode = selectedCode ?: repository.systemCountryCode
            countries.find { it.code.lowercase() == effectiveCode.lowercase() }
                ?: countries.firstOrNull()
        }
    }
}
