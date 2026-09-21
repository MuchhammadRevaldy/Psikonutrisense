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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.psikonutrisense.app.data.model.Child
import com.psikonutrisense.app.ui.UiState
import com.psikonutrisense.app.ui.components.DatePickerField
import com.psikonutrisense.app.ui.components.DropdownField
import com.psikonutrisense.app.ui.components.YaTidakRadioRow
import com.psikonutrisense.app.ui.theme.*

private val PILL_FIELD_SHAPE = RoundedCornerShape(28.dp)

@Composable
fun DataDiriAnakScreen(
    actionState: UiState<String>,
    onBackClick: () -> Unit,
    onSaveClick: (Child) -> Unit,
    existing: Child? = null,
    onDeleteClick: () -> Unit = {},
    isOnboarding: Boolean = false,
    onOnboardingComplete: () -> Unit = {}
) {
    val context = LocalContext.current
    var name by remember(existing) { mutableStateOf(existing?.namaBalita ?: "") }
    var dob by remember(existing) { mutableStateOf(existing?.tanggalLahirBalita ?: "") }
    var gender by remember(existing) { mutableStateOf(existing?.jenisKelamin?.takeIf { it.isNotBlank() } ?: "L") }
    var birthWeight by remember(existing) { mutableStateOf(existing?.bbLahirKg?.toString() ?: "") }
    var birthHeight by remember(existing) { mutableStateOf(existing?.pbLahirCm?.toString() ?: "") }
    var jumlahSaudara by remember(existing) { mutableStateOf((existing?.jumlahSaudara ?: 0).toString()) }
    var punyaKia by remember(existing) { mutableStateOf(existing?.statusKepemilikanKia ?: true) }
    var jenisPersalinan by remember(existing) { mutableStateOf(existing?.jenisPersalinan?.takeIf { it.isNotBlank() } ?: "Normal") }
    var usiaKehamilan by remember(existing) { mutableStateOf(existing?.usiaKehamilanMinggu?.toString() ?: "") }
    var showDeleteConfirm by remember { mutableStateOf(false) }

    LaunchedEffect(actionState) {
        if (actionState is UiState.Success) {
            Toast.makeText(context, actionState.data, Toast.LENGTH_SHORT).show()
            if (isOnboarding) {
                onOnboardingComplete()
            } else {
                onBackClick()
            }
        } else if (actionState is UiState.Error) {
            Toast.makeText(context, actionState.message, Toast.LENGTH_LONG).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .background(BackgroundSoftPink)
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {
        if (!isOnboarding) {
            IconButton(onClick = onBackClick, modifier = Modifier.padding(bottom = 4.dp)) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali", tint = PrimaryRose)
            }
        }

        Text(
            text = "Data Diri Anak",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = PrimaryRose,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(24.dp))

        if (isOnboarding) {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = CardLightPink),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Hampir selesai! 👶", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = PrimaryRoseDark)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Isi data anak Anda untuk melanjutkan", fontSize = 13.sp, color = TextMuted)
                    Spacer(modifier = Modifier.height(12.dp))
                    LinearProgressIndicator(
                        progress = { 1.0f },
                        modifier = Modifier.fillMaxWidth().height(6.dp),
                        color = PrimaryRose,
                        trackColor = BorderLight
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Langkah 2 dari 2", fontSize = 11.sp, color = TextMuted)
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
        }

        Text("Nama Anak", fontSize = 14.sp, color = TextDarkTitle)
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            modifier = Modifier.fillMaxWidth(),
            shape = PILL_FIELD_SHAPE,
            colors = OutlinedTextFieldDefaults.colors(unfocusedContainerColor = Color.White, focusedContainerColor = Color.White, unfocusedBorderColor = Color.Transparent)
        )
        Spacer(modifier = Modifier.height(16.dp))

        DatePickerField(label = "Tanggal Lahir", value = dob, onDateSelected = { dob = it }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(16.dp))

        Text("Jenis Kelamin", fontSize = 14.sp, color = TextDarkTitle)
        Spacer(modifier = Modifier.height(6.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            FilterChip(
                selected = gender == "L",
                onClick = { gender = "L" },
                label = { Text("Laki-laki") },
                colors = FilterChipDefaults.filterChipColors(selectedContainerColor = ButtonPinkBg, selectedLabelColor = ButtonPinkText)
            )
            Spacer(modifier = Modifier.width(8.dp))
            FilterChip(
                selected = gender == "P",
                onClick = { gender = "P" },
                label = { Text("Perempuan") },
                colors = FilterChipDefaults.filterChipColors(selectedContainerColor = ButtonPinkBg, selectedLabelColor = ButtonPinkText)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Text("Jumlah Saudara", fontSize = 14.sp, color = TextDarkTitle)
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = jumlahSaudara,
            onValueChange = { jumlahSaudara = it.filter { c -> c.isDigit() } },
            modifier = Modifier.fillMaxWidth(),
            shape = PILL_FIELD_SHAPE,
            colors = OutlinedTextFieldDefaults.colors(unfocusedContainerColor = Color.White, focusedContainerColor = Color.White, unfocusedBorderColor = Color.Transparent)
        )
        Spacer(modifier = Modifier.height(16.dp))

        YaTidakRadioRow(label = "Mempunyai Buku KIA", value = punyaKia, onChange = { punyaKia = it })
        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = birthWeight,
                onValueChange = { birthWeight = it },
                label = { Text("Berat Lahir (kg)") },
                modifier = Modifier.weight(1f),
                shape = PILL_FIELD_SHAPE,
                colors = OutlinedTextFieldDefaults.colors(unfocusedContainerColor = Color.White, focusedContainerColor = Color.White, unfocusedBorderColor = Color.Transparent)
            )
            Spacer(modifier = Modifier.width(12.dp))
            OutlinedTextField(
                value = birthHeight,
                onValueChange = { birthHeight = it },
                label = { Text("Panjang Lahir (cm)") },
                modifier = Modifier.weight(1f),
                shape = PILL_FIELD_SHAPE,
                colors = OutlinedTextFieldDefaults.colors(unfocusedContainerColor = Color.White, focusedContainerColor = Color.White, unfocusedBorderColor = Color.Transparent)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        DropdownField(
            label = "Jenis Persalinan",
            selectedValue = jenisPersalinan,
            options = listOf("Normal", "Caesar"),
            onValueSelected = { jenisPersalinan = it }
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text("Usia Kehamilan saat Lahir (minggu)", fontSize = 14.sp, color = TextDarkTitle)
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = usiaKehamilan,
            onValueChange = { usiaKehamilan = it.filter { c -> c.isDigit() } },
            placeholder = { Text("mis. 39") },
            modifier = Modifier.fillMaxWidth(),
            shape = PILL_FIELD_SHAPE,
            colors = OutlinedTextFieldDefaults.colors(unfocusedContainerColor = Color.White, focusedContainerColor = Color.White, unfocusedBorderColor = Color.Transparent)
        )

        Spacer(modifier = Modifier.height(28.dp))

        Button(
            onClick = {
                if (name.isBlank() || dob.isBlank()) {
                    Toast.makeText(context, "Mohon isi Nama & Tanggal Lahir Anak", Toast.LENGTH_SHORT).show()
                    return@Button
                }
                val child = Child(
                    id = existing?.id ?: 0,
                    motherId = existing?.motherId ?: 0,
                    namaBalita = name,
                    tanggalLahirBalita = dob,
                    jenisKelamin = gender,
                    anakKe = existing?.anakKe ?: 1,
                    jumlahSaudara = jumlahSaudara.toIntOrNull() ?: 0,
                    bbLahirKg = birthWeight.toDoubleOrNull(),
                    pbLahirCm = birthHeight.toDoubleOrNull(),
                    lahirPrematur = existing?.lahirPrematur ?: false,
                    jenisPersalinan = jenisPersalinan,
                    usiaKehamilanMinggu = usiaKehamilan.toIntOrNull(),
                    statusKepemilikanKia = punyaKia
                )
                onSaveClick(child)
            },
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryRoseDark),
            shape = RoundedCornerShape(28.dp),
            enabled = actionState !is UiState.Loading,
            modifier = Modifier.fillMaxWidth().height(54.dp)
        ) {
            if (actionState is UiState.Loading) {
                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
            } else {
                Text(
                    if (isOnboarding) "Mulai Menggunakan Psikonutrisense 🚀" else "Simpan",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }

        if (existing != null && !isOnboarding) {
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedButton(
                onClick = { showDeleteConfirm = true },
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red),
                shape = RoundedCornerShape(28.dp),
                enabled = actionState !is UiState.Loading,
                modifier = Modifier.fillMaxWidth().height(54.dp)
            ) {
                Text("Hapus Data Anak", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }

    if (showDeleteConfirm) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirm = false },
            title = { Text("Hapus Data Anak?") },
            text = { Text("Data anak ini beserta riwayat pertumbuhan & imunisasinya akan dihapus. Tindakan ini tidak dapat dibatalkan.") },
            confirmButton = {
                TextButton(onClick = {
                    showDeleteConfirm = false
                    onDeleteClick()
                }) {
                    Text("Hapus", color = Color.Red, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteConfirm = false }) {
                    Text("Batal")
                }
            }
        )
    }
}
