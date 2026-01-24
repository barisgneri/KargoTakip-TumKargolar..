package com.barisproduction.kargo.data.local

import androidx.room.RoomDatabaseConstructor

// The Room compiler generates the implementation of this class
actual object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    actual override fun initialize(): AppDatabase {
        TODO("Not yet implemented")
    }
}