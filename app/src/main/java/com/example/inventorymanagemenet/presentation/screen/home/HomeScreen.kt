package com.example.inventorymanagemenet.presentation.screen.home

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController


import coil.compose.AsyncImage
import com.example.inventorymanagemenet.data.room.models.ItemList
import com.example.inventorymanagemenet.presentation.components.ItemCard
import com.example.inventorymanagemenet.presentation.navigation.AppNav
import com.example.inventorymanagemenet.presentation.navigation.Screen
import com.example.inventorymanagemenet.ui.theme.BaseBg
import com.example.inventorymanagemenet.ui.theme.ButtonBg

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navController: NavController, // Untuk navigasi
    viewModel: HomeViewModel = hiltViewModel()
) {
    val items by viewModel.items.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var itemToDelete by remember { mutableStateOf<ItemList?>(null) }



    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Inventory Management") },

            )
        },
        floatingActionButton = {
            FloatingActionButton(
                containerColor = ButtonBg,
                shape = RoundedCornerShape(10.dp),
                onClick = { navController.navigate(Screen.AddItem.route) },

            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add Item")
            }
        }
    ) {paddingValues ->

        HomeScreenContent(
            viewModel = viewModel,
            onNavigateToAddItem = {
                navController.navigate(Screen.AddItem.route) // Navigasi ke AddItemScreen
            },
            modifier = Modifier.padding(PaddingValues(top = 60.dp)) .background(color = BaseBg),
            navController = navController)



    }
}




@Composable
fun HomeScreenContent(
    viewModel: HomeViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
    onNavigateToAddItem: ()-> Unit
) {
    Spacer(modifier = Modifier.height(64.dp))
    val items by viewModel.items.collectAsState()
    LazyVerticalGrid(
        columns = GridCells.Adaptive(140.dp),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.fillMaxSize()
    ) {
        items(items, key = { it.id }) { item ->
            ItemCard(
                name = item.nama_brg,
                stok = item.stok,
                kategori = item.deskripsi,
                harga = item.harga,
                image = item.imagePath,
                modifier = Modifier,
                onClick = {
                    // Navigasi dengan hanya mengirim item.id
                    navController.navigate("detail_barang/${item.id}")
                }
            )
        }
    }

    // Floating Action Button di sudut kanan bawah



}



