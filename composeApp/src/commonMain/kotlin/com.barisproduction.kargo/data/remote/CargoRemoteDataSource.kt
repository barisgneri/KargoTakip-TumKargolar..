package com.barisproduction.kargo.data.remote

import com.barisproduction.kargo.data.remote.model.CargoDto
import com.barisproduction.kargo.data.remote.model.AppUpdateConfigDto

interface CargoRemoteDataSource{
    suspend fun getAllParcels(): List<CargoDto>
    suspend fun getAppUpdateConfig(): AppUpdateConfigDto
}