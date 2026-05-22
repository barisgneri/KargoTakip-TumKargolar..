package com.barisproduction.kargo.domain.repository

import com.barisproduction.kargo.domain.model.CountryModel
import com.barisproduction.kargo.domain.model.LanguageModel
import kotlinx.coroutines.flow.StateFlow

interface AppConfigRepository {
    // Veri Listeleri
    suspend fun getCountries(): List<CountryModel>
    fun getLanguages(): List<LanguageModel>

    // Dil Yönetimi
    val currentLanguage: StateFlow<String?>
    fun setLanguage(langCode: String?)

    // Tema Yönetimi
    val isDarkMode: StateFlow<Boolean?>
    fun setTheme(isDark: Boolean?)
}
