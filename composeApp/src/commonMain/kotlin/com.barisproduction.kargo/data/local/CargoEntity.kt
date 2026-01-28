package com.barisproduction.kargo.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cargo_table")
data class CargoEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String? = null,
    val cargoName: String,
    val trackingNumber: String,
    val createdAt: Long,
)
