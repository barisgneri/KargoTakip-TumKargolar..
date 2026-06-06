package com.barisproduction.kargo.domain.usecase

import com.barisproduction.kargo.domain.model.LanguageModel
import com.barisproduction.kargo.domain.repository.AppConfigRepository

class GetLanguagesUseCase(private val repository: AppConfigRepository) {
    operator fun invoke(): List<LanguageModel> = repository.getLanguages()
}
