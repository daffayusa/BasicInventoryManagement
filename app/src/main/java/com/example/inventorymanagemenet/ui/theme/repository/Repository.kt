package com.example.inventorymanagemenet.ui.theme.repository

import com.example.inventorymanagemenet.data.room.ItemDao
import com.example.inventorymanagemenet.data.room.models.ItemList
import com.example.inventorymanagemenet.data.room.models.Transaksi
import com.example.inventorymanagemenet.data.room.TransaksiDao
import kotlinx.coroutines.flow.Flow

class Repository(
    private val itemDao : ItemDao,
    private val transaksiDao: TransaksiDao
){
    suspend fun insertItem(item: ItemList) {
        itemDao.insert(item)
    }

    suspend fun updateItem(item: ItemList) {
        itemDao.update(item)
    }

    suspend fun deleteItem(item: ItemList) {
        itemDao.delete(item)
    }
    suspend fun deleteItemById(itemId: Int) {
        itemDao.deleteItemById(itemId)
    }

    fun getAllItems(): Flow<List<ItemList>> {
        return itemDao.getAllItems()
    }

    fun getItemById(itemId: Int): Flow<ItemList> {
        return itemDao.getItem(itemId)
    }

    suspend fun addStock(itemId: Int, jumlah: Int) {
        itemDao.addStok(itemId, jumlah)
    }

    suspend fun reduceStock(itemId: Int, jumlah: Int) {
        itemDao.reduceStok(itemId, jumlah)
    }

    // Transaction-related operations
    suspend fun addTransaction(transaksi: Transaksi) {
        transaksiDao.tambahTrans(transaksi)
    }

    fun getTransactionsForItem(itemId: Int): Flow<List<Transaksi>> {
        return transaksiDao.getTransaksiforItem(itemId)
    }

    fun getAllTransactions(): Flow<List<Transaksi>> {
        return transaksiDao.getAllTransaksi()
    }

}