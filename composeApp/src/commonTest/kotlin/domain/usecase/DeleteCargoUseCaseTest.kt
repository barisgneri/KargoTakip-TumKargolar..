package domain.usecase

import com.barisproduction.kargo.domain.model.CargoModel
import com.barisproduction.kargo.domain.usecase.DeleteCargoUseCase
import domain.FakeLocalRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class DeleteCargoUseCaseTest {

    private lateinit var fakeLocalRepository: FakeLocalRepository
    private lateinit var deleteCargoUseCase: DeleteCargoUseCase

    @BeforeTest
    fun setup() {
        fakeLocalRepository = FakeLocalRepository()
        deleteCargoUseCase = DeleteCargoUseCase(fakeLocalRepository)
    }

    @Test
    fun `invoke kargoyu basariyla silmeli`() = runTest {
        // Arrange
        val trackNo = "123456"
        val cargo = CargoModel(
            parcelName = "Silinecek",
            cargoName = "Aras",
            logo = "logo",
            trackNo = trackNo
        )
        fakeLocalRepository.insertCargo(cargo)

        // Act
        deleteCargoUseCase(trackNo)

        // Assert
        val cargos = fakeLocalRepository.getAllCargos().first()
        assertEquals(0, cargos.size)
    }
}
