package com.barisproduction.kargo.domain.usecase

import com.barisproduction.kargo.domain.model.LanguageModel
import com.barisproduction.kargo.domain.repository.AppConfigRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetSelectedLanguageUseCase(private val repository: AppConfigRepository) {
    operator fun invoke(languagesFlow: Flow<List<LanguageModel>>): Flow<LanguageModel?> {
        return combine(
            repository.currentLanguage,
            languagesFlow
        ) { selectedCode, languages ->
            if (languages.isEmpty()) return@combine null

            val effectiveCode = selectedCode ?: repository.systemLanguageCode
            languages.find { it.code == effectiveCode } ?: languages.firstOrNull()
        }
    }
}
