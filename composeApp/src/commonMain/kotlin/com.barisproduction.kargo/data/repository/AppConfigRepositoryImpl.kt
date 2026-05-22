package com.barisproduction.kargo.data.repository

import com.barisproduction.kargo.data.preferences.AppPreferenceStore
import com.barisproduction.kargo.data.remote.AppConfigApiService
import com.barisproduction.kargo.domain.model.CountryModel
import com.barisproduction.kargo.domain.model.LanguageModel
import com.barisproduction.kargo.domain.repository.AppConfigRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AppConfigRepositoryImpl(
    private val apiService: AppConfigApiService,
    private val preferenceStore: AppPreferenceStore
) : AppConfigRepository {

    // --- Veri Listeleri ---
    override suspend fun getCountries(): List<CountryModel> {
        return try {
            apiService.getCountries().map { 
                CountryModel(code = it.code, name = it.name, flagEmoji = it.flagEmoji)
            }
        } catch (e: Exception) {
            println("AppConfigRepositoryImpl: getCountries error: ${e.message}")
            listOf(
                CountryModel("tr", "Türkiye", "🇹🇷"),
                CountryModel("us", "United States", "🇺🇸"),
                CountryModel("de", "Germany", "🇩🇪")
            )
        }
    }

    override fun getLanguages(): List<LanguageModel> {
        return listOf(
            LanguageModel("Türkçe", "tr"),
            LanguageModel("English", "en")
        )
    }

    // --- Dil Yönetimi ---
    private val _currentLanguage = MutableStateFlow(preferenceStore.getLanguage())
    override val currentLanguage: StateFlow<String?> = _currentLanguage.asStateFlow()

    override fun setLanguage(langCode: String?) {
        preferenceStore.setLanguage(langCode)
        _currentLanguage.value = langCode
    }

    // --- Tema Yönetimi ---
    private val _isDarkMode = MutableStateFlow(preferenceStore.getTheme())
    override val isDarkMode: StateFlow<Boolean?> = _isDarkMode.asStateFlow()

    override fun setTheme(isDark: Boolean?) {
        preferenceStore.setTheme(isDark)
        _isDarkMode.value = isDark
    }
}
