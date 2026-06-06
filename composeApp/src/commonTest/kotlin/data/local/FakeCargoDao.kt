package data.local

import com.barisproduction.kargo.data.local.CargoDao
import com.barisproduction.kargo.data.local.CargoEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class FakeCargoDao : CargoDao {
    private val cargos = MutableStateFlow<List<CargoEntity>>(emptyList())

    override suspend fun insertCargo(cargo: CargoEntity) {
        val currentList = cargos.value.toMutableList()
        currentList.add(cargo)
        cargos.value = currentList
    }

    override suspend fun updateCargo(cargo: CargoEntity) {
        val currentList = cargos.value.toMutableList()
        val index = currentList.indexOfFirst { it.trackingNumber == cargo.trackingNumber }
        if (index != -1) {
            currentList[index] = cargo
            cargos.value = currentList
        }
    }

    override fun getAllCargos(): Flow<List<CargoEntity>> {
        return cargos.map { it.sortedByDescending { entity -> entity.createdAt } }
    }

    override fun getCargosByCountry(countryCode: String): Flow<List<CargoEntity>> {
        return cargos.map { list ->
            list.filter { it.companyCountryCode == countryCode }
                .sortedByDescending { it.createdAt }
        }
    }

    override suspend fun deleteCargo(cargo: CargoEntity) {
        val currentList = cargos.value.toMutableList()
        currentList.removeAll { it.trackingNumber == cargo.trackingNumber }
        cargos.value = currentList
    }

    override suspend fun getCargoByTrackingNumber(trackingNumber: String): CargoEntity? {
        return cargos.value.find { it.trackingNumber == trackingNumber }
    }

    override fun getCargoByTrackingNumberFlow(trackingNumber: String): Flow<CargoEntity?> {
        return cargos.map { list -> list.find { it.trackingNumber == trackingNumber } }
    }
}
