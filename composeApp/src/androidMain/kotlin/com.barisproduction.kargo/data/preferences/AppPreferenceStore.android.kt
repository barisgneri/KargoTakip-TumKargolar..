package com.barisproduction.kargo.data.preferences

import android.content.Context

class AndroidAppPreferenceStore(
    context: Context
) : AppPreferenceStore {
    private val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    override fun getTheme(): Boolean? {
        return if (sharedPreferences.contains(KEY_THEME)) {
            sharedPreferences.getBoolean(KEY_THEME, false)
        } else {
            null
        }
    }

    override fun setTheme(isDark: Boolean?) {
        if (isDark == null) {
            sharedPreferences.edit().remove(KEY_THEME).apply()
        } else {
            sharedPreferences.edit().putBoolean(KEY_THEME, isDark).apply()
        }
    }

    override fun getLanguage(): String? {
        return sharedPreferences.getString(KEY_LANGUAGE, null)
    }

    override fun setLanguage(langCode: String?) {
        if (langCode == null) {
            sharedPreferences.edit().remove(KEY_LANGUAGE).apply()
        } else {
            sharedPreferences.edit().putString(KEY_LANGUAGE, langCode).apply()
        }
    }

    override fun getString(key: String, defaultValue: String): String {
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue
    }

    override fun putString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    private companion object {
        const val PREF_NAME = "kargo_app_preferences"
        const val KEY_THEME = "app_theme_dark"
        const val KEY_LANGUAGE = "app_language"
    }
}
