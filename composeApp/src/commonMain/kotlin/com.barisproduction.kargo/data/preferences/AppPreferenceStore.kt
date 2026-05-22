package com.barisproduction.kargo.data.preferences

interface AppPreferenceStore {
    fun getTheme(): Boolean? // null: System, true: Dark, false: Light
    fun setTheme(isDark: Boolean?)
    
    fun getLanguage(): String? // null: System
    fun setLanguage(langCode: String?)

    fun getString(key: String, defaultValue: String): String
    fun putString(key: String, value: String)
}
