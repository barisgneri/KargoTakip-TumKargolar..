package com.barisproduction.kargo

import platform.Foundation.NSBundle
import platform.UIKit.UIDevice
class IOSPlatform : Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
    override val versionCode: Int
        get() {
            return NSBundle.mainBundle.objectForInfoDictionaryKey("CFBundleVersion")?.toString()?.toIntOrNull() ?: 0
        }
}
actual fun getPlatform(): Platform = IOSPlatform()