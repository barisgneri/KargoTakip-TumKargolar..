package com.barisproduction.kargo.data.remote.model

import dev.gitlive.firebase.firestore.DocumentReference
import kotlinx.serialization.Serializable

@Serializable
data class CountryCompaniesDto(
    val companies: List<DocumentReference>
)
