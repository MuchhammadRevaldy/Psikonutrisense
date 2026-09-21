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
import com.psikonutrisense.app.data.model.Mother
import com.psikonutrisense.app.ui.UiState
import com.psikonutrisense.app.ui.components.DatePickerField
import com.psikonutrisense.app.ui.components.DropdownField
import com.psikonutrisense.app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataDiriIbuScreen(
    actionState: UiState<String>,
    onBackClick: () -> Unit,
    onSaveClick: (Mother) -> Unit,
    existing: Mother? = null,
    onDeleteClick: () -> Unit = {},
    isOnboarding: Boolean = false,
    onOnboardingComplete: () -> Unit = {}
) {
    val context = LocalContext.current
    var name by remember(existing) { mutableStateOf(existing?.namaIbu ?: "") }
    var nik by remember(existing) { mutableStateOf(existing?.nikIbu ?: "") }
    var dob by remember(existing) { mutableStateOf(existing?.tanggalLahirIbu ?: "") }
    var address by remember(existing) { mutableStateOf(existing?.alamatDusunRtRw ?: "") }
    var phone by remember(existing) { mutableStateOf(existing?.noHpWhatsapp ?: "") }
    var education by remember(existing) { mutableStateOf(existing?.pendidikanTerakhir?.takeIf { it.isNotBlank() } ?: "SMA / SMK / Sederajat") }
    var job by remember(existing) { mutableStateOf(existing?.pekerjaan ?: "") }
    var showDeleteConfirm by remember { mutableStateOf(false) }

    val educationOptions = remember {
        listOf(
            "Tidak / Belum Sekolah",
            "SD / Sederajat",
            "SMP / Sederajat",
            "SMA / SMK / Sederajat",
            "D3 / Sarjana Muda",
            "S1 / Sarjana",
            "S2 / Magister",
            "S3 / Doktor"
        )
    }

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
                        if (isOnboarding) "Langkah 1: Data Diri Ibu" else "Form Data Diri Ibu",
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
                            text = "Selamat datang di Psikonutrisense! 🎉",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryRoseDark
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Lengkapi data diri Anda untuk memulai",
                            fontSize = 13.sp,
                            color = TextMuted
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        LinearProgressIndicator(
                            progress = { 0.5f },
                            modifier = Modifier.fillMaxWidth().height(6.dp),
                            color = PrimaryRose,
                            trackColor = BorderLight
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Langkah 1 dari 2",
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
                    Text("Informasi Demografi & Kesehatan Ibu", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = PrimaryRose)
                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Nama Lengkap Ibu *") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = nik,
                        onValueChange = { if (it.length <= 16) nik = it },
                        label = { Text("NIK *") },
                        placeholder = { Text("Masukkan NIK Ibu") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    DatePickerField(
                        label = "Tanggal Lahir Ibu *",
                        value = dob,
                        onDateSelected = { dob = it },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = phone,
                        onValueChange = { phone = it },
                        label = { Text("No. HP / WA *") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = address,
                        onValueChange = { address = it },
                        label = { Text("Alamat Lengkap / Dusun RT RW *") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    DropdownField(
                        label = "Pendidikan Terakhir *",
                        selectedValue = education,
                        options = educationOptions,
                        onValueSelected = { education = it }
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = job,
                        onValueChange = { job = it },
                        label = { Text("Pekerjaan *") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            if (name.isBlank() || nik.isBlank() || dob.isBlank() || address.isBlank() || phone.isBlank() || job.isBlank()) {
                                Toast.makeText(context, "Mohon lengkapi seluruh kolom bertanda *", Toast.LENGTH_SHORT).show()
                                return@Button
                            }
                            val mother = Mother(
                                id = existing?.id ?: 0,
                                userId = existing?.userId,
                                namaIbu = name,
                                nikIbu = nik,
                                tanggalLahirIbu = dob,
                                alamatDusunRtRw = address,
                                noHpWhatsapp = phone,
                                pendidikanTerakhir = education,
                                pekerjaan = job,
                                poinIbuAktif = existing?.poinIbuAktif ?: 1250,
                                lencanaAktif = existing?.lencanaAktif ?: true
                            )
                            onSaveClick(mother)
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
                                if (isOnboarding) "Lanjutkan →" else "Simpan Data Ibu",
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
                            Text("Hapus Data Ibu", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }

    if (showDeleteConfirm) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirm = false },
            title = { Text("Hapus Data Ibu?") },
            text = { Text("Data ibu ini akan dihapus secara permanen. Tindakan ini tidak dapat dibatalkan.") },
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
