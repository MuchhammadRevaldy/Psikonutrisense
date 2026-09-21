package com.psikonutrisense.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Notifications
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
fun EdukasiScreen(
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edukasi & Pengingat Gizi", fontWeight = FontWeight.Bold) },
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
                        Icon(Icons.Default.Notifications, contentDescription = null, tint = PrimaryRose)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Pengingat Posyandu & TTD",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryRose
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text("📅 Posyandu Rutin: Setiap tanggal 15 tiap bulan", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = TextDarkTitle)
                    Text("💊 Tablet Tambah Darah Ibu: Minum 1 tablet/hari (Malam hari)", fontSize = 13.sp, color = TextMuted)
                    Text("👶 Imunisasi DPT-HB-Hib 3: Jatuh tempo minggu depan", fontSize = 13.sp, color = TextMuted)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Materi Edukasi Kesehatan & Nutrisi",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TextDarkTitle
            )

            Spacer(modifier = Modifier.height(8.dp))

            val articles = listOf(
                "Pentingnya 1.000 Hari Pertama Kehidupan (HPK) Bagi Bayi",
                "Mengenal Protein Hewani sebagai Penolak Stunting Utama",
                "Pola Pengasuhan dan Kesehatan Mental Ibu Menyusui",
                "Sanitasi Lingkungan: Cuci Tangan Pakai Sabun & Air Bersih"
            )

            articles.forEach { title ->
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
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = CardPinkBg,
                            modifier = Modifier.size(40.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(Icons.Default.Lightbulb, contentDescription = null, tint = PrimaryRose)
                            }
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Text(
                            text = title,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextDarkTitle,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}
