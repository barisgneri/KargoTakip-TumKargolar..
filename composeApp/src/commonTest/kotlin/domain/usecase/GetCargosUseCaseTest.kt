package domain.usecase

import com.barisproduction.kargo.domain.model.CargoModel
import com.barisproduction.kargo.domain.usecase.GetCargosUseCase
import domain.FakeLocalRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetCargosUseCaseTest {

    private lateinit var fakeLocalRepository: FakeLocalRepository
    private lateinit var getCargosUseCase: GetCargosUseCase

    @BeforeTest
    fun setup() {
        fakeLocalRepository = FakeLocalRepository()
        getCargosUseCase = GetCargosUseCase(fakeLocalRepository)
    }

    @Test
    fun `invoke kargolari dogru sekilde donmeli`() = runTest {
        // Arrange
        val cargo1 = CargoModel("Paket 1", "Aras", "logo1", "111")
        val cargo2 = CargoModel("Paket 2", "Surat", "logo2", "222")
        fakeLocalRepository.insertCargo(cargo1)
        fakeLocalRepository.insertCargo(cargo2)

        // Act
        val result = getCargosUseCase().first()

        // Assert
        assertEquals(2, result.size)
        assertEquals("Paket 1", result[0].parcelName)
        assertEquals("Paket 2", result[1].parcelName)
    }
}
