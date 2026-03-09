package com.barisproduction.kargo.data.local

import androidx.room.AutoMigration
import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.migration.AutoMigrationSpec
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

@Database(entities = [CargoEntity::class], version = 2, autoMigrations = [
    AutoMigration(from = 1, to = 2)
])
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cargoDao(): CargoDao
}

expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase>{
    override fun initialize(): AppDatabase
}

fun getRoomDatabase(
    builder: RoomDatabase.Builder<AppDatabase>
): AppDatabase {
    return builder
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}
