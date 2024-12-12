package com.example.inventorymanagemenet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.inventorymanagemenet.presentation.navigation.AppNav
import com.example.inventorymanagemenet.presentation.screen.home.HomeViewModel
import com.example.inventorymanagemenet.ui.theme.InventoryManagemenetTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val homeViewModel: HomeViewModel by viewModels() // Ambil ViewModel
            AppNav(homeViewModel = homeViewModel) // Jalankan navigasi
        }
    }
}



