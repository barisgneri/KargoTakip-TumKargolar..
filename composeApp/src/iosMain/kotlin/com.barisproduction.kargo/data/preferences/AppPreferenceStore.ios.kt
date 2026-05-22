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

    private companion object {
        const val KEY_THEME = "app_theme_dark"
        const val KEY_LANGUAGE = "app_language"
    }
}
