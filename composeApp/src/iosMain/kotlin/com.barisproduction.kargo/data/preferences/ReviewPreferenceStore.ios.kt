package com.barisproduction.kargo.data.preferences

import platform.Foundation.NSUserDefaults

class IosReviewPreferenceStore : ReviewPreferenceStore {
    private val userDefaults = NSUserDefaults.standardUserDefaults

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return if (userDefaults.objectForKey(key) == null) {
            defaultValue
        } else {
            userDefaults.boolForKey(key)
        }
    }

    override fun putBoolean(key: String, value: Boolean) {
        userDefaults.setBool(value, key)
    }
}
