package com.barisproduction.kargo.domain.usecase

import com.barisproduction.kargo.domain.model.CountryModel
import com.barisproduction.kargo.domain.repository.AppConfigRepository

class GetCountriesUseCase(private val repository: AppConfigRepository) {
    suspend operator fun invoke(): List<CountryModel> = repository.getCountries()
}
