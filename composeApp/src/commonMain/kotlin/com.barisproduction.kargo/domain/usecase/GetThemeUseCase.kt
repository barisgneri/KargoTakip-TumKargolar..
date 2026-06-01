package com.barisproduction.kargo.domain.usecase

import com.barisproduction.kargo.domain.repository.AppConfigRepository
import kotlinx.coroutines.flow.StateFlow

class GetThemeUseCase(private val repository: AppConfigRepository) {
    operator fun invoke(): StateFlow<Boolean?> = repository.isDarkMode
}
