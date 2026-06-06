package com.barisproduction.kargo.ui.settings

import com.barisproduction.kargo.domain.model.CountryModel
import com.barisproduction.kargo.domain.model.LanguageModel
import kargotakiptumkargolar.composeapp.generated.resources.Res
import kargotakiptumkargolar.composeapp.generated.resources.select_country
import kargotakiptumkargolar.composeapp.generated.resources.select_language
import org.jetbrains.compose.resources.StringResource

object SettingsContract {
    data class UiState(
        val isLoading: Boolean = false,
        val isDarkMode: Boolean? = null,
        val selectedCountry: String = "",
        val selectedLanguage: String = "",
        val activePicker: PickerType? = null,
        val countries: List<CountryModel> = emptyList(),
        val languages: List<LanguageModel> = emptyList(),
        val showAboutDialog: Boolean = false,
        val showCountryInfoDialog: Boolean = false,
        val appVersion: String = ""
    )

    enum class PickerType(val titleRes: StringResource) {
        COUNTRY(Res.string.select_country),
        LANGUAGE(Res.string.select_language)
    }

    sealed interface UiAction {
        data object OnBackClick : UiAction
        data class OnDarkModeChange(val isDark: Boolean) : UiAction
        data object OnCountryClick : UiAction
        data class OnCountrySelect(val country: CountryModel) : UiAction
        data object OnLanguageClick : UiAction
        data class OnLanguageSelect(val language: LanguageModel) : UiAction
        data object OnDismissPicker : UiAction
        data object OnAboutClick : UiAction
        data object OnDismissAbout : UiAction
        data object OnCountryInfoConfirm : UiAction
        data object OnDismissCountryInfo : UiAction
    }

    sealed interface UiEffect {
        data object NavigateBack : UiEffect
    }
}
