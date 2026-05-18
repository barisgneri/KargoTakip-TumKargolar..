package domain

import com.barisproduction.kargo.data.local.CargoEntity
import com.barisproduction.kargo.data.repository.LocalRepositoryImpl
import com.barisproduction.kargo.domain.model.CargoModel
import data.local.FakeCargoDao
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class LocalRepositoryImplTest {

    private lateinit var fakeCargoDao: FakeCargoDao
    private lateinit var repository: LocalRepositoryImpl

    @BeforeTest
    fun setup() {
        fakeCargoDao = FakeCargoDao()
        repository = LocalRepositoryImpl(fakeCargoDao)
    }

    @Test
    fun `insertCargo veritabanina dogru sekilde kaydetmeli`() = runTest {
        val cargo = CargoModel(
            parcelName = "Yeni Paket",
            cargoName = "Aras Kargo",
            logo = "aras_logo",
            trackNo = "123456"
        )

        repository.insertCargo(cargo)

        val savedCargo = fakeCargoDao.getCargoByTrackingNumber("123456")
        assertNotNull(savedCargo)
        assertEquals("Yeni Paket", savedCargo.parcelName)
        assertEquals("123456", savedCargo.trackingNumber)
    }

    @Test
    fun `updateCargo mevcut kargoyu guncellemeli`() = runTest {
        val cargo = CargoModel(
            parcelName = "Eski Isim",
            cargoName = "Sürat Kargo",
            logo = "surat_logo",
            trackNo = "987654"
        )
        repository.insertCargo(cargo)

        val updatedCargo = cargo.copy(parcelName = "Guncel Isim")
        repository.updateCargo(updatedCargo)

        val result = fakeCargoDao.getCargoByTrackingNumber("987654")
        assertEquals("Guncel Isim", result?.parcelName)
    }

    @Test
    fun `deleteCargo kargoyu veritabanindan silmeli`() = runTest {
        val cargo = CargoModel(
            parcelName = "Silinecek",
            cargoName = "PTT",
            logo = "ptt_logo",
            trackNo = "000000"
        )
        repository.insertCargo(cargo)

        repository.deleteCargo("000000")

        val result = fakeCargoDao.getCargoByTrackingNumber("000000")
        assertTrue(result == null)
    }

    @Test
    fun `getAllCargos tum kargolari tarih sirasina gore Flow olarak donmeli`() = runTest {
        val entity1 = CargoEntity(
            parcelName = "Paket 1",
            trackingNumber = "1",
            cargoName = "Aras",
            logo = "logo",
            createdAt = 1000L
        )
        val entity2 = CargoEntity(
            parcelName = "Paket 2",
            trackingNumber = "2",
            cargoName = "Yurtiçi",
            logo = "logo",
            createdAt = 2000L
        )

        fakeCargoDao.insertCargo(entity1)
        fakeCargoDao.insertCargo(entity2)

        val cargosFlow = repository.getAllCargos()
        val list = cargosFlow.first()

        assertEquals(2, list.size)
        assertEquals("Paket 2", list[0].parcelName)
        assertEquals("Paket 1", list[1].parcelName)
    }
}
