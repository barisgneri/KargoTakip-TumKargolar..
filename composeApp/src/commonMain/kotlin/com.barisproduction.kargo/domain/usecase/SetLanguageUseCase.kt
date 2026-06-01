package com.barisproduction.kargo.domain.usecase

import com.barisproduction.kargo.domain.repository.AppConfigRepository

class SetLanguageUseCase(private val repository: AppConfigRepository) {
    operator fun invoke(langCode: String?) = repository.setLanguage(langCode)
}
