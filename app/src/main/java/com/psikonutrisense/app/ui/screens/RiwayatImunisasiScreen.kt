package com.psikonutrisense.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.psikonutrisense.app.data.model.VaccinationRecord
import com.psikonutrisense.app.ui.UiState
import com.psikonutrisense.app.ui.theme.*
import kotlinx.coroutines.launch

private data class VaccineScheduleItem(val jenisImunisasi: String, val usiaLabel: String)

private val STANDARD_SCHEDULE = listOf(
    VaccineScheduleItem("HB", "0-7 hari"),
    VaccineScheduleItem("BCG", "0-1 bulan"),
    VaccineScheduleItem("Polio 1", "1 bulan"),
    VaccineScheduleItem("DPT-HB-Hib 1", "2 bulan"),
    VaccineScheduleItem("Polio 2", "2 bulan"),
    VaccineScheduleItem("DPT-HB-Hib 2", "3 bulan"),
    VaccineScheduleItem("Polio 3", "3 bulan"),
    VaccineScheduleItem("DPT-HB-Hib 3", "4 bulan"),
    VaccineScheduleItem("Polio 4", "4 bulan"),
    VaccineScheduleItem("Campak/MR", "9 bulan")
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
    var actionFor by remember { mutableStateOf<Pair<VaccineScheduleItem, VaccinationRecord?>?>(null) }
    val records = (immunizationState as? UiState.Success)?.data ?: emptyList()
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    val nextDue = STANDARD_SCHEDULE.firstOrNull { schedule ->
        records.none { it.jenisImunisasi == schedule.jenisImunisasi && it.status == "Sudah" }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .background(BackgroundSoftPink)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali", tint = PrimaryRose)
            }
            Text("Riwayat Imunisasi Anak", fontSize = 19.sp, fontWeight = FontWeight.Bold, color = PrimaryRose)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp)
        ) {
            if (immunizationState is UiState.Loading) {
                Box(modifier = Modifier.fillMaxWidth().height(120.dp), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = PrimaryRose)
                }
            }

            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(vertical = 8.dp)) {
                    Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)) {
                        Text("Imunisasi", fontSize = 12.sp, color = TextMuted, modifier = Modifier.weight(1.1f))
                        Text("Usia", fontSize = 12.sp, color = TextMuted, modifier = Modifier.weight(0.8f))
                        Text("Tanggal", fontSize = 12.sp, color = TextMuted, modifier = Modifier.weight(0.9f))
                        Text("Status", fontSize = 12.sp, color = PrimaryRose, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, modifier = Modifier.weight(0.6f))
                    }
                    HorizontalDivider(color = BorderLight)

                    for (schedule in STANDARD_SCHEDULE) {
                        val record = records.firstOrNull { it.jenisImunisasi == schedule.jenisImunisasi }
                        val isDone = record?.status == "Sudah"

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { actionFor = schedule to record }
                                .padding(horizontal = 16.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(schedule.jenisImunisasi, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = TextDarkTitle, modifier = Modifier.weight(1.1f))
                            Text(schedule.usiaLabel, fontSize = 12.sp, color = TextMuted, modifier = Modifier.weight(0.8f))
                            Text(record?.tanggalPemberian ?: "-", fontSize = 12.sp, color = TextMuted, modifier = Modifier.weight(0.9f))
                            Box(modifier = Modifier.weight(0.6f), contentAlignment = Alignment.Center) {
                                if (isDone) {
                                    Box(
                                        modifier = Modifier.size(24.dp).background(GreenStatusAccent, CircleShape),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(Icons.Default.Check, contentDescription = "Sudah", tint = Color.White, modifier = Modifier.size(14.dp))
                                    }
                                } else {
                                    Box(
                                        modifier = Modifier.size(24.dp).clip(CircleShape).background(BorderLight)
                                    )
                                }
                            }
                        }
                        if (schedule != STANDARD_SCHEDULE.last()) {
                            HorizontalDivider(color = BorderLight.copy(alpha = 0.6f))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (nextDue != null) {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = CardLightPink),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Imunisasi berikutnya", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = PrimaryRose)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(nextDue.jenisImunisasi, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextDarkTitle)
                        Text(nextDue.usiaLabel, fontSize = 12.sp, color = TextMuted)
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            OutlinedButton(
                onClick = { coroutineScope.launch { scrollState.animateScrollTo(0) } },
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White, contentColor = PrimaryRoseDark),
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Text("Lihat Jadwal Imunisasi", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }

    val pending = actionFor
    if (pending != null) {
        val (schedule, record) = pending
        val isDone = record?.status == "Sudah"
        AlertDialog(
            onDismissRequest = { actionFor = null },
            title = { Text(schedule.jenisImunisasi) },
            text = { Text(if (isDone) "Sudah diberikan pada ${record?.tanggalPemberian}." else "Status: Belum diberikan.") },
            confirmButton = {
                if (!isDone) {
                    TextButton(onClick = {
                        actionFor = null
                        val todayIso = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault()).format(java.util.Date())
                        val toSave = (record ?: VaccinationRecord(
                            childId = childId,
                            jenisImunisasi = schedule.jenisImunisasi,
                            tanggalJadwal = todayIso
                        )).copy(status = "Sudah", tanggalPemberian = record?.tanggalPemberian ?: todayIso)
                        onMarkDone(toSave)
                    }) {
                        Text("Tandai Sudah", color = PrimaryRose, fontWeight = FontWeight.Bold)
                    }
                }
            },
            dismissButton = {
                Row {
                    if (record != null) {
                        TextButton(onClick = {
                            actionFor = null
                            onDelete(record)
                        }) {
                            Text("Hapus", color = Color.Red)
                        }
                    }
                    TextButton(onClick = { actionFor = null }) {
                        Text("Tutup")
                    }
                }
            }
        )
    }
}
