package com.example.inventorymanagemenet.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.inventorymanagemenet.data.room.InventoryManagementDatabase
import com.example.inventorymanagemenet.data.room.ItemDao
import com.example.inventorymanagemenet.data.room.TransaksiDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {


    private val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE Transaksi ADD COLUMN transaksiDate TEXT NOT NULL DEFAULT ''")
        }
    }

    private val MIGRATION_2_3 = object : Migration(2, 3) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE ItemList ADD COLUMN desc TEXT NOT NULL DEFAULT ''")
        }
    }
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): InventoryManagementDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            InventoryManagementDatabase::class.java,
            "inventory_management_db"
        )   .fallbackToDestructiveMigration() // Reset database jika ada perubahan skema
            .build()
    }

    @Provides
    fun provideItemDao(database: InventoryManagementDatabase): ItemDao {
        return database.itemDao()
    }

    @Provides
    fun provideTransaksiDao(database: InventoryManagementDatabase): TransaksiDao {
        return database.transaksiDao()
    }
}