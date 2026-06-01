package com.barisproduction.kargo.data.repository

import com.barisproduction.kargo.common.Resource
import com.barisproduction.kargo.data.remote.model.toDomain
import com.barisproduction.kargo.data.remote.CargoRemoteDataSource
import com.barisproduction.kargo.data.util.ErrorParser
import com.barisproduction.kargo.domain.model.AppUpdateConfig
import com.barisproduction.kargo.domain.model.Parcels
import com.barisproduction.kargo.domain.repository.AppConfigRepository
import com.barisproduction.kargo.domain.repository.CargoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CargoRepositoryImpl(
    private val remoteDataSource: CargoRemoteDataSource,
    private val configRepository: AppConfigRepository
) : CargoRepository {

    private val _parcelListState = MutableStateFlow<Resource<List<Parcels>>>(Resource.Loading())
    override fun getCargoParcelListState(): StateFlow<Resource<List<Parcels>>> = _parcelListState.asStateFlow()

    private var lastCountryCode: String? = null

    override suspend fun getCargoParcelList(countryCode: String?) {
        val effectiveCountryCode = countryCode ?: configRepository.currentCountry.value ?: "tr"
        
        if (_parcelListState.value is Resource.Success && lastCountryCode == effectiveCountryCode) return

        _parcelListState.emit(Resource.Loading())
        try {
            val dtoList = remoteDataSource.getCompaniesByCountry(effectiveCountryCode)
            val domainList = dtoList.map { it.toDomain() }

            lastCountryCode = effectiveCountryCode
            _parcelListState.emit(Resource.Success(domainList))
        } catch (e: Exception) {
            _parcelListState.emit(ErrorParser.parse(e))
        }
    }

    override suspend fun getAppUpdateConfig(): Resource<AppUpdateConfig> {
        return try {
            val dto = remoteDataSource.getAppUpdateConfig()
            Resource.Success(dto.toDomain())
        } catch (e: Exception) {
            ErrorParser.parse(e)
        }
    }

}
