package com.barisproduction.kargo.data.preferences

import android.content.Context

class AndroidReviewPreferenceStore(
    context: Context
) : ReviewPreferenceStore {
    private val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }

    override fun putBoolean(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    private companion object {
        const val PREF_NAME = "kargo_review_preferences"
    }
}
