package com.barisproduction.kargo.data.preferences

import platform.Foundation.NSUserDefaults

actual class ReviewPreferenceStore {
    private val userDefaults = NSUserDefaults.standardUserDefaults

    actual fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return if (userDefaults.objectForKey(key) == null) {
            defaultValue
        } else {
            userDefaults.boolForKey(key)
        }
    }

    actual fun putBoolean(key: String, value: Boolean) {
        userDefaults.setBool(value, key)
    }
}
