package com.barisproduction.kargo.data.preferences

interface ReviewPreferenceStore {
    fun getBoolean(key: String, defaultValue: Boolean): Boolean
    fun putBoolean(key: String, value: Boolean)
}
