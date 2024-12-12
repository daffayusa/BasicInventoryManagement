package com.example.inventorymanagemenet.presentation.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home_screen")
    object AddItem : Screen("add_item_screen")
    object DetailBarang : Screen("detail_barang/{itemId}")
}