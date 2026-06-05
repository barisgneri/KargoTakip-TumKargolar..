package domain.usecase

import com.barisproduction.kargo.domain.model.CargoModel
import com.barisproduction.kargo.domain.usecase.InsertCargoUseCase
import domain.FakeAppConfigRepository
import domain.FakeLocalRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class InsertCargoUseCaseTest {

    private lateinit var fakeLocalRepository: FakeLocalRepository
    private lateinit var fakeAppConfigRepository: FakeAppConfigRepository
    private lateinit var insertCargoUseCase: InsertCargoUseCase

    @BeforeTest
    fun setup() {
        fakeLocalRepository = FakeLocalRepository()
        fakeAppConfigRepository = FakeAppConfigRepository()
        insertCargoUseCase = InsertCargoUseCase(fakeLocalRepository, fakeAppConfigRepository)
    }

    @Test
    fun `invoke kargoyu basariyla eklemeli ve ulke kodunu set etmeli`() = runTest {
        // Arrange
        fakeAppConfigRepository.setCountry("tr")
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
        assertEquals("tr", cargos[0].companyCountryCode)
        assertEquals(cargo.trackNo, cargos[0].trackNo)
    }
}
