package com.example.inventorymanagemenet.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.inventorymanagemenet.data.room.models.ItemList
import com.example.inventorymanagemenet.data.room.models.Transaksi
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(itemList: ItemList)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(itemList: ItemList)

    @Delete
    suspend fun delete(itemList: ItemList)

    @Query("SELECT * FROM items")
    fun getAllItems():Flow<List<ItemList>>

    @Query("SELECT * FROM items WHERE item_id = :itemId")
    fun getItem(itemId: Int):Flow<ItemList>

    @Query("UPDATE Items Set stok = stok + :jumlah where item_id = :itemId")
    suspend fun addStok(itemId: Int, jumlah: Int)

    @Query("UPDATE Items Set stok =  stok - :jumlah where item_id= :itemId")
    suspend fun reduceStok(itemId: Int, jumlah: Int)


    @Query("DELETE FROM items WHERE item_id = :itemId")
    suspend fun deleteItemById(itemId: Int)

}

@Dao
interface TransaksiDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun tambahTrans (transaksi: Transaksi)

    @Query("SELECT * FROM transaksi WHERE item_id = :ItemId ORDER BY transaksi_date DESC")
    fun getTransaksiforItem(ItemId: Int): Flow<List<Transaksi>>

    @Query("SELECT * FROM Transaksi ORDER BY transaksi_date DESC")
    fun getAllTransaksi(): Flow<List<Transaksi>>


}