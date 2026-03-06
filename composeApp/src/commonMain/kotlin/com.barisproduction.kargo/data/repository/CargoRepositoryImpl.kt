package com.barisproduction.kargo.data.repository

import com.barisproduction.kargo.common.AppError
import com.barisproduction.kargo.common.Resource
import com.barisproduction.kargo.data.model.toDomain
import com.barisproduction.kargo.data.remote.CargoRemoteDataSource
import com.barisproduction.kargo.data.util.ErrorParser
import com.barisproduction.kargo.domain.model.Parcels
import com.barisproduction.kargo.domain.repository.CargoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CargoRepositoryImpl(private val remoteDataSource: CargoRemoteDataSource) : CargoRepository {

    private val _parcelListState = MutableStateFlow<Resource<List<Parcels>>>(Resource.Loading())
    override fun getCargoParcelListState(): StateFlow<Resource<List<Parcels>>> = _parcelListState.asStateFlow()

    override suspend fun getCargoParcelList() {
        if (_parcelListState.value is Resource.Success) return // Opsiyonel cache

        _parcelListState.emit(Resource.Loading())
        try {
            val dtoList = remoteDataSource.getAllParcels()
            val domainList = dtoList.map { it.toDomain() }

            _parcelListState.emit(Resource.Success(domainList))
        } catch (e: Exception) {
            _parcelListState.emit(ErrorParser.parse(e))
        }
    }
}
