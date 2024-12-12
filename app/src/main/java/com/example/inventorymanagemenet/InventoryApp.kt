package com.example.inventorymanagemenet

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class InventoryApp: Application() {
    override fun onCreate(){
        super.onCreate()
        Graph.provide(this)
    }
}