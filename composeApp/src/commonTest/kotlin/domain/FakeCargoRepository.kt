package domain

import com.barisproduction.kargo.common.AppError
import com.barisproduction.kargo.common.Resource
import com.barisproduction.kargo.domain.model.AppUpdateConfig
import com.barisproduction.kargo.domain.model.Parcels
import com.barisproduction.kargo.domain.repository.CargoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeCargoRepository : CargoRepository {

    var dummyParcelList: List<Parcels> = emptyList()
    var dummyAppUpdateConfig: Resource<AppUpdateConfig> = Resource.Loading()
    var shouldReturnError = false

    private val _parcelListState = MutableStateFlow<Resource<List<Parcels>>>(Resource.Loading())

    override fun getCargoParcelListState(): StateFlow<Resource<List<Parcels>>> = _parcelListState.asStateFlow()

    override suspend fun getCargoParcelList() {
        if (shouldReturnError) {
            _parcelListState.emit(Resource.Error(AppError.NotFound))
        } else {
            _parcelListState.emit(Resource.Success(dummyParcelList))
        }
    }

    override suspend fun getAppUpdateConfig(): Resource<AppUpdateConfig> {
        return dummyAppUpdateConfig
    }
}