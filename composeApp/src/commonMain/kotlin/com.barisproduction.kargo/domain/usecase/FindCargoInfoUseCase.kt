package com.barisproduction.kargo.domain.usecase

import com.barisproduction.kargo.common.AppError
import com.barisproduction.kargo.common.Resource
import com.barisproduction.kargo.common.extensions.toUserMessage
import com.barisproduction.kargo.domain.model.CargoModel
import com.barisproduction.kargo.domain.model.Parcels
import com.barisproduction.kargo.domain.repository.CargoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map

class FindCargoInfoUseCase(private val repository: CargoRepository) {
    operator fun invoke(cargoName: String): Resource<Parcels> {
        val response = repository.getCargoParcelListState().value

        return when (response) {
            is Resource.Success -> {
                val data = response.data?.find { it.parcelName == cargoName }

                if (data != null) {
                    val parcelModel = Parcels(
                        parcelName = data.parcelName,
                        trackingUrl = data.trackingUrl,
                        logo = data.logo,
                        js = data.js
                    )
                    Resource.Success(parcelModel)
                } else {
                    Resource.Error(errorType = AppError.General("Kargo bulunamadı."))
                }
            }

            is Resource.Error -> {
                Resource.Error(errorType = AppError.General(response.errorType.toUserMessage()))
            }

            is Resource.Loading -> {
                Resource.Loading()
            }
        }

    }
}