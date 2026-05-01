package com.barisproduction.kargo.domain.model

data class ForceUpdateDecision(
    val isRequired: Boolean,
    val storeUrl: String?
)
