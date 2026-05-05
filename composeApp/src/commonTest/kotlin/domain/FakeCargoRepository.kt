package domain

import com.barisproduction.kargo.common.Resource
import com.barisproduction.kargo.domain.model.AppUpdateConfig
import com.barisproduction.kargo.domain.model.Parcels
import com.barisproduction.kargo.domain.repository.CargoRepository
import kotlinx.coroutines.flow.StateFlow

class FakeCargoRepository : CargoRepository {
    override suspend fun getCargoParcelList() {
        TODO("Not yet implemented")
    }

    override suspend fun getAppUpdateConfig(): Resource<AppUpdateConfig> {
        TODO("Not yet implemented")
    }

    override fun getCargoParcelListState(): StateFlow<Resource<List<Parcels>>> {
        TODO("Not yet implemented")
    }
}