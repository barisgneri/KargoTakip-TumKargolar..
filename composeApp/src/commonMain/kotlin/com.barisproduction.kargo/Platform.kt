package com.barisproduction.kargo

interface Platform {
    val name: String
    val versionCode: Int
}

expect fun getPlatform(): Platform