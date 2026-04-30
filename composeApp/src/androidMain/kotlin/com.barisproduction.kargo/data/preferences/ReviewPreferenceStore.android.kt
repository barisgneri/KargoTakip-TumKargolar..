package com.barisproduction.kargo.data.preferences

import android.content.Context

actual class ReviewPreferenceStore(
    context: Context
) {
    private val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    actual fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }

    actual fun putBoolean(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    private companion object {
        const val PREF_NAME = "kargo_review_preferences"
    }
}
