package com.barisproduction.kargo.data.repository

import com.barisproduction.kargo.common.toFormattedDate
import com.barisproduction.kargo.data.local.CargoDao
import com.barisproduction.kargo.data.local.CargoEntity
import com.barisproduction.kargo.domain.model.CargoModel
import com.barisproduction.kargo.domain.model.ParcelModel
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
            name = cargo.name,
            cargoName = cargo.parcel.parcelName,
            trackingNumber = cargo.trackNo,
            createdAt = Clock.System.now().toEpochMilliseconds()
        )
        cargoDao.insertCargo(entity)
    }

    override fun getAllCargos(): Flow<List<CargoModel>> {
        return cargoDao.getAllCargos().map { entities ->
            entities.map { entity ->
                CargoModel(
                    name = entity.name,
                    parcel = ParcelModel.fromName(entity.cargoName)?: ParcelModel.OTHER,
                    trackNo = entity.trackingNumber,
                    addDate = entity.createdAt.toFormattedDate()
                )
            }
        }
    }

    override suspend fun deleteCargo(trackNo: String) {
        getCargoByTrackingNumber(trackNo)?.let { entity ->
            cargoDao.deleteCargo(entity)
        }
    }

    override suspend fun getCargoByTrackingNumber(trackingNumber: String): CargoEntity? {
        return cargoDao.getCargoByTrackingNumber(trackingNumber)
    }
}
