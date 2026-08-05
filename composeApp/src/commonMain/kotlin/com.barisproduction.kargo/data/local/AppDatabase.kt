package com.barisproduction.kargo.data.local

import androidx.room.AutoMigration
import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import androidx.sqlite.execSQL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

@Database(entities = [CargoEntity::class], version = 4)
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
        .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4)
        .build()
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(connection: SQLiteConnection) {
        // patlama dikkat
    }
}

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(connection: SQLiteConnection) {
        connection.execSQL(
            "ALTER TABLE cargo_table ADD COLUMN companyCountryCode TEXT NOT NULL DEFAULT 'tr'"
        )
    }
}

val MIGRATION_3_4 = object : Migration(3, 4) {
    override fun migrate(connection: SQLiteConnection) {
        // cargoName'in bazı kullanıcılarda NOT NULL kalması sorununu çözmek için tabloyu yeniden oluşturuyoruz
        connection.execSQL(
            """
            CREATE TABLE IF NOT EXISTS cargo_table_new (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 
                parcelName TEXT NOT NULL, 
                cargoName TEXT, 
                trackingNumber TEXT NOT NULL, 
                logo TEXT NOT NULL, 
                createdAt INTEGER NOT NULL, 
                companyCountryCode TEXT NOT NULL
            )
            """.trimIndent()
        )
        connection.execSQL(
            """
            INSERT INTO cargo_table_new (id, parcelName, cargoName, trackingNumber, logo, createdAt, companyCountryCode)
            SELECT id, parcelName, cargoName, trackingNumber, logo, createdAt, companyCountryCode FROM cargo_table
            """.trimIndent()
        )
        connection.execSQL("DROP TABLE cargo_table")
        connection.execSQL("ALTER TABLE cargo_table_new RENAME TO cargo_table")
    }
}
