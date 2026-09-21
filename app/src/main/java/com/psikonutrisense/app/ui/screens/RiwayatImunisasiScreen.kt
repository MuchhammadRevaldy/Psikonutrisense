package com.psikonutrisense.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.psikonutrisense.app.data.model.VaccinationRecord
import com.psikonutrisense.app.ui.UiState
import com.psikonutrisense.app.ui.theme.*

private data class VaccineScheduleItem(val jenisImunisasi: String, val usiaLabel: String)

private val STANDARD_SCHEDULE = listOf(
    VaccineScheduleItem("Hepatitis B (HB-0)", "Usia 0 Bulan - Diberikan saat lahir"),
    VaccineScheduleItem("BCG & Polio 1", "Usia 1 Bulan - Mencegah TBC & Polio"),
    VaccineScheduleItem("DPT-HB-Hib 1 & Polio 2", "Usia 2 Bulan"),
    VaccineScheduleItem("DPT-HB-Hib 2 & Polio 3", "Usia 3 Bulan"),
    VaccineScheduleItem("DPT-HB-Hib 3 & Polio 4 & IPV", "Usia 4 Bulan"),
    VaccineScheduleItem("Campak / MR", "Usia 9 Bulan")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RiwayatImunisasiScreen(
    immunizationState: UiState<List<VaccinationRecord>>,
    onBackClick: () -> Unit,
    childId: Int = 0,
    onMarkDone: (VaccinationRecord) -> Unit = {},
    onDelete: (VaccinationRecord) -> Unit = {}
) {
    var pendingDelete by remember { mutableStateOf<VaccinationRecord?>(null) }
    val records = (immunizationState as? UiState.Success)?.data ?: emptyList()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Riwayat Imunisasi Anak", fontWeight = FontWeight.Bold) },
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
            Text(
                text = "Daftar Imunisasi Wajib & Tambahan",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TextDarkTitle
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (immunizationState is UiState.Loading) {
                Box(modifier = Modifier.fillMaxWidth().height(100.dp), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = PrimaryRose)
                }
            }

            for (scheduleItem in STANDARD_SCHEDULE) {
                val record = records.firstOrNull { it.jenisImunisasi == scheduleItem.jenisImunisasi }
                val isDone = record?.status == "Sudah"

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
                        Column(modifier = Modifier.weight(1f)) {
                            Text(scheduleItem.jenisImunisasi, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = TextDarkTitle)
                            Text(scheduleItem.usiaLabel, fontSize = 12.sp, color = TextMuted)
                            if (record?.tanggalPemberian != null) {
                                Text("Diberikan: ${record.tanggalPemberian}", fontSize = 11.sp, color = TextMuted)
                            }
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = if (isDone) GreenStatusBg else ImunisasiBlueBg
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    if (isDone) {
                                        Icon(Icons.Default.Check, contentDescription = null, tint = GreenStatusText, modifier = Modifier.size(14.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("Sudah", color = GreenStatusText, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                    } else {
                                        Text(record?.status ?: "Belum", color = ImunisasiBlueIcon, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }

                            if (!isDone) {
                                Spacer(modifier = Modifier.width(8.dp))
                                TextButton(onClick = {
                                    val todayIso = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
                                        .format(java.util.Date())
                                    val toSave = (record ?: VaccinationRecord(
                                        childId = childId,
                                        jenisImunisasi = scheduleItem.jenisImunisasi,
                                        tanggalJadwal = todayIso
                                    )).copy(
                                        status = "Sudah",
                                        tanggalPemberian = record?.tanggalPemberian ?: todayIso
                                    )
                                    onMarkDone(toSave)
                                }) {
                                    Text("Tandai Sudah", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = PrimaryRose)
                                }
                            }

                            if (record != null) {
                                IconButton(onClick = { pendingDelete = record }) {
                                    Icon(Icons.Default.Delete, contentDescription = "Hapus", tint = Color.Red, modifier = Modifier.size(18.dp))
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    val toDelete = pendingDelete
    if (toDelete != null) {
        AlertDialog(
            onDismissRequest = { pendingDelete = null },
            title = { Text("Hapus Catatan Imunisasi?") },
            text = { Text("Catatan \"${toDelete.jenisImunisasi}\" akan dihapus secara permanen.") },
            confirmButton = {
                TextButton(onClick = {
                    pendingDelete = null
                    onDelete(toDelete)
                }) {
                    Text("Hapus", color = Color.Red, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { pendingDelete = null }) {
                    Text("Batal")
                }
            }
        )
    }
}
