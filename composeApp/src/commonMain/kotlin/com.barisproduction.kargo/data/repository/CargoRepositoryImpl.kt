package com.barisproduction.kargo.data.repository

import com.barisproduction.kargo.common.Resource
import com.barisproduction.kargo.data.model.toDomain
import com.barisproduction.kargo.data.remote.CargoRemoteDataSource
import com.barisproduction.kargo.domain.model.Parcels
import com.barisproduction.kargo.domain.repository.CargoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CargoRepositoryImpl(private val remoteDataSource: CargoRemoteDataSource) : CargoRepository {

    private val _parcelListState = MutableStateFlow<Resource<List<Parcels>>>(Resource.Loading())
    private val parcelListState: StateFlow<Resource<List<Parcels>>> = _parcelListState.asStateFlow()

    override suspend fun getCargoParcelList() {
        _parcelListState.emit(Resource.Loading())
        try {
            val dtoList = remoteDataSource.getAllParcels()
            val domainList = dtoList.map {
               it.toDomain()
            }

            _parcelListState.emit(Resource.Success(domainList))
        } catch (e: Exception) {
            _parcelListState.emit(Resource.Error(e.message ?: "Unknown Error"))
        }
    }

    override fun getCargoParcelListState(): StateFlow<Resource<List<Parcels>>> {
        return parcelListState
    }


}
