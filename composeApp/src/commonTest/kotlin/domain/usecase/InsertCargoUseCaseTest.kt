package domain.usecase

import com.barisproduction.kargo.domain.model.CargoModel
import com.barisproduction.kargo.domain.usecase.InsertCargoUseCase
import domain.FakeLocalRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class InsertCargoUseCaseTest {

    private lateinit var fakeLocalRepository: FakeLocalRepository
    private lateinit var insertCargoUseCase: InsertCargoUseCase

    @BeforeTest
    fun setup() {
        fakeLocalRepository = FakeLocalRepository()
        insertCargoUseCase = InsertCargoUseCase(fakeLocalRepository)
    }

    @Test
    fun `invoke kargoyu basariyla eklemeli`() = runTest {
        // Arrange
        val cargo = CargoModel(
            parcelName = "Test Paket",
            cargoName = "Aras Kargo",
            logo = "aras_logo",
            trackNo = "123456"
        )

        // Act
        insertCargoUseCase(cargo)

        // Assert
        val cargos = fakeLocalRepository.getAllCargos().first()
        assertEquals(1, cargos.size)
        assertEquals(cargo, cargos[0])
    }
}
