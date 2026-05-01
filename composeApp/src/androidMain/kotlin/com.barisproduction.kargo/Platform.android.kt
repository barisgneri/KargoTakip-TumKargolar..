package com.barisproduction.kargo

import android.content.Context
import android.os.Build

class AndroidPlatform(private val context: Context) : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
    override val versionCode: Int
        get() {
            return try {
                val packageInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    context.packageManager.getPackageInfo(context.packageName, android.content.pm.PackageManager.PackageInfoFlags.of(0))
                } else {
                    context.packageManager.getPackageInfo(context.packageName, 0)
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    packageInfo.longVersionCode.toInt()
                } else {
                    packageInfo.versionCode
                }
            } catch (e: Exception) {
                0
            }
        }
}