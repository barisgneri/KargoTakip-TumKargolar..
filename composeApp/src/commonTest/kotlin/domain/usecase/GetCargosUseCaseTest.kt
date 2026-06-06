package domain.usecase

import com.barisproduction.kargo.domain.model.CargoModel
import com.barisproduction.kargo.domain.usecase.GetCargosUseCase
import domain.FakeAppConfigRepository
import domain.FakeLocalRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetCargosUseCaseTest {

    private lateinit var fakeLocalRepository: FakeLocalRepository
    private lateinit var fakeAppConfigRepository: FakeAppConfigRepository
    private lateinit var getCargosUseCase: GetCargosUseCase

    @BeforeTest
    fun setup() {
        fakeLocalRepository = FakeLocalRepository()
        fakeAppConfigRepository = FakeAppConfigRepository()
        getCargosUseCase = GetCargosUseCase(fakeLocalRepository, fakeAppConfigRepository)
    }

    @Test
    fun `invoke sadece secili ulkedeki kargolari donmeli`() = runTest {
        // Arrange
        val cargoTr = CargoModel("Paket TR", "Aras", "logo1", "111", companyCountryCode = "tr")
        val cargoUs = CargoModel("Paket US", "UPS", "logo2", "222", companyCountryCode = "us")
        
        fakeLocalRepository.insertCargo(cargoTr)
        fakeLocalRepository.insertCargo(cargoUs)
        
        fakeAppConfigRepository.setCountry("tr")

        // Act
        val result = getCargosUseCase().first()

        // Assert
        assertEquals(1, result.size)
        assertEquals("Paket TR", result[0].parcelName)
        
        // Change country
        fakeAppConfigRepository.setCountry("us")
        val resultUs = getCargosUseCase().first()
        assertEquals(1, resultUs.size)
        assertEquals("Paket US", resultUs[0].parcelName)
    }
}
