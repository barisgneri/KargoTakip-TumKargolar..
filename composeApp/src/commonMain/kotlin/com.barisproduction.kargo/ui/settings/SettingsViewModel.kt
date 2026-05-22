package com.barisproduction.kargo.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.barisproduction.kargo.delegation.MVI
import com.barisproduction.kargo.delegation.mvi
import com.barisproduction.kargo.Platform
import com.barisproduction.kargo.domain.usecase.GetCountriesUseCase
import com.barisproduction.kargo.domain.usecase.GetLanguagesUseCase
import com.barisproduction.kargo.domain.usecase.GetSelectedCountryUseCase
import com.barisproduction.kargo.domain.usecase.GetSelectedLanguageUseCase
import com.barisproduction.kargo.domain.usecase.GetThemeUseCase
import com.barisproduction.kargo.domain.usecase.SetCountryUseCase
import com.barisproduction.kargo.domain.usecase.SetLanguageUseCase
import com.barisproduction.kargo.domain.usecase.SetThemeUseCase
import com.barisproduction.kargo.ui.settings.SettingsContract.PickerType
import com.barisproduction.kargo.ui.settings.SettingsContract.UiAction
import com.barisproduction.kargo.ui.settings.SettingsContract.UiEffect
import com.barisproduction.kargo.ui.settings.SettingsContract.UiState
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val getCountriesUseCase: GetCountriesUseCase,
    private val getLanguagesUseCase: GetLanguagesUseCase,
    private val getSelectedCountryUseCase: GetSelectedCountryUseCase,
    private val getSelectedLanguageUseCase: GetSelectedLanguageUseCase,
    private val getThemeUseCase: GetThemeUseCase,
    private val setThemeUseCase: SetThemeUseCase,
    private val setCountryUseCase: SetCountryUseCase,
    private val setLanguageUseCase: SetLanguageUseCase,
    private val platform: Platform
) : ViewModel(), MVI<UiState, UiAction, UiEffect> by mvi(UiState()) {

    init {
        getAppConfig()
        observeTheme()
        observeLanguage()
        observeCountry()
        updateUiState { copy(appVersion = "v${platform.versionCode}") }
    }

    private fun observeTheme() {
        getThemeUseCase().onEach { isDark ->
            updateUiState { copy(isDarkMode = isDark ?: false) }
        }.launchIn(viewModelScope)
    }

    private fun observeLanguage() {
        getSelectedLanguageUseCase(uiState.map { it.languages })
            .filterNotNull()
            .onEach { language ->
                if (uiState.value.selectedLanguage != language.name) {
                    updateUiState { copy(selectedLanguage = language.name) }
                }
            }.launchIn(viewModelScope)
    }

    private fun observeCountry() {
        getSelectedCountryUseCase(uiState.map { it.countries })
            .filterNotNull()
            .onEach { country ->
                if (uiState.value.selectedCountry != country.displayName) {
                    updateUiState { copy(selectedCountry = country.displayName) }
                }
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
                is UiAction.OnDarkModeChange -> setThemeUseCase(uiAction.isDark)
                is UiAction.OnCountryClick -> updateUiState { copy(activePicker = PickerType.COUNTRY) }
                is UiAction.OnCountrySelect -> {
                    setCountryUseCase(uiAction.country.code)
                    updateUiState { copy(activePicker = null) }
                }
                is UiAction.OnLanguageClick -> updateUiState { copy(activePicker = PickerType.LANGUAGE) }
                is UiAction.OnLanguageSelect -> {
                    setLanguageUseCase(uiAction.language.code)
                    updateUiState { copy(activePicker = null) }
                }
                is UiAction.OnDismissPicker -> updateUiState { copy(activePicker = null) }
                is UiAction.OnAboutClick -> updateUiState { copy(showAboutDialog = true) }
                is UiAction.OnDismissAbout -> updateUiState { copy(showAboutDialog = false) }
            }
        }
    }
}
