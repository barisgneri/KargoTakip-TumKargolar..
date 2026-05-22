package com.barisproduction.kargo.data.preferences

import platform.Foundation.NSUserDefaults

class IosAppPreferenceStore : AppPreferenceStore {
    private val userDefaults = NSUserDefaults.standardUserDefaults

    override fun getTheme(): Boolean? {
        return if (userDefaults.objectForKey(KEY_THEME) == null) {
            null
        } else {
            userDefaults.boolForKey(KEY_THEME)
        }
    }

    override fun setTheme(isDark: Boolean?) {
        if (isDark == null) {
            userDefaults.removeObjectForKey(KEY_THEME)
        } else {
            userDefaults.setBool(isDark, KEY_THEME)
        }
    }

    override fun getLanguage(): String? {
        return userDefaults.stringForKey(KEY_LANGUAGE)
    }

    override fun setLanguage(langCode: String?) {
        if (langCode == null) {
            userDefaults.removeObjectForKey(KEY_LANGUAGE)
        } else {
            userDefaults.setObject(langCode, KEY_LANGUAGE)
        }
    }

    override fun getString(key: String, defaultValue: String): String {
        return userDefaults.stringForKey(key) ?: defaultValue
    }

    override fun putString(key: String, value: String) {
        userDefaults.setObject(value, key)
    }

    override fun getSelectedCountry(): String? {
        return userDefaults.stringForKey(KEY_SELECTED_COUNTRY)
    }

    override fun setSelectedCountry(countryCode: String?) {
        if (countryCode == null) {
            userDefaults.removeObjectForKey(KEY_SELECTED_COUNTRY)
        } else {
            userDefaults.setObject(countryCode, KEY_SELECTED_COUNTRY)
        }
    }

    private companion object {
        const val KEY_THEME = "app_theme_dark"
        const val KEY_LANGUAGE = "app_language"
        const val KEY_SELECTED_COUNTRY = "selected_country"
    }
}
