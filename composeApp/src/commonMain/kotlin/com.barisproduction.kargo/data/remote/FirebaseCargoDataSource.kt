package com.barisproduction.kargo.data.remote

import com.barisproduction.kargo.data.model.CargoDto
import dev.gitlive.firebase.firestore.FirebaseFirestore

class FirebaseCargoDataSource(private val firestore: FirebaseFirestore) : CargoRemoteDataSource {
    override suspend fun getAllParcels(): List<CargoDto> {
        val response = firestore.collection("cargoCompany").get()
        return response.documents.map { it.data(CargoDto.serializer()) }
    }
}