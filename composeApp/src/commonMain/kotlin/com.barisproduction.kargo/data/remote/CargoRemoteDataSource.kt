package com.barisproduction.kargo.data.remote

import com.barisproduction.kargo.data.remote.model.CargoDto

interface CargoRemoteDataSource{
    suspend fun getAllParcels(): List<CargoDto>
}