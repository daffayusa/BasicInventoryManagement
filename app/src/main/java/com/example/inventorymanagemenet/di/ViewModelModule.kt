package com.example.inventorymanagemenet.di


import com.example.inventorymanagemenet.data.room.InventoryManagementDatabase
import com.example.inventorymanagemenet.data.room.ItemDao
import com.example.inventorymanagemenet.data.room.TransaksiDao
import com.example.inventorymanagemenet.presentation.screen.home.HomeViewModel
import com.example.inventorymanagemenet.ui.theme.repository.Repository

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ViewModelModule {

    @Provides
    @Singleton
    fun provideRepository(itemDao: ItemDao, transaksiDao: TransaksiDao): Repository {
        return Repository(itemDao, transaksiDao)
    }
}