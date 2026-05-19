package domain.usecase

import com.barisproduction.kargo.common.Resource
import com.barisproduction.kargo.domain.model.AppUpdateConfig
import com.barisproduction.kargo.domain.usecase.CheckForceUpdateUseCase
import domain.FakeCargoRepository
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class CheckForceUpdateUseCaseTest {

    private lateinit var fakeRepository: FakeCargoRepository
    private lateinit var checkForceUpdateUseCase: CheckForceUpdateUseCase

    @BeforeTest
    fun setup() {
        fakeRepository = FakeCargoRepository()
        checkForceUpdateUseCase = CheckForceUpdateUseCase(fakeRepository)
    }

    @Test
    fun `invoke Android icin guncelleme gerekliyse true donmeli`() = runTest {
        // Arrange
        val config = AppUpdateConfig(
            requireUpdate = true,
            androidMinBuildCode = 10,
            iosMinBuildCode = 10,
            androidStoreUrl = "android_url",
            iosStoreUrl = "ios_url"
        )
        fakeRepository.dummyAppUpdateConfig = Resource.Success(config)

        // Act
        val result = checkForceUpdateUseCase(platformName = "android", currentVersionCode = 5)

        // Assert
        assertTrue(result is Resource.Success)
        val data = result.data
        assertEquals(data?.isRequired, true)
        assertEquals("android_url", data?.storeUrl)
    }

    @Test
    fun `invoke Android icin guncelleme gerekli degilse false donmeli`() = runTest {
        // Arrange
        val config = AppUpdateConfig(
            requireUpdate = true,
            androidMinBuildCode = 10,
            iosMinBuildCode = 10,
            androidStoreUrl = "android_url",
            iosStoreUrl = "ios_url"
        )
        fakeRepository.dummyAppUpdateConfig = Resource.Success(config)

        // Act
        val result = checkForceUpdateUseCase(platformName = "android", currentVersionCode = 15)

        // Assert
        assertTrue(result is Resource.Success)
        val data = (result as Resource.Success).data
        assertFalse(data?.isRequired == true)
    }

    @Test
    fun `invoke iOS icin guncelleme gerekliyse true donmeli`() = runTest {
        // Arrange
        val config = AppUpdateConfig(
            requireUpdate = true,
            androidMinBuildCode = 10,
            iosMinBuildCode = 20,
            androidStoreUrl = "android_url",
            iosStoreUrl = "ios_url"
        )
        fakeRepository.dummyAppUpdateConfig = Resource.Success(config)

        // Act
        val result = checkForceUpdateUseCase(platformName = "ios", currentVersionCode = 15)

        // Assert
        assertTrue(result is Resource.Success)
        val data = (result as Resource.Success).data
        assertEquals(data?.isRequired, true)
        assertEquals("ios_url", data?.storeUrl)
    }

    @Test
    fun `invoke repository hata donerse Error donmeli`() = runTest {
        // Arrange
        fakeRepository.dummyAppUpdateConfig = Resource.Error(com.barisproduction.kargo.common.AppError.NotFound)

        // Act
        val result = checkForceUpdateUseCase(platformName = "android", currentVersionCode = 1)

        // Assert
        assertTrue(result is Resource.Error)
    }
}
