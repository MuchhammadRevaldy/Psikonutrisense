package com.psikonutrisense.app.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.psikonutrisense.app.data.model.GrowthRecord
import com.psikonutrisense.app.ui.UiState
import com.psikonutrisense.app.ui.components.DatePickerField
import com.psikonutrisense.app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AntropometriScreen(
    actionState: UiState<String>,
    onBackClick: () -> Unit,
    onSaveClick: (GrowthRecord) -> Unit,
    childId: Int = 0,
    existing: GrowthRecord? = null
) {
    val context = LocalContext.current
    var date by remember(existing) { mutableStateOf(existing?.tanggalPenimbangan ?: "") }
    var ageMonths by remember { mutableStateOf("12") }
    var weight by remember(existing) { mutableStateOf(existing?.beratBadanKg?.takeIf { it != 0.0 }?.toString() ?: "") }
    var height by remember(existing) { mutableStateOf(existing?.tinggiBadanCm?.takeIf { it != 0.0 }?.toString() ?: "") }
    var lila by remember(existing) { mutableStateOf(existing?.lilaCm?.takeIf { it != 0.0 }?.toString() ?: "") }
    var lika by remember(existing) { mutableStateOf(existing?.lingkarKepalaCm?.takeIf { it != 0.0 }?.toString() ?: "") }

    LaunchedEffect(actionState) {
        if (actionState is UiState.Success) {
            Toast.makeText(context, actionState.data, Toast.LENGTH_SHORT).show()
            onBackClick()
        } else if (actionState is UiState.Error) {
            Toast.makeText(context, actionState.message, Toast.LENGTH_LONG).show()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (existing != null) "Edit Data Antropometri" else "Input Data Antropometri", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali")
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
                    Text("Pengukuran Tumbuh Kembang Anak", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = PrimaryRose)
                    Spacer(modifier = Modifier.height(16.dp))

                    DatePickerField(
                        label = "Tanggal Pengukuran *",
                        value = date,
                        onDateSelected = { date = it },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = ageMonths,
                        onValueChange = { ageMonths = it },
                        label = { Text("Usia Anak (Bulan)") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    Row(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = weight,
                            onValueChange = { weight = it },
                            label = { Text("Berat Badan / BB (kg)") },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        OutlinedTextField(
                            value = height,
                            onValueChange = { height = it },
                            label = { Text("Tinggi/Panjang / TB (cm)") },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = lila,
                            onValueChange = { lila = it },
                            label = { Text("LILA (cm)") },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        OutlinedTextField(
                            value = lika,
                            onValueChange = { lika = it },
                            label = { Text("Lingkar Kepala (cm)") },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            if (date.isBlank()) {
                                Toast.makeText(context, "Mohon pilih Tanggal Pengukuran", Toast.LENGTH_SHORT).show()
                                return@Button
                            }
                            val record = GrowthRecord(
                                id = existing?.id ?: 0,
                                childId = existing?.childId ?: childId,
                                tanggalPenimbangan = date,
                                beratBadanKg = weight.toDoubleOrNull() ?: 0.0,
                                tinggiBadanCm = height.toDoubleOrNull() ?: 0.0,
                                lilaCm = lila.toDoubleOrNull() ?: 0.0,
                                lingkarKepalaCm = lika.toDoubleOrNull() ?: 0.0,
                                statusPenimbangan = existing?.statusPenimbangan ?: "Naik",
                                statusGizi = existing?.statusGizi ?: "Normal",
                                zscoreBbu = existing?.zscoreBbu ?: 0.0,
                                zscoreTbu = existing?.zscoreTbu ?: 0.0
                            )
                            onSaveClick(record)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryRose),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth().height(50.dp)
                    ) {
                        Text("Simpan & Hitung Status WHO", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }
            }
        }
    }
}
