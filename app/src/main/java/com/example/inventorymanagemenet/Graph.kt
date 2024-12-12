package com.example.inventorymanagemenet

import android.content.Context
import com.example.inventorymanagemenet.data.room.InventoryManagementDatabase
import com.example.inventorymanagemenet.ui.theme.repository.Repository

object Graph {
    lateinit var db: InventoryManagementDatabase
        private set

    val repository by lazy {
        Repository(
            itemDao = db.itemDao(),
            transaksiDao = db.transaksiDao()
        )
    }

    fun provide(context: Context){
        db = InventoryManagementDatabase.getDatabase(context)
    }


}