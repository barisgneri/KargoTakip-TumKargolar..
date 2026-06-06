package com.barisproduction.kargo.domain.model

data class CountryModel(
    val code: String,
    val name: String,
    val flagEmoji: String
) {
    val displayName: String get() = "$flagEmoji $name"
}
