package com.barisproduction.kargo.domain.usecase

import com.barisproduction.kargo.domain.repository.AppConfigRepository

class SetCountryUseCase(private val repository: AppConfigRepository) {
    operator fun invoke(countryCode: String?) = repository.setCountry(countryCode)
}
