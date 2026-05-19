package domain.usecase

import com.barisproduction.kargo.domain.model.CargoModel
import com.barisproduction.kargo.domain.usecase.UpdateCargoUseCase
import domain.FakeLocalRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class UpdateCargoUseCaseTest {

    private lateinit var fakeLocalRepository: FakeLocalRepository
    private lateinit var updateCargoUseCase: UpdateCargoUseCase

    @BeforeTest
    fun setup() {
        fakeLocalRepository = FakeLocalRepository()
        updateCargoUseCase = UpdateCargoUseCase(fakeLocalRepository)
    }

    @Test
    fun `invoke kargoyu basariyla guncellemeli`() = runTest {
        // Arrange
        val trackNo = "123456"
        val initialCargo = CargoModel(
            parcelName = "Eski Isim",
            cargoName = "Aras",
            logo = "logo",
            trackNo = trackNo
        )
        fakeLocalRepository.insertCargo(initialCargo)
        
        val updatedCargo = initialCargo.copy(parcelName = "Yeni Isim")

        // Act
        updateCargoUseCase(updatedCargo)

        // Assert
        val cargos = fakeLocalRepository.getAllCargos().first()
        assertEquals(1, cargos.size)
        assertEquals("Yeni Isim", cargos[0].parcelName)
    }
}
