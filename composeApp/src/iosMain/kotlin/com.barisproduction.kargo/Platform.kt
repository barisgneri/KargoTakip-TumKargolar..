package com.barisproduction.kargo

import platform.Foundation.NSBundle
import platform.Foundation.NSLocale
import platform.Foundation.NSUserDefaults
import platform.Foundation.languageCode
import platform.UIKit.UIDevice

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
    override val versionCode: Int
        get() = (NSBundle.mainBundle.infoDictionary?.get("CFBundleVersion") as? String)?.toIntOrNull() ?: 0
    override val systemLanguageCode: String
        get() = NSLocale.currentLocale.languageCode
}

actual fun changeLanguage(langCode: String) {
    NSUserDefaults.standardUserDefaults.setObject(listOf(langCode), "AppleLanguages")
    NSUserDefaults.standardUserDefaults.synchronize()
}

actual fun getPlatform(): Platform = IOSPlatform()
