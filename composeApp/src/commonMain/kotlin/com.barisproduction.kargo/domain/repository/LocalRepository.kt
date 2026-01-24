package com.barisproduction.kargo.domain.repository

import com.barisproduction.kargo.data.local.CargoEntity
import com.barisproduction.kargo.domain.model.CargoModel
import kotlinx.coroutines.flow.Flow

interface LocalRepository {
    suspend fun insertCargo(cargo: CargoModel)
    fun getAllCargos(): Flow<List<CargoModel>>
    suspend fun deleteCargo(cargo: CargoEntity)
    suspend fun getCargoByTrackingNumber(trackingNumber: String): CargoEntity?
}
