package domain

import com.barisproduction.kargo.data.local.CargoEntity
import com.barisproduction.kargo.domain.model.CargoModel
import com.barisproduction.kargo.domain.repository.LocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class FakeLocalRepository : LocalRepository {
    private val cargos = MutableStateFlow<List<CargoModel>>(emptyList())
    private val entitiesFlow = MutableStateFlow<Map<String, CargoEntity>>(emptyMap())

    override suspend fun insertCargo(cargo: CargoModel) {
        val current = cargos.value.toMutableList()
        current.add(cargo)
        cargos.value = current
    }

    override suspend fun updateCargo(cargo: CargoModel) {
        val current = cargos.value.toMutableList()
        val index = current.indexOfFirst { it.trackNo == cargo.trackNo }
        if (index != -1) {
            current[index] = cargo
            cargos.value = current
        }
    }

    override fun getAllCargos(): Flow<List<CargoModel>> {
        return cargos
    }

    override fun getCargosByCountry(countryCode: String): Flow<List<CargoModel>> {
        return cargos.map { list ->
            list.filter { it.companyCountryCode == countryCode }
        }
    }

    override suspend fun deleteCargo(trackNo: String) {
        val current = cargos.value.toMutableList()
        current.removeAll { it.trackNo == trackNo }
        cargos.value = current
    }

    override suspend fun getCargoByTrackingNumber(trackingNumber: String): CargoEntity? {
        return entitiesFlow.value[trackingNumber]
    }

    override fun observeCargoByTrackingNumber(trackingNumber: String): Flow<CargoEntity?> {
        return entitiesFlow.map { it[trackingNumber] }
    }

    fun addCargoEntity(entity: CargoEntity) {
        val current = entitiesFlow.value.toMutableMap()
        current[entity.trackingNumber] = entity
        entitiesFlow.value = current
    }
}
