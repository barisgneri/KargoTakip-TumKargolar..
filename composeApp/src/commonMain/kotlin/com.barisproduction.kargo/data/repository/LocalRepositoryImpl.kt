package com.barisproduction.kargo.data.repository

import com.barisproduction.kargo.common.extensions.toFormattedDate
import com.barisproduction.kargo.data.local.CargoDao
import com.barisproduction.kargo.data.local.CargoEntity
import com.barisproduction.kargo.domain.model.CargoModel
import com.barisproduction.kargo.domain.repository.LocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class LocalRepositoryImpl(
    private val cargoDao: CargoDao
) : LocalRepository {

    @OptIn(ExperimentalTime::class)
    override suspend fun insertCargo(cargo: CargoModel) {
        val entity = CargoEntity(
            parcelName = cargo.parcelName,
            cargoName = cargo.cargoName,
            trackingNumber = cargo.trackNo,
            logo = cargo.logo,
            createdAt = Clock.System.now().toEpochMilliseconds()
        )
        cargoDao.insertCargo(entity)
    }

    override suspend fun updateCargo(cargo: CargoModel) {
        val existingEntity = cargoDao.getCargoByTrackingNumber(cargo.trackNo)
        if (existingEntity != null) {
            val updatedEntity = existingEntity.copy(
                parcelName = cargo.parcelName,
                cargoName = cargo.cargoName,
                logo = cargo.logo,
            )
            cargoDao.updateCargo(updatedEntity)
        }
    }

    override fun getAllCargos(): Flow<List<CargoModel>> {
        return cargoDao.getAllCargos().map { entities ->
            entities.map { entity ->
                CargoModel(
                    parcelName = entity.parcelName,
                    cargoName = entity.cargoName,
                    logo = entity.logo,
                    trackNo = entity.trackingNumber,
                    addDate = entity.createdAt.toFormattedDate()
                )
            }
        }
    }

    override suspend fun deleteCargo(trackNo: String) {
        cargoDao.getCargoByTrackingNumber(trackNo)?.let { entity ->
            cargoDao.deleteCargo(entity)
        }
    }

    override suspend fun getCargoByTrackingNumber(trackingNumber: String): CargoEntity? {
        return cargoDao.getCargoByTrackingNumber(trackingNumber)
    }
}
