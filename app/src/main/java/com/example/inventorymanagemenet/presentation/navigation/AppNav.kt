package com.example.inventorymanagemenet.presentation.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.inventorymanagemenet.presentation.screen.barang.DetailBarangScreen
import com.example.inventorymanagemenet.presentation.screen.home.AddItemScreen
import com.example.inventorymanagemenet.presentation.screen.home.HomeScreen
import com.example.inventorymanagemenet.presentation.screen.home.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNav(homeViewModel: HomeViewModel,modifier: Modifier = Modifier) {

    val navController = rememberNavController() // Inisialisasi navController

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route // Set Home sebagai layar pertama
    ) {
        // Rute untuk HomeScreen
        composable(Screen.Home.route) {
            HomeScreen(
                navController = navController, // Berikan navController
                viewModel = homeViewModel
            )
        }

        // Rute untuk AddItemScreen
        composable(Screen.AddItem.route) {
            AddItemScreen(
                homeViewModel = homeViewModel,
                navController = navController,
                context = LocalContext.current
            )
        }
        composable(
            route = Screen.DetailBarang.route,  // Pastikan ini menggunakan route dengan parameter {itemId}
            arguments = listOf(navArgument("itemId") { type = NavType.IntType }) // Menangani parameter itemId
        ) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getInt("itemId") ?: 0
            DetailBarangScreen(itemId = itemId, viewModel = hiltViewModel(), modifier = Modifier, navController= navController)
        }
    }
    
}