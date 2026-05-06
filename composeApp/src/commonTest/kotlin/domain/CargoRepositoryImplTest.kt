package domain

import com.barisproduction.kargo.common.Resource
import com.barisproduction.kargo.data.remote.model.AppUpdateConfigDto
import com.barisproduction.kargo.data.remote.model.CargoDto
import com.barisproduction.kargo.data.repository.CargoRepositoryImpl
import data.remote.FakeCargoRemoteDataSource
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CargoRepositoryImplTest {

    private lateinit var fakeRemoteDataSource: FakeCargoRemoteDataSource
    private lateinit var repository: CargoRepositoryImpl

    @BeforeTest
    fun setup() {

        fakeRemoteDataSource = FakeCargoRemoteDataSource()
        repository = CargoRepositoryImpl(fakeRemoteDataSource)
    }

    @Test
    fun `getCargoParcelList basarili oldugunda StateFlow icinde Success ve Domain model donmeli`() =
        runTest {
            fakeRemoteDataSource.dummyDtoList = listOf(
                CargoDto(
                    name = "Aç kapıyı aras kargo",
                    url = "https://google.com",
                    logo = "logo1",
                    js = "javasriptkodu"
                ),
                CargoDto(
                    name = "Aç kapıyı sürat kargo",
                    url = "https://facebook.com",
                    logo = "logo2",
                    js = "javasriptkodu2"
                )
            )

            repository.getCargoParcelList()

            val currentState = repository.getCargoParcelListState().value

            assertTrue(currentState is Resource.Success)

            val data = (currentState as Resource.Success).data
            assertEquals(2, data?.size)
            assertEquals("Aç kapıyı aras kargo", data?.get(0)?.parcelName)
        }

    @Test
    fun `getCargoParcelList API hata verirse StateFlow icinde Error donmeli`() = runTest {
        fakeRemoteDataSource.shouldThrowError = true
        repository.getCargoParcelList()

        val currentState = repository.getCargoParcelListState().value
        assertTrue(currentState is Resource.Error)
    }

    @Test
    fun `getCargoParcelList onceden Success aldiysa gereksiz yere API cagirisi yapmamali`() =
        runTest {
            fakeRemoteDataSource.dummyDtoList = listOf(
                CargoDto(
                    name = "Aç kapıyı aras kargo",
                    url = "https://google.com",
                    logo = "logo1",
                    js = "javasriptkodu"
                )
            )

            // Act - İlk çağrı (Başarılı olacak ve StateFlow Success olacak)
            repository.getCargoParcelList()

            // Şimdi veriyi değiştirip tekrar çağıracağız. Eğer kodundaki 'if' bloğu çalışıyorsa,
            // yeni veriyi ALMAMASI lazım.
            fakeRemoteDataSource.dummyDtoList = listOf(
                CargoDto(
                    name = "aras kargo",
                    url = "https://google.com",
                    logo = "logo1",
                    js = "javasriptkodu"
                ),
                CargoDto(
                    name = "sürat kargo",
                    url = "https://facebook.com",
                    logo = "logo2",
                    js = "javasriptkodu2"
                )
            )

            // Act - İkinci çağrı
            repository.getCargoParcelList()

            // Assert
            val currentState = repository.getCargoParcelListState().value
            val data = (currentState as Resource.Success).data

            // Sadece ilk çağrıdaki verinin kalmış olması, cache mantığının çalıştığını kanıtlar
            assertEquals(1, data?.size)
            assertEquals(1, fakeRemoteDataSource.callCount)
        }

    @Test
    fun `getAppUpdateConfig basarili oldugunda Success ve dogru veriyi donmeli`() = runTest {
        fakeRemoteDataSource.dummyAppConfig = AppUpdateConfigDto(
            requireUpdate = true,
            androidStoreUrl = "http",
            iosStoreUrl = "http",
            androidMinBuildCode = 11,
            iosMinBuildCode = 11,
            )

        val result = repository.getAppUpdateConfig()

        assertTrue(result is Resource.Success)
        assertEquals(11, result.data?.androidMinBuildCode)
        assertEquals("http", result.data?.androidStoreUrl)
    }

    @Test
    fun `getAppUpdateConfig API hata verirse Resource Error donmeli`() = runTest {
        fakeRemoteDataSource.shouldThrowError = true
        val result = repository.getAppUpdateConfig()

        assertTrue(result is Resource.Error)
    }
}