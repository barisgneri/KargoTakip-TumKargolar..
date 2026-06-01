package com.barisproduction.kargo

interface Platform {
    val name: String
    val versionCode: Int
    val systemLanguageCode: String
    val systemCountryCode: String
}

expect fun changeLanguage(langCode: String)

expect fun getPlatform(): Platform
