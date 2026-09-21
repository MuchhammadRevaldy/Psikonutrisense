package com.psikonutrisense.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
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
fun TujuanUtamaScreen(
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tujuan Utama Aplikasi", fontWeight = FontWeight.Bold) },
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
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Star, contentDescription = null, tint = AccentYellow)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Visi & Misi Psikonutrisense",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryRose
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Aplikasi Psikonutrisense dirancang secara terintegrasi untuk mendukung pencegahan stunting melalui intervensi psikososial dan gizi berbasis bahan pangan lokal.",
                        fontSize = 14.sp,
                        color = TextDarkTitle
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text("🎯 Target Utama:", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = TextDarkTitle)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("1. Mengedukasi ibu tentang pentingnya nutrisi protein hewani.", fontSize = 13.sp, color = TextMuted)
                    Text("2. Memantau secara berkala kurva pertumbuhan anak sesuai standar WHO.", fontSize = 13.sp, color = TextMuted)
                    Text("3. Menjaga kesehatan psikologis dan mengurangi tingkat stres ibu menyusui.", fontSize = 13.sp, color = TextMuted)
                    Text("4. Memberikan resep masakan berbahan dasar pangan lokal yang terjangkau dan tinggi gizi.", fontSize = 13.sp, color = TextMuted)
                }
            }
        }
    }
}
