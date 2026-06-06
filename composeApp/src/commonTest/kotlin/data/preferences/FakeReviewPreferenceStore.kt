package data.preferences

import com.barisproduction.kargo.data.preferences.ReviewPreferenceStore

class FakeReviewPreferenceStore : ReviewPreferenceStore {
    private val preferences = mutableMapOf<String, Boolean>()

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return preferences[key] ?: defaultValue
    }

    override fun putBoolean(key: String, value: Boolean) {
        preferences[key] = value
    }
}
