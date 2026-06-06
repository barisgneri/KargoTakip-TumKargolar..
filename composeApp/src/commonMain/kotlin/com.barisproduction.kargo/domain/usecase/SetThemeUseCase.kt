package com.barisproduction.kargo.domain.usecase

import com.barisproduction.kargo.domain.repository.AppConfigRepository

class SetThemeUseCase(private val repository: AppConfigRepository) {
    operator fun invoke(isDark: Boolean?) = repository.setTheme(isDark)
}
