package domain.usecase

import com.barisproduction.kargo.common.Resource
import com.barisproduction.kargo.domain.model.Parcels
import com.barisproduction.kargo.domain.usecase.FindCargoInfoUseCase
import domain.FakeCargoRepository
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class FindCargoInfoUseCaseTest {

    private lateinit var fakeRepository: FakeCargoRepository
    private lateinit var findCargoInfoUseCase: FindCargoInfoUseCase

    @BeforeTest
    fun setup() {
        fakeRepository = FakeCargoRepository()
        findCargoInfoUseCase = FindCargoInfoUseCase(fakeRepository)
    }

    @Test
    fun `invoke kargo adiyla eslesen bilgiyi donmeli`() = runTest {
        // Arrange
        val parcelName = "Aras Kargo"
        val expectedParcel = Parcels(parcelName, "logo", "url", "js")
        fakeRepository.dummyParcelList = listOf(expectedParcel)
        fakeRepository.getCargoParcelList() // State'i Success yap

        // Act
        val result = findCargoInfoUseCase(parcelName)

        // Assert
        assertTrue(result is Resource.Success)
        assertEquals(expectedParcel.parcelName, result.data?.parcelName)
    }

    @Test
    fun `invoke kargo bulunamazsa hata donmeli`() = runTest {
        // Arrange
        fakeRepository.dummyParcelList = emptyList()
        fakeRepository.getCargoParcelList()

        // Act
        val result = findCargoInfoUseCase("Olmayan Kargo")

        // Assert
        assertTrue(result is Resource.Error)
    }
}
