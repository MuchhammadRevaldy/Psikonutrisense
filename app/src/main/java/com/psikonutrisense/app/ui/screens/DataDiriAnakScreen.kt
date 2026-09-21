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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.psikonutrisense.app.data.model.Child
import com.psikonutrisense.app.ui.UiState
import com.psikonutrisense.app.ui.components.DatePickerField
import com.psikonutrisense.app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
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

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (isOnboarding) "Langkah 2: Data Diri Anak" else "Form Data Diri Anak",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    if (!isOnboarding) {
                        IconButton(onClick = onBackClick) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali")
                        }
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
            // Onboarding progress indicator
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
                        Text(
                            text = "Hampir selesai! 👶",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryRoseDark
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Isi data anak Anda untuk melanjutkan",
                            fontSize = 13.sp,
                            color = TextMuted
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        LinearProgressIndicator(
                            progress = { 1.0f },
                            modifier = Modifier.fillMaxWidth().height(6.dp),
                            color = PrimaryRose,
                            trackColor = BorderLight
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Langkah 2 dari 2",
                            fontSize = 11.sp,
                            color = TextMuted
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text("Identitas Demografi Anak", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = PrimaryRose)
                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Nama Lengkap Anak *") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    DatePickerField(
                        label = "Tanggal Lahir Anak *",
                        value = dob,
                        onDateSelected = { dob = it },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    Text("Jenis Kelamin *", fontSize = 13.sp, color = TextMuted)
                    Row(modifier = Modifier.fillMaxWidth()) {
                        FilterChip(
                            selected = gender == "L",
                            onClick = { gender = "L" },
                            label = { Text("Laki-laki") },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = ButtonPinkBg,
                                selectedLabelColor = ButtonPinkText
                            )
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        FilterChip(
                            selected = gender == "P",
                            onClick = { gender = "P" },
                            label = { Text("Perempuan") },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = ButtonPinkBg,
                                selectedLabelColor = ButtonPinkText
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = birthWeight,
                            onValueChange = { birthWeight = it },
                            label = { Text("Berat Lahir (kg)") },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        OutlinedTextField(
                            value = birthHeight,
                            onValueChange = { birthHeight = it },
                            label = { Text("Panjang Lahir (cm)") },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

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
                                jumlahSaudara = existing?.jumlahSaudara ?: 0,
                                bbLahirKg = birthWeight.toDoubleOrNull(),
                                pbLahirCm = birthHeight.toDoubleOrNull(),
                                lahirPrematur = existing?.lahirPrematur ?: false
                            )
                            onSaveClick(child)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryRose),
                        shape = RoundedCornerShape(12.dp),
                        enabled = actionState !is UiState.Loading,
                        modifier = Modifier.fillMaxWidth().height(50.dp)
                    ) {
                        if (actionState is UiState.Loading) {
                            CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                        } else {
                            Text(
                                if (isOnboarding) "Mulai Menggunakan Psikonutrisense 🚀" else "Simpan Data Anak",
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
                            shape = RoundedCornerShape(12.dp),
                            enabled = actionState !is UiState.Loading,
                            modifier = Modifier.fillMaxWidth().height(50.dp)
                        ) {
                            Text("Hapus Data Anak", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
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
