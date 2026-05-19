package domain.usecase

import com.barisproduction.kargo.data.local.CargoEntity
import com.barisproduction.kargo.domain.usecase.CheckCargoInDBUseCase
import domain.FakeLocalRepository
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class CheckCargoInDBUseCaseTest {

    private lateinit var fakeLocalRepository: FakeLocalRepository
    private lateinit var checkCargoInDBUseCase: CheckCargoInDBUseCase

    @BeforeTest
    fun setup() {
        fakeLocalRepository = FakeLocalRepository()
        checkCargoInDBUseCase = CheckCargoInDBUseCase(fakeLocalRepository)
    }

    @Test
    fun `invoke kargo DBde varsa true donmeli`() = runTest {
        // Arrange
        val trackingNumber = "123456"
        val entity = CargoEntity(
            parcelName = "Test",
            trackingNumber = trackingNumber,
            cargoName = "Aras",
            logo = "logo",
            createdAt = 1000L
        )
        fakeLocalRepository.addCargoEntity(entity)

        // Act
        val result = checkCargoInDBUseCase(trackingNumber)

        // Assert
        assertTrue(result)
    }

    @Test
    fun `invoke kargo DBde yoksa false donmeli`() = runTest {
        // Arrange
        val trackingNumber = "999999"

        // Act
        val result = checkCargoInDBUseCase(trackingNumber)

        // Assert
        assertFalse(result)
    }
}
