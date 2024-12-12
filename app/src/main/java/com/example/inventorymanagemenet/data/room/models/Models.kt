package com.example.inventorymanagemenet.data.room.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName= "items" )
data class ItemList(
    @ColumnInfo(name="item_id")
    @PrimaryKey(autoGenerate = true)
    val id : Int=0,
    val nama_brg:String,
    val deskripsi:String,
    val desc: String,
    val stok : Int,
    val harga : Int,
    @ColumnInfo(name = "image_path")
    val imagePath: String // Menyimpan path gambar atau URL

)

@Entity(tableName = "Transaksi")
data class Transaksi(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "transaksi_id")
    val id: Int,
    @ColumnInfo(name = "item_id")
    val itemId: Int,
    val kategori :String,
    val jumlah : Int,
    @ColumnInfo(name = "transaksi_date")
    val transaksiDate: Long


)
