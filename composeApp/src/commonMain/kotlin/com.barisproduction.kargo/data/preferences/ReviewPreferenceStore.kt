package com.barisproduction.kargo.data.preferences

expect class ReviewPreferenceStore {
    fun getBoolean(key: String, defaultValue: Boolean): Boolean
    fun putBoolean(key: String, value: Boolean)
}
