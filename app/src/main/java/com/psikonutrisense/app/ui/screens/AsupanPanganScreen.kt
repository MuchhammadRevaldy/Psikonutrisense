package com.psikonutrisense.app.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.psikonutrisense.app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AsupanPanganScreen(
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    var eatingFrequency by remember { mutableStateOf("3 Kali / Hari") }
    var proteinTypes by remember { mutableStateOf("Telur, Ikan Kembung, Ayam") }
    var vegFruitFreq by remember { mutableStateOf("Setiap Hari") }
    var snackFreq by remember { mutableStateOf("2 Kali / Hari") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Asupan Pangan & Nutrisi Harian", fontWeight = FontWeight.Bold) },
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
                    Text("Survey Asupan Makan 24 Jam Terakhir", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = PrimaryRose)
                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = eatingFrequency,
                        onValueChange = { eatingFrequency = it },
                        label = { Text("Frekuensi Makan Utama (per Hari)") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = proteinTypes,
                        onValueChange = { proteinTypes = it },
                        label = { Text("Sumber Protein Hewani yang Dikonsumsi") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = vegFruitFreq,
                        onValueChange = { vegFruitFreq = it },
                        label = { Text("Frekuensi Sayur & Buah") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = snackFreq,
                        onValueChange = { snackFreq = it },
                        label = { Text("Frekuensi Makanan Selingan (Snack)") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            Toast.makeText(context, "Asupan Pangan Berhasil Disimpan", Toast.LENGTH_SHORT).show()
                            onBackClick()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryRose),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth().height(50.dp)
                    ) {
                        Text("Simpan Survey Asupan", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }
            }
        }
    }
}
