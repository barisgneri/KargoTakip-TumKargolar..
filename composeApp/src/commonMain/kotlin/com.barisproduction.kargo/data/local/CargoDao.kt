package com.barisproduction.kargo.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CargoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCargo(cargo: CargoEntity)

    @Update
    suspend fun updateCargo(cargo: CargoEntity)

    @Query("SELECT * FROM cargo_table ORDER BY createdAt DESC")
    fun getAllCargos(): Flow<List<CargoEntity>>

    @Delete
    suspend fun deleteCargo(cargo: CargoEntity)

    @Query("SELECT * FROM cargo_table WHERE trackingNumber = :trackingNumber LIMIT 1")
    suspend fun getCargoByTrackingNumber(trackingNumber: String): CargoEntity?
}
