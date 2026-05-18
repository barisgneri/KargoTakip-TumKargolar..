package domain

import com.barisproduction.kargo.data.local.CargoEntity
import com.barisproduction.kargo.domain.model.CargoModel
import com.barisproduction.kargo.domain.repository.LocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeLocalRepository : LocalRepository {
    private val cargos = MutableStateFlow<List<CargoModel>>(emptyList())
    private val entities = mutableMapOf<String, CargoEntity>()

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

    override suspend fun deleteCargo(trackNo: String) {
        val current = cargos.value.toMutableList()
        current.removeAll { it.trackNo == trackNo }
        cargos.value = current
    }

    override suspend fun getCargoByTrackingNumber(trackingNumber: String): CargoEntity? {
        return entities[trackingNumber]
    }

    fun addCargoEntity(entity: CargoEntity) {
        entities[entity.trackingNumber] = entity
    }
}
