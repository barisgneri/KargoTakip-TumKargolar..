package com.barisproduction.kargo.data.remote

import com.barisproduction.kargo.data.remote.model.CargoDto
import com.barisproduction.kargo.data.remote.model.AppUpdateConfigDto

import dev.gitlive.firebase.firestore.FirebaseFirestore

class FirebaseCargoDataSource(private val firestore: FirebaseFirestore) : CargoRemoteDataSource {
    override suspend fun getAllParcels(): List<CargoDto> {
        val response = firestore.collection("cargoCompany").get()
        return response.documents.map { it.data(CargoDto.serializer()) }
    }

    override suspend fun getAppUpdateConfig(): AppUpdateConfigDto {
        val response = firestore.collection("appConfig").document("update").get()
        return response.data(AppUpdateConfigDto.serializer())
    }

}