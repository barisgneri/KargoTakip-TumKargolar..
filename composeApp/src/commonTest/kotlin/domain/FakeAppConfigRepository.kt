package domain

import com.barisproduction.kargo.domain.model.CountryModel
import com.barisproduction.kargo.domain.model.LanguageModel
import com.barisproduction.kargo.domain.repository.AppConfigRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeAppConfigRepository : AppConfigRepository {
    override suspend fun getCountries(): List<CountryModel> = emptyList()
    override fun getLanguages(): List<LanguageModel> = emptyList()

    private val _currentLanguage = MutableStateFlow<String?>("en")
    override val currentLanguage: StateFlow<String?> = _currentLanguage.asStateFlow()
    override fun setLanguage(langCode: String?) { _currentLanguage.value = langCode }

    private val _isDarkMode = MutableStateFlow<Boolean?>(false)
    override val isDarkMode: StateFlow<Boolean?> = _isDarkMode.asStateFlow()
    override fun setTheme(isDark: Boolean?) { _isDarkMode.value = isDark }

    private val _currentCountry = MutableStateFlow<String?>("us")
    override val currentCountry: StateFlow<String?> = _currentCountry.asStateFlow()
    override fun setCountry(countryCode: String?) { _currentCountry.value = countryCode }

    override val systemCountryCode: String = "us"
    override val systemLanguageCode: String = "en"
}
