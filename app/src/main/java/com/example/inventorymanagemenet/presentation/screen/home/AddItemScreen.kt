package com.example.inventorymanagemenet.presentation.screen.home

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.inventorymanagemenet.presentation.components.BarangTextField
import com.example.inventorymanagemenet.presentation.navigation.Screen
import com.example.inventorymanagemenet.ui.theme.ButtonBg

import com.example.inventorymanagemenet.ui.theme.LightCyan
import okhttp3.internal.wait
import java.io.File
import java.io.FileOutputStream

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@Composable
fun AddItemScreen(homeViewModel: HomeViewModel, navController: NavController, context: Context) {
    val namaBarang = remember { mutableStateOf("") }
    val stok = remember { mutableStateOf("") }
    val harga = remember { mutableStateOf("") }
    val desc = remember { mutableStateOf("") }
    val kategori = remember { mutableStateOf("") }
    var imagePath by remember { mutableStateOf<String?>(null) }

    // Launcher untuk membuka galeri
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            imagePath = saveImageToInternalStorage(context, it)
        }
    }

    // Launcher untuk membuka kamera
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            imagePath = saveBitmapToInternalStorage(context, bitmap)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Tambah Barang Baru") })
        }
    ){
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 64.dp)
                .background(color = LightCyan)
        ) {
            UploadImageBox(
                onGallerySelected = { galleryLauncher.launch("image/*") },
                onCameraSelected = { cameraLauncher.launch(null) },
                imagePath = imagePath
            )
            Column(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp))
                    .background(color = Color.White)
                    .padding(start = 16.dp, end = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(12.dp))
                BarangTextField(
                    valueState = namaBarang,
                    label = "Nama Barang"
                )
                BarangTextField(
                    valueState = desc,
                    label = "Deskripsi"
                )
                Spacer(modifier = Modifier.height(12.dp))
                FormKategori(
                    kategori = kategori.value,
                    onKategoriSelected = {kategori.value=it}
                )
                Spacer(modifier = Modifier.height(12.dp))
                BarangTextField(
                    valueState = stok,
                    label = "Stok",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Spacer(modifier = Modifier.height(12.dp))
                BarangTextField(
                    valueState = harga,
                    label = "Harga",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Spacer(modifier = Modifier.height(12.dp))

                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = {
                        val stokInt = stok.value.toIntOrNull() ?: 0
                        val hargaInt = harga.value.toIntOrNull() ?: 0
                        homeViewModel.addNewItem(
                            namaBarang.value,
                            kategori.value,
                            desc.value,
                            stokInt,
                            hargaInt,
                            imagePath ?: ""
                        )
                        navController.navigate(Screen.Home.route)


                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ButtonBg, // Mengubah warna latar belakang
                        contentColor = Color.White // Menentukan warna teks atau ikon
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Simpan")
                }

            }

        }
    }
}


fun saveImageToInternalStorage(context: Context, uri: Uri): String {
    val inputStream = context.contentResolver.openInputStream(uri)
    val fileName = "${System.currentTimeMillis()}.jpg"
    val file = File(context.filesDir, fileName)

    inputStream?.use { input ->
        FileOutputStream(file).use { output ->
            input.copyTo(output)
        }
    }

    return file.absolutePath
}

fun saveBitmapToInternalStorage(context: Context, bitmap: Bitmap): String {
    val fileName = "${System.currentTimeMillis()}.jpg"
    val file = File(context.filesDir, fileName)

    FileOutputStream(file).use { output ->
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, output)
    }

    return file.absolutePath
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormKategori(
    kategori: String,
    onKategoriSelected: (String) -> Unit // Callback untuk mengirim kategori yang dipilih
) {

    var expanded by remember { mutableStateOf(false) } // Untuk mengontrol status dropdown
    val kategoriItem = listOf("Makanan", "Minuman", "Alat Tulis") // List kategori

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        // Input field untuk kategori
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it }
        ) {
            OutlinedTextField(
                value = kategori,
                onValueChange = { },
                label = { Text("Pilih Kategori") },
                readOnly = true, // Field ini hanya untuk tampilan, tidak bisa diketik manual
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor() // Menambahkan anchor untuk dropdown menu
                    .clickable { expanded = !expanded } // Klik untuk toggle dropdown
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                kategoriItem.forEach { label ->
                    DropdownMenuItem(
                        text = { Text(label) },
                        onClick = {
                            onKategoriSelected(label) // Pilih kategori
                            expanded = false // Tutup dropdown setelah memilih
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun UploadImageBox(
    onGallerySelected: () -> Unit,
    onCameraSelected: () -> Unit,
    imagePath: String?
) {
    var showDialog by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .padding(16.dp)
            .clip(shape = RoundedCornerShape(10.dp))
            .background(color = Color.White)
            .clickable { showDialog = true },
        contentAlignment = Alignment.Center
    ) {
        if (imagePath != null && imagePath.isNotEmpty()) {
            // Menampilkan gambar dari path file
            Image(
                painter = rememberAsyncImagePainter(File(imagePath)),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
        } else {
            // Tampilan default jika belum ada gambar
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Unggah Gambar",
                    modifier = Modifier.size(48.dp),
                    tint = Color.Gray
                )
                Text(
                    text = "Unggah Foto",
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            }
        }
    }

    // Dialog untuk memilih sumber gambar
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Pilih Sumber Gambar") },
            text = { Text("Silakan pilih untuk mengambil gambar dari galeri atau kamera.") },
            confirmButton = {
                Button(
                    onClick = {
                    showDialog = false
                    onGallerySelected()
                },colors = ButtonDefaults.buttonColors(
                    containerColor = ButtonBg, // Mengubah warna latar belakang
                    contentColor = Color.White // Menentukan warna teks atau ikon
                )) {
                    Text("Galeri")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                    showDialog = false
                    onCameraSelected()
                },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ButtonBg, // Mengubah warna latar belakang
                        contentColor = Color.White // Menentukan warna teks atau ikon
                    )) {
                    Text("Kamera")
                }
            }
        )
    }
}
