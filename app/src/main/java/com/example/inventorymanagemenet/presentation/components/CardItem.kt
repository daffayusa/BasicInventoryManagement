package com.example.inventorymanagemenet.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.inventorymanagemenet.ui.theme.LightCyan

@Composable
fun ItemCard(
    name: String,
    stok:Int,
    harga:Int,
    kategori: String,
    image: String,
    onClick: ()->Unit,
    modifier: Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp), // Rounded corner
        elevation = CardDefaults.cardElevation( 4.dp), // Bayangan
        modifier = Modifier.fillMaxWidth() .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            AsyncImage(
                model = image,
                contentDescription = "Gambar dari $(item.nama_brg)",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop

            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = name,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth(),
                fontSize = 18.sp

            )
            Text(
                text = kategori,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Light,
                color = Color.Gray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth(),
                fontSize = 16.sp

            )
            Spacer(
                modifier = Modifier.height(8.dp)
            )
            Text(
                text = "Rp."+harga.toString(),
                textAlign = TextAlign.Left,
                fontSize = 16.sp,
                modifier = Modifier.fillMaxWidth(),
                color = Color.Red

            )
            Spacer(
                modifier=Modifier.height(6.dp)
            )
            Text(
                text = "Stok: "+stok.toString(),
                textAlign = TextAlign.Left,
                fontSize = 12.sp,
                modifier = Modifier.fillMaxWidth(),
                color = Color.DarkGray
            )

        }
    }
}