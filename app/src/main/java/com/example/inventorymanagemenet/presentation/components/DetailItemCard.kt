package com.example.inventorymanagemenet.presentation.components

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.inventorymanagemenet.ui.theme.LightCyan

@Composable
fun DetailItem (
    name: String,
    stok:Int,
    harga:Int,
    deskripsi:String,
    desc: String,
    image: String,
    modifier: Modifier = Modifier)
{
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth() .background(color = LightCyan) .padding(start = 16.dp, end = 16.dp)
    ){
        AsyncImage(
            model = image,
            contentDescription = "Gambar dari $(item.nama_brg)",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(8.dp,8.dp,8.dp,8.dp))
                .padding(top = 20.dp),
            contentScale = ContentScale.Crop

        )
        Text(
            text = name,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth(),
            fontSize = 24.sp
        )
        Text(
            text = deskripsi,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Light,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth(),
            fontSize = 18.sp

        )
        Text(
            text = desc,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Light,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth(),
            fontSize = 18.sp

        )
        Spacer(
            modifier = Modifier.height(8.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Rp."+harga.toString(),
                textAlign = TextAlign.Left,
                fontSize = 18.sp,
                //modifier = Modifier.fillMaxWidth(),
                color = Color.Red

            )
            Spacer(modifier= Modifier.width(18.dp))
            Text(
                text = "Stok: "+stok.toString(),
                textAlign = TextAlign.Left,
                fontSize = 18.sp,
                //modifier = Modifier.fillMaxWidth(),
                color = Color.DarkGray
            )
        }


    }
}