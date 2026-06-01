package com.barisproduction.kargo.data.remote

import com.barisproduction.kargo.data.remote.model.CargoDto
import com.barisproduction.kargo.data.remote.model.AppUpdateConfigDto
import com.barisproduction.kargo.data.remote.model.CountryCompaniesDto
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.Source
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class FirebaseCargoDataSource(private val firestore: FirebaseFirestore) : CargoRemoteDataSource {
    override suspend fun getAllParcels(): List<CargoDto> {
        val response = firestore.collection("cargoCompany").get(Source.SERVER)
        return response.documents.map { it.data(CargoDto.serializer()) }
    }

    override suspend fun getCompaniesByCountry(countryCode: String): List<CargoDto> = coroutineScope {
        val countryDoc = firestore.collection("cargo_companies").document(countryCode.lowercase()).get(Source.SERVER)
        val companyRefs = countryDoc.data(CountryCompaniesDto.serializer()).companies

        companyRefs.map { ref ->
            async {
                ref.get(Source.SERVER).data(CargoDto.serializer())
            }
        }.awaitAll()
    }

    override suspend fun getAppUpdateConfig(): AppUpdateConfigDto {
        val response = firestore.collection("appConfig").document("update").get(Source.SERVER)
        return response.data(AppUpdateConfigDto.serializer())
    }

}
