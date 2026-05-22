package com.barisproduction.kargo.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.barisproduction.kargo.delegation.MVI
import com.barisproduction.kargo.delegation.mvi
import com.barisproduction.kargo.domain.repository.AppConfigRepository
import com.barisproduction.kargo.domain.usecase.GetCountriesUseCase
import com.barisproduction.kargo.domain.usecase.GetLanguagesUseCase
import com.barisproduction.kargo.getPlatform
import com.barisproduction.kargo.ui.settings.SettingsContract.PickerType
import com.barisproduction.kargo.ui.settings.SettingsContract.UiAction
import com.barisproduction.kargo.ui.settings.SettingsContract.UiEffect
import com.barisproduction.kargo.ui.settings.SettingsContract.UiState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val getCountriesUseCase: GetCountriesUseCase,
    private val getLanguagesUseCase: GetLanguagesUseCase,
    private val appConfigRepository: AppConfigRepository
) : ViewModel(), MVI<UiState, UiAction, UiEffect> by mvi(UiState()) {

    init {
        getAppConfig()
        observeTheme()
        observeLanguage()
    }

    private fun observeTheme() {
        appConfigRepository.isDarkMode.onEach { isDark ->
            updateUiState { copy(isDarkMode = isDark ?: false) }
        }.launchIn(viewModelScope)
    }

    private fun observeLanguage() {
        appConfigRepository.currentLanguage.onEach { langCode ->
            val languages = getLanguagesUseCase()
            val effectiveCode = langCode ?: getPlatform().systemLanguageCode
            val selectedLangName = languages.find { it.code == effectiveCode }?.name ?: languages.first().name
            updateUiState { copy(selectedLanguage = selectedLangName) }
        }.launchIn(viewModelScope)
    }

    private fun getAppConfig() = viewModelScope.launch {
        val countries = getCountriesUseCase()
        val languages = getLanguagesUseCase()
        updateUiState {
            copy(
                countries = countries,
                languages = languages
            )
        }
    }

    override fun onAction(uiAction: UiAction) {
        viewModelScope.launch {
            when (uiAction) {
                is UiAction.OnBackClick -> emitUiEffect(UiEffect.NavigateBack)
                is UiAction.OnDarkModeChange -> appConfigRepository.setTheme(uiAction.isDark)
                is UiAction.OnCountryClick -> updateUiState { copy(activePicker = PickerType.COUNTRY) }
                is UiAction.OnCountrySelect -> updateUiState {
                    copy(
                        selectedCountry = uiAction.country.displayName,
                        activePicker = null
                    )
                }
                is UiAction.OnLanguageClick -> updateUiState { copy(activePicker = PickerType.LANGUAGE) }
                is UiAction.OnLanguageSelect -> {
                    appConfigRepository.setLanguage(uiAction.language.code)
                    updateUiState { copy(activePicker = null) }
                }
                is UiAction.OnDismissPicker -> updateUiState { copy(activePicker = null) }
            }
        }
    }
}
