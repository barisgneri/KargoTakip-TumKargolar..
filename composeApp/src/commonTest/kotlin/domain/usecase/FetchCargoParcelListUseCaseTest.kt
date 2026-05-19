package domain.usecase

import com.barisproduction.kargo.common.Resource
import com.barisproduction.kargo.domain.model.Parcels
import com.barisproduction.kargo.domain.usecase.FetchCargoParcelListUseCase
import domain.FakeCargoRepository
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class FetchCargoParcelListUseCaseTest {

    private lateinit var fakeRepository: FakeCargoRepository
    private lateinit var fetchCargoParcelListUseCase: FetchCargoParcelListUseCase

    @BeforeTest
    fun setup() {
        fakeRepository = FakeCargoRepository()
        fetchCargoParcelListUseCase = FetchCargoParcelListUseCase(fakeRepository)
    }

    @Test
    fun `invoke repository uzerinden kargo listesini cekmeli`() = runTest {
        // Arrange
        val parcels = listOf(Parcels("Test", "logo", "url", "js"))
        fakeRepository.dummyParcelList = parcels

        // Act
        fetchCargoParcelListUseCase()

        // Assert
        val state = fakeRepository.getCargoParcelListState().value
        assertTrue(state is Resource.Success)
        assertEquals(state.data?.containsAll(parcels), true)
    }
}
