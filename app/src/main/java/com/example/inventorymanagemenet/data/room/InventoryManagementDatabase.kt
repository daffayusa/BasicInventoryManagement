package com.example.inventorymanagemenet.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.inventorymanagemenet.data.room.models.ItemList
import com.example.inventorymanagemenet.data.room.models.Transaksi

@Database(
    entities = [ItemList::class, Transaksi:: class],
    version = 3,
    exportSchema = false
)
abstract class InventoryManagementDatabase:RoomDatabase() {
    abstract fun itemDao(): ItemDao
    abstract fun transaksiDao(): TransaksiDao

    companion object {
        @Volatile
        private var INSTANCE: InventoryManagementDatabase? = null
        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Tambahkan kolom baru 'desc' dengan nilai default
                database.execSQL("ALTER TABLE ItemList ADD COLUMN desc TEXT NOT NULL DEFAULT ''")
            }
        }

        fun getDatabase(context: Context): InventoryManagementDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    InventoryManagementDatabase::class.java,
                    "inventory_management_database"
                )
                    .fallbackToDestructiveMigration() // Untuk reset data saat ada perubahan skema
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}