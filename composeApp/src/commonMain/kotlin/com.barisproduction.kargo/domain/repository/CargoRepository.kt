package com.barisproduction.kargo.domain.repository

import com.barisproduction.kargo.common.Resource
import com.barisproduction.kargo.domain.model.Parcels
import kotlinx.coroutines.flow.StateFlow


interface CargoRepository {
    suspend fun getCargoParcelList()
    fun getCargoParcelListState(): StateFlow<Resource<List<Parcels>>>

}