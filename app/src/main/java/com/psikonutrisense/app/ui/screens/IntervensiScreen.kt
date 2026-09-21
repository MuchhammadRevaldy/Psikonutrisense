package com.psikonutrisense.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.psikonutrisense.app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IntervensiScreen(
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Intervensi Gizi & PMT", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Kembali")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PrimaryRose,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(BackgroundSoftPink)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = AccentGreen)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Rekomendasi Intervensi Spesifik",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryRose
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "1. Pemberian Makanan Tambahan (PMT) berbahan pangan lokal tinggi protein hewani (Telur, Ikan, Daging Ayam).",
                        fontSize = 13.sp,
                        color = TextDarkTitle
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "2. Pemantauan Konsumsi Vitamin A dan Tablet Tambah Darah (TTD) untuk Ibu.",
                        fontSize = 13.sp,
                        color = TextDarkTitle
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "3. Edukasi Kebersihan Lingkungan dan Akses Air Bersih (Sanitasi).",
                        fontSize = 13.sp,
                        color = TextDarkTitle
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Jadwal Log Pemantauan PMT",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TextDarkTitle
            )

            Spacer(modifier = Modifier.height(8.dp))

            val pmtDays = listOf("Hari 1: Bubur Singkong Daging Ayam", "Hari 2: Tim Telur Sayur Bayam", "Hari 3: Sup Ikan Kembung Pangan Lokal", "Hari 4: Puding Kelor Telur Puyuh")

            pmtDays.forEachIndexed { index, title ->
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(title, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                            Text("1 Porsi Habis | Terpantau Posyandu", fontSize = 12.sp, color = TextMuted)
                        }
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = GreenStatusBg
                        ) {
                            Text(
                                text = "Lengkap",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                color = GreenStatusText,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}
