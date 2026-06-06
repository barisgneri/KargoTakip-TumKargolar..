package domain.usecase

import com.barisproduction.kargo.common.Resource
import com.barisproduction.kargo.domain.model.Parcels
import com.barisproduction.kargo.domain.usecase.GetCargoParcelListUseCase
import domain.FakeCargoRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GetCargoParcelListUseCaseTest {

    private lateinit var fakeRepository: FakeCargoRepository
    private lateinit var getCargoParcelListUseCase: GetCargoParcelListUseCase

    @BeforeTest
    fun setup() {
        fakeRepository = FakeCargoRepository()
        getCargoParcelListUseCase = GetCargoParcelListUseCase(fakeRepository)
    }

    @Test
    fun `invoke kargo listesini flow olarak donmeli`() = runTest {
        // Arrange
        val parcels = listOf(Parcels("Paket", "logo", "url", "js"))
        fakeRepository.dummyParcelList = parcels
        fakeRepository.getCargoParcelList() // State'i guncelle

        // Act
        val result = getCargoParcelListUseCase().first()

        // Assert
        assertTrue(result is Resource.Success)
        assertEquals(parcels, (result as Resource.Success).data)
    }
}
