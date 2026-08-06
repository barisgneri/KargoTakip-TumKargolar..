package domain

import app.cash.turbine.test
import com.barisproduction.kargo.common.Resource
import com.barisproduction.kargo.data.remote.model.AppUpdateConfigDto
import com.barisproduction.kargo.data.remote.model.CargoDto
import com.barisproduction.kargo.data.repository.CargoRepositoryImpl
import com.barisproduction.kargo.domain.repository.CargoRepository
import data.remote.FakeCargoRemoteDataSource
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CargoRepositoryImplTest {

    private lateinit var fakeRemoteDataSource: FakeCargoRemoteDataSource
    private lateinit var fakeAppConfigRepository: FakeAppConfigRepository
    private lateinit var repository: CargoRepository

    @BeforeTest
    fun setup() {
        fakeRemoteDataSource = FakeCargoRemoteDataSource()
        fakeAppConfigRepository = FakeAppConfigRepository()
        repository = CargoRepositoryImpl(fakeRemoteDataSource, fakeAppConfigRepository)
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

            repository.getCargoParcelListState().test {
                // 1. Initial State (Loading)
                assertTrue(awaitItem() is Resource.Loading)

                repository.getCargoParcelList()

                // 2. getCargoParcelList basinda tekrar Loading emit ediliyor
                assertTrue(awaitItem() is Resource.Loading)

                // 3. Success State
                val successState = awaitItem()
                assertTrue(successState is Resource.Success)

                val data = (successState as Resource.Success).data
                assertEquals(2, data?.size)
                assertEquals("Aç kapıyı aras kargo", data?.get(0)?.parcelName)
            }
        }

    @Test
    fun `getCargoParcelList API hata verirse StateFlow icinde Error donmeli`() = runTest {
        fakeRemoteDataSource.shouldThrowError = true

        repository.getCargoParcelListState().test {
            // 1. Initial State (Loading)
            assertTrue(awaitItem() is Resource.Loading)

            repository.getCargoParcelList()

            // 2. getCargoParcelList basinda tekrar Loading emit ediliyor
            assertTrue(awaitItem() is Resource.Loading)

            // 3. Error State
            assertTrue(awaitItem() is Resource.Error)
        }
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

            repository.getCargoParcelListState().test {
                // Initial State
                assertTrue(awaitItem() is Resource.Loading)

                // Act - İlk çağrı
                repository.getCargoParcelList()
                // Loading ve sonra Success bekliyoruz
                assertTrue(awaitItem() is Resource.Loading)
                val firstSuccess = awaitItem()
                assertTrue(firstSuccess is Resource.Success)

                // Şimdi veriyi değiştirip tekrar çağıracağız.
                fakeRemoteDataSource.dummyDtoList = listOf(
                    CargoDto(
                        name = "aras kargo",
                        url = "https://google.com",
                        logo = "logo1",
                        js = "javasriptkodu"
                    )
                )

                // Act - İkinci çağrı
                repository.getCargoParcelList()

                // Assert - Yeni bir event gelmemeli (çünkü cache'den dönüyor ve StateFlow aynı değeri emit etmez)
                expectNoEvents()

                val data = (firstSuccess as Resource.Success).data
                assertEquals(1, data?.size)
                assertEquals(1, fakeRemoteDataSource.callCount)
            }
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