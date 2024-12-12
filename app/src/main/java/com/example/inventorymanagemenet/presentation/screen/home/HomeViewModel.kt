package com.example.inventorymanagemenet.presentation.screen.home

import android.content.res.Resources
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.inventorymanagemenet.Graph
import com.example.inventorymanagemenet.data.room.models.ItemList
import com.example.inventorymanagemenet.data.room.models.Transaksi
import com.example.inventorymanagemenet.ui.theme.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    // StateFlow untuk daftar item yang akan diobservasi UI
    private val _items = MutableStateFlow<List<ItemList>>(emptyList())
    val items: StateFlow<List<ItemList>> = _items.asStateFlow()

    private val _selectedItem = MutableStateFlow<ItemList?>(null)
    val selectedItem: StateFlow<ItemList?> = _selectedItem

    // StateFlow untuk riwayat transaksi
    private val _transactions = MutableStateFlow<List<Transaksi>>(emptyList())
    val transactions: StateFlow<List<Transaksi>> = _transactions.asStateFlow()
    // Load data dari repository
    init {
        fetchItems()
    }

    private fun fetchItems() {
        viewModelScope.launch {
            repository.getAllItems().collect { itemList ->
                _items.value = itemList
            }
        }
    }

    fun addNewItem(namaBarang: String, deskripsi: String, desc: String, stok: Int, harga: Int, imagePath: String) {
        viewModelScope.launch {
            val newItem = ItemList(
                nama_brg = namaBarang,
                deskripsi = deskripsi,
                desc = desc,
                stok = stok,
                harga = harga,
                imagePath = imagePath
            )
            repository.insertItem(newItem)
        }
    }

    fun getItemById(itemId: Int) {
        viewModelScope.launch {
            repository.getItemById(itemId).collect { item ->
                _selectedItem.value = item
            }
        }
    }

    fun addTransaction(itemId: Int, kategori: String, jumlah: Int, tanggal: Long) {
        viewModelScope.launch {
            try {
                // Validasi jumlah
                if (jumlah <= 0) throw IllegalArgumentException("Jumlah harus lebih dari 0")

                val item = repository.getItemById(itemId).first() // Pastikan menggunakan first() atau firstOrNull()
                if (kategori == "Keluar" && item.stok < jumlah) {
                    throw IllegalStateException("Stok tidak mencukupi")
                }

                val transaksi = Transaksi(
                    id = 0,
                    itemId = itemId,
                    kategori = kategori,
                    jumlah = jumlah,
                    transaksiDate = tanggal // Menyimpan tanggal transaksi sebagai String
                )

                // Simpan transaksi
                repository.addTransaction(transaksi)

                // Update stok
                if (kategori == "Masuk") {
                    repository.addStock(itemId, jumlah)
                } else if (kategori == "Keluar") {
                    repository.reduceStock(itemId, jumlah)
                }

                // Perbarui data
                getItemById(itemId)
                fetchTransactionsForItem(itemId)
            } catch (e: Exception) {
                // Log atau tampilkan pesan error
                Log.e("HomeViewModel", "Error saat menambahkan transaksi: ${e.message}")
            }
        }
    }


    // Mengambil riwayat transaksi untuk item tertentu
    fun fetchTransactionsForItem(itemId: Int) {
        viewModelScope.launch {
            repository.getTransactionsForItem(itemId).collect { transaksiList ->
                _transactions.value = transaksiList
            }
        }
    }

    // Menghapus item berdasarkan ID
    fun deleteItem(itemId: Int) {
        viewModelScope.launch {
            repository.deleteItemById(itemId)
            fetchItems() // Refresh daftar item
        }
    }


}



