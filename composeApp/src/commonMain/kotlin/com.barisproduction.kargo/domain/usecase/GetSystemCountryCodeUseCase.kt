package com.barisproduction.kargo.domain.usecase

import com.barisproduction.kargo.domain.repository.AppConfigRepository

class GetSystemCountryCodeUseCase(private val repository: AppConfigRepository) {
    operator fun invoke(): String = repository.systemCountryCode
}
