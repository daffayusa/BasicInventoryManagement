package com.example.inventorymanagemenet.presentation.screen.barang

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.inventorymanagemenet.R
import com.example.inventorymanagemenet.data.room.ItemDao
import com.example.inventorymanagemenet.data.room.TransaksiDao
import com.example.inventorymanagemenet.data.room.models.Transaksi
import com.example.inventorymanagemenet.presentation.components.DetailItem
import com.example.inventorymanagemenet.presentation.components.TransactionKeluar
import com.example.inventorymanagemenet.presentation.components.TransactionMasuk
import com.example.inventorymanagemenet.presentation.navigation.Screen
import com.example.inventorymanagemenet.presentation.screen.home.HomeViewModel
import com.example.inventorymanagemenet.ui.theme.ButtonBg
import com.example.inventorymanagemenet.ui.theme.LightCyan
import com.example.inventorymanagemenet.ui.theme.repository.Repository
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailBarangScreen(
    itemId: Int,
    viewModel: HomeViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    // Mengambil data barang berdasarkan itemId
    LaunchedEffect(itemId) {
        viewModel.getItemById(itemId) // Memanggil getItemById untuk mendapatkan data item
        viewModel.fetchTransactionsForItem(itemId)
    }

    val item = viewModel.selectedItem.collectAsState().value
    var showDialog by remember { mutableStateOf(false) }
    var transactionType by remember { mutableStateOf("Masuk") }

    Scaffold(
        topBar =
        {
            TopAppBar(
                title = { Text(text = "Detail Barang") },
                actions = {
                    IconButton(onClick = {
                        // Panggil fungsi hapus item
                        viewModel.deleteItem(itemId)
                        // Kembali ke HomeScreen

                        navController.navigate(Screen.Home.route)
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Delete Item",
                            tint = Color.Red
                        )
                    }
                }
            )
        }
    ) {paddingValues ->
        Column(
            modifier = Modifier.padding(PaddingValues(top = 60.dp)) .background(color = LightCyan)
        ) {
            if (item != null) {
                // Menampilkan DetailItemCard dengan data dari item
                DetailItem(
                    name = item.nama_brg,
                    stok = item.stok,
                    harga = item.harga,
                    desc = item.desc,
                    deskripsi = item.deskripsi,
                    image = item.imagePath,

                    )
                Spacer(modifier = Modifier.height(8.dp))
                Column(
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp))
                        .background(color = Color.White)
                        .padding(start = 16.dp, end = 16.dp)
                        .fillMaxWidth() .fillMaxHeight()
                ) {
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Riwayat Transaksi",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Spacer(modifier = Modifier.width(62.dp))
                        Button(
                            onClick = {
                                transactionType = "Masuk"
                                showDialog = true
                            },
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = ButtonBg,
                                contentColor = Color.White
                            )
                        ) {
                            Icon(imageVector = Icons.Filled.Add, contentDescription = "Add Item")
                        }


                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    // Menampilkan riwayat transaksi
                    TransContent(viewModel = viewModel)
                }



            } else {
                // Menampilkan placeholder jika data belum ada
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Data barang tidak ditemukan", style = MaterialTheme.typography.bodyLarge)
                }
            }

        }
        // Dialog Input
        // Dialog Input
        if (showDialog) {
            TransactionInputDialog(
                transactionType = transactionType,
                onDismiss = { showDialog = false },
                onConfirm = { category, jumlah, date ->
                    showDialog = false
                    viewModel.addTransaction(
                        itemId = itemId,
                        kategori = category,
                        jumlah = jumlah,
                        tanggal = date // Mengirim tanggal transaksi ke ViewModel
                    )
                }
            )
        }


    }



}

@Composable
fun TransactionInputDialog(
    transactionType: String,
    onDismiss: () -> Unit,
    onConfirm: (String, Int, Long) -> Unit // Long untuk tanggal
) {
    var jumlahInput by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf("") }
    var selectedDateInMillis by remember { mutableStateOf(0L) } // Variabel untuk tanggal dalam bentuk Long
    var transactionCategory by remember { mutableStateOf(transactionType) }

    val context = LocalContext.current
    val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Transaksi $transactionCategory") },
        text = {
            Column {
                Text("Pilih kategori transaksi:")
                Row {
                    RadioButton(
                        selected = transactionCategory == "Masuk",
                        onClick = { transactionCategory = "Masuk" }
                    )
                    Text("Masuk")

                    Spacer(modifier = Modifier.width(16.dp))

                    RadioButton(
                        selected = transactionCategory == "Keluar",
                        onClick = { transactionCategory = "Keluar" }
                    )
                    Text("Keluar")
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text("Masukkan jumlah barang:")
                OutlinedTextField(
                    value = jumlahInput,
                    onValueChange = {
                        if (it.all { char -> char.isDigit() }) {
                            jumlahInput = it
                        }
                    },
                    label = { Text("Jumlah") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text("Pilih tanggal transaksi:")
                OutlinedTextField(
                    value = selectedDate,
                    onValueChange = { selectedDate = it },
                    label = { Text("Tanggal") },
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = {
                            val calendar = Calendar.getInstance()
                            val datePickerDialog = DatePickerDialog(
                                context,
                                { _, year, month, dayOfMonth ->
                                    calendar.set(year, month, dayOfMonth)
                                    selectedDateInMillis = calendar.timeInMillis // Simpan tanggal dalam Long
                                    selectedDate = dateFormatter.format(calendar.time) // Format menjadi string
                                },
                                calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH)
                            )
                            datePickerDialog.show()
                        }) {
                            Icon(imageVector = Icons.Filled.Add, contentDescription = "Pick Date")
                        }
                    }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (jumlahInput.isNotEmpty() && selectedDateInMillis != 0L) {
                        onConfirm(transactionCategory, jumlahInput.toInt(), selectedDateInMillis)
                    }
                }
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Batal")
            }
        }
    )
}



@Composable
fun TransContent(
    viewModel: HomeViewModel,
    modifier: Modifier = Modifier
) {
    val transactions = viewModel.transactions.collectAsState().value

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.background(color = Color.White)
    ) {
        items(transactions) { transaction ->
            if (transaction.kategori == "Masuk") {
                TransactionMasuk(
                    kategori = transaction.kategori,
                    jumlah = transaction.jumlah,
                    transaksiDate = transaction.transaksiDate
                )
            } else if (transaction.kategori == "Keluar") {
                TransactionKeluar(
                    kategori = transaction.kategori,
                    jumlah = transaction.jumlah,
                    transaksiDate = transaction.transaksiDate
                )
            }
        }
    }
}

