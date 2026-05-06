package data.remote

import com.barisproduction.kargo.data.remote.CargoRemoteDataSource
import com.barisproduction.kargo.data.remote.model.AppUpdateConfigDto
import com.barisproduction.kargo.data.remote.model.CargoDto

class FakeCargoRemoteDataSource : CargoRemoteDataSource {

    var shouldThrowError = false
    var dummyDtoList: List<CargoDto> = emptyList()
    var dummyAppConfig: AppUpdateConfigDto? = null
    var callCount = 0

    override suspend fun getAllParcels(): List<CargoDto> {
        callCount++
        if (shouldThrowError) {
            throw Exception("Sunucuya ulaşılamadı veya API hatası")
        }
        return dummyDtoList
    }

    override suspend fun getAppUpdateConfig(): AppUpdateConfigDto {
        if (shouldThrowError || dummyAppConfig == null) {
            throw Exception("Config alınamadı")
        }
        return dummyAppConfig!!
    }
}