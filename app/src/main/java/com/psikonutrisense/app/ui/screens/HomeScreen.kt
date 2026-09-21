package com.psikonutrisense.app.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.psikonutrisense.app.data.model.*
import com.psikonutrisense.app.ui.UiState
import com.psikonutrisense.app.ui.theme.*

@Composable
fun HomeScreen(
    mothersState: UiState<List<Mother>> = UiState.Idle,
    childrenState: UiState<List<Child>> = UiState.Idle,
    growthState: UiState<List<GrowthRecord>> = UiState.Idle,
    immunizationState: UiState<List<VaccinationRecord>> = UiState.Idle,
    onNavigateToGrowthChart: () -> Unit = {},
    onNavigateToResep: () -> Unit = {},
    onNavigateToImunisasi: () -> Unit = {},
    onNavigateToPsikososial: () -> Unit = {},
    onNavigateToEdukasi: () -> Unit = {},
    onNavigateToDataIbu: () -> Unit = {},
    onNavigateToDataAnak: () -> Unit = {},
    onNavigateToAntropometri: () -> Unit = {},
    onNavigateToIntervensi: () -> Unit = {},
    onNavigateToAccount: () -> Unit = {}
) {
    var selectedPmtOption by remember { mutableStateOf("Habis") }
    var target1Checked by remember { mutableStateOf(true) }
    var target2Checked by remember { mutableStateOf(false) }
    var target3Checked by remember { mutableStateOf(false) }

    // Extract Mother data
    val mother = when (mothersState) {
        is UiState.Success -> mothersState.data.firstOrNull()
        else -> null
    }

    // Extract Child data
    val child = when (childrenState) {
        is UiState.Success -> childrenState.data.firstOrNull()
        else -> null
    }

    // Extract Growth Record
    val growthRecord = when (growthState) {
        is UiState.Success -> growthState.data.lastOrNull()
        else -> null
    }

    // Extract Next Immunization Record
    val nextImmunization = when (immunizationState) {
        is UiState.Success -> immunizationState.data.firstOrNull { it.status != "Sudah" } ?: immunizationState.data.firstOrNull()
        else -> null
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = BackgroundSoftPink
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // 1. Header Section
            HeaderSection(
                motherName = mother?.namaIbu,
                onProfileClick = onNavigateToAccount
            )

            // 2. Kondisi Ibu Banner
            KondisiIbuBanner(
                poin = mother?.poinIbuAktif ?: 1250,
                lencana = mother?.lencanaAktif ?: true,
                onClick = onNavigateToPsikososial
            )

            // 3. Profil Anak & Quick Access
            ProfilAnakSection(
                child = child,
                isLoading = childrenState is UiState.Loading,
                onNavigateToGrowthChart = onNavigateToGrowthChart,
                onNavigateToResep = onNavigateToResep,
                onNavigateToImunisasi = onNavigateToImunisasi,
                onNavigateToPsikososial = onNavigateToPsikososial,
                onNavigateToEdukasi = onNavigateToEdukasi,
                onNavigateToDataAnak = onNavigateToDataAnak
            )

            // 4. Modul Harian PMT
            ModulHarianPMTSection(
                selectedOption = selectedPmtOption,
                onOptionSelect = { selectedPmtOption = it },
                onScanQrClick = onNavigateToIntervensi
            )

            // 5. Target Minggu Ini
            TargetMingguIniSection(
                target1 = target1Checked,
                onTarget1Change = { target1Checked = it },
                target2 = target2Checked,
                onTarget2Change = { target2Checked = it },
                target3 = target3Checked,
                onTarget3Change = { target3Checked = it }
            )

            // 6. Status Gizi Card
            StatusGiziCard(
                growthRecord = growthRecord,
                isLoading = growthState is UiState.Loading,
                onClick = onNavigateToAntropometri
            )

            // 7. Lihat Grafik Pertumbuhan Button
            Button(
                onClick = onNavigateToGrowthChart,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(26.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ButtonPinkBg,
                    contentColor = ButtonPinkText
                )
            ) {
                Text(
                    text = "Lihat Grafik Pertumbuhan",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // 8. Imunisasi Section Card
            ImunisasiCard(
                vaccination = nextImmunization,
                isLoading = immunizationState is UiState.Loading,
                onClick = onNavigateToImunisasi
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun HeaderSection(
    motherName: String?,
    onProfileClick: () -> Unit
) {
    val displayName = if (!motherName.isNullOrBlank()) "Halo, $motherName! 👋" else "Halo, Ibunda! 👋"
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .clickable(onClick = onProfileClick)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = displayName,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDarkHeader
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Pantau tumbuh kembang\nsi Kecil setiap hari",
                fontSize = 14.sp,
                color = TextMuted,
                lineHeight = 18.sp
            )
        }

        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(1.dp, BorderLight, CircleShape)
                .clickable { onProfileClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.Person,
                contentDescription = "Profile Ibu",
                tint = TextDarkHeader,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun KondisiIbuBanner(
    poin: Int,
    lencana: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(CardLightPink),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Face,
                        contentDescription = "Kondisi Ibu",
                        tint = PrimaryRose,
                        modifier = Modifier.size(28.dp)
                    )
                }

                Column {
                    Text(
                        text = "KONDISI IBU",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextMuted,
                        letterSpacing = 0.5.sp
                    )
                    Text(
                        text = "Tenang & Bahagia",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryRoseDark
                    )
                }
            }

            Column(horizontalAlignment = Alignment.End) {
                if (lencana) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = BadgePinkBg
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "🥇 Lencana Ibu Aktif",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                }
                Text(
                    text = "$poin Poin",
                    fontSize = 11.sp,
                    color = TextMuted
                )
            }
        }
    }
}

@Composable
fun ProfilAnakSection(
    child: Child?,
    isLoading: Boolean,
    onNavigateToGrowthChart: () -> Unit,
    onNavigateToResep: () -> Unit,
    onNavigateToImunisasi: () -> Unit,
    onNavigateToPsikososial: () -> Unit,
    onNavigateToEdukasi: () -> Unit,
    onNavigateToDataAnak: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Profil Anak",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextDarkHeader
            )
            Text(
                text = "Edit",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = PrimaryRose,
                modifier = Modifier.clickable(onClick = onNavigateToDataAnak)
            )
        }

        // Child Profile Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onNavigateToDataAnak),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = CardPinkBg),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                val isBoy = child?.jenisKelamin?.lowercase() in listOf("l", "laki-laki")
                val avatarEmoji = if (child == null) "👶" else if (isBoy) "👦" else "👧"

                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFFFD5D1)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = avatarEmoji, fontSize = 28.sp)
                }

                Column {
                    if (isLoading) {
                        Text(
                            text = "Memuat data...",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextDarkTitle
                        )
                    } else if (child != null) {
                        Text(
                            text = child.namaBalita,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextDarkTitle
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = calculateAgeString(child.tanggalLahirBalita),
                            fontSize = 14.sp,
                            color = TextMuted
                        )
                    } else {
                        Text(
                            text = "Belum Ada Data Anak",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextDarkTitle
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Ketuk untuk mengisi data anak",
                            fontSize = 13.sp,
                            color = PrimaryRose
                        )
                    }
                }
            }
        }

        // Category Navigation Icons Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CategoryItem(icon = "🌱", label = "Tumbuh", onClick = onNavigateToGrowthChart)
            CategoryItem(icon = "🥩", label = "Makan", onClick = onNavigateToResep)
            CategoryItem(icon = "🛡️", label = "Imunisasi", onClick = onNavigateToImunisasi)
            CategoryItem(icon = "🧩", label = "Psikososial", onClick = onNavigateToPsikososial)
            CategoryItem(icon = "📚", label = "Edukasi", onClick = onNavigateToEdukasi)
        }
    }
}

private fun calculateAgeString(birthDateStr: String): String {
    if (birthDateStr.isBlank()) return "-"
    try {
        val parts = birthDateStr.split("-")
        if (parts.size == 3) {
            val year = parts[0].toIntOrNull() ?: return birthDateStr
            val month = parts[1].toIntOrNull() ?: return birthDateStr
            val day = parts[2].toIntOrNull() ?: return birthDateStr
            val now = java.util.Calendar.getInstance()
            val currentYear = now.get(java.util.Calendar.YEAR)
            val currentMonth = now.get(java.util.Calendar.MONTH) + 1
            
            var ageYears = currentYear - year
            var ageMonths = currentMonth - month
            if (ageMonths < 0) {
                ageYears -= 1
                ageMonths += 12
            }
            if (ageYears > 0) {
                return if (ageMonths > 0) "$ageYears tahun $ageMonths bulan" else "$ageYears tahun"
            } else {
                return "$ageMonths bulan"
            }
        }
    } catch (e: Exception) {
        // Fallback
    }
    return birthDateStr
}

@Composable
fun CategoryItem(icon: String, label: String, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Box(
            modifier = Modifier
                .size(52.dp)
                .clip(CircleShape)
                .background(Color.White)
                .border(1.dp, BorderLight, CircleShape)
                .clickable(onClick = onClick),
            contentAlignment = Alignment.Center
        ) {
            Text(text = icon, fontSize = 22.sp)
        }
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = TextDarkTitle
        )
    }
}

@Composable
fun ModulHarianPMTSection(
    selectedOption: String,
    onOptionSelect: (String) -> Unit,
    onScanQrClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Modul Harian PMT",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDarkHeader
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable(onClick = onScanQrClick)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.QrCodeScanner,
                        contentDescription = "Scan QR",
                        tint = PrimaryRose,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Scan QR",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryRose
                    )
                }
            }

            Text(
                text = "Konsumsi PMT Hari Ini:",
                fontSize = 12.sp,
                color = TextMuted
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("Habis", "Sebagian", "Tidak").forEach { option ->
                    val isSelected = option == selectedOption
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(40.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(if (isSelected) PrimaryRose else Color.White)
                            .border(
                                width = if (isSelected) 0.dp else 1.dp,
                                color = BorderLight,
                                shape = RoundedCornerShape(20.dp)
                            )
                            .clickable { onOptionSelect(option) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = option,
                            fontSize = 13.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) Color.White else TextDarkTitle
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TargetMingguIniSection(
    target1: Boolean,
    onTarget1Change: (Boolean) -> Unit,
    target2: Boolean,
    onTarget2Change: (Boolean) -> Unit,
    target3: Boolean,
    onTarget3Change: (Boolean) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(
            text = "Target Minggu Ini",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = TextDarkHeader
        )

        ChecklistItem(
            title = "Berikan Protein Hewani",
            isChecked = target1,
            onCheckedChange = onTarget1Change
        )
        ChecklistItem(
            title = "Stimulasi Main Bersama",
            isChecked = target2,
            onCheckedChange = onTarget2Change
        )
        ChecklistItem(
            title = "Hadir Posyandu",
            isChecked = target3,
            onCheckedChange = onTarget3Change
        )
    }
}

@Composable
fun ChecklistItem(
    title: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!isChecked) }
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(if (isChecked) PrimaryRose else Color.White)
                .border(1.dp, if (isChecked) PrimaryRose else BorderLight, RoundedCornerShape(6.dp)),
            contentAlignment = Alignment.Center
        ) {
            if (isChecked) {
                Icon(
                    imageVector = Icons.Outlined.Check,
                    contentDescription = "Checked",
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
            }
        }

        Text(
            text = title,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = TextDarkTitle
        )
    }
}

@Composable
fun StatusGiziCard(
    growthRecord: GrowthRecord?,
    isLoading: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = GreenStatusBg),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = "Status Gizi",
                    fontSize = 12.sp,
                    color = GreenStatusText,
                    fontWeight = FontWeight.Medium
                )

                if (isLoading) {
                    Text(
                        text = "Memuat...",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = GreenStatusText
                    )
                } else if (growthRecord != null) {
                    Text(
                        text = growthRecord.statusGizi,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = GreenStatusText
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "BB: ${growthRecord.beratBadanKg} kg | TB: ${growthRecord.tinggiBadanCm} cm",
                        fontSize = 13.sp,
                        color = GreenStatusText
                    )
                    Text(
                        text = "Z-Score BB/U: ${growthRecord.zscoreBbu} | TB/U: ${growthRecord.zscoreTbu}",
                        fontSize = 12.sp,
                        color = GreenStatusText
                    )
                } else {
                    Text(
                        text = "Belum Ada Catatan",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = GreenStatusText
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Ketuk untuk catat antropometri pertama",
                        fontSize = 12.sp,
                        color = GreenStatusText
                    )
                }
            }

            Box(
                modifier = Modifier.size(70.dp),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawCircle(
                        color = GreenStatusAccent,
                        radius = size.minDimension / 2 - 8,
                        style = Stroke(width = 12.dp.toPx())
                    )
                }
            }
        }
    }
}

@Composable
fun ImunisasiCard(
    vaccination: VaccinationRecord?,
    isLoading: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(ImunisasiBlueBg),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Vaccines,
                        contentDescription = "Imunisasi Icon",
                        tint = ImunisasiBlueIcon,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Text(
                    text = "Imunisasi",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDarkTitle
                )
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F9F9))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min)
                ) {
                    Box(
                        modifier = Modifier
                            .width(4.dp)
                            .fillMaxHeight()
                            .background(AccentYellow)
                    )

                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        if (isLoading) {
                            Text(
                                text = "Memuat jadwal imunisasi...",
                                fontSize = 14.sp,
                                color = TextMuted
                            )
                        } else if (vaccination != null) {
                            Text(
                                text = "Jadwal Berikutnya (${vaccination.tanggalJadwal}):",
                                fontSize = 12.sp,
                                color = TextMuted
                            )
                            Text(
                                text = vaccination.jenisImunisasi,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextDarkTitle
                            )
                        } else {
                            Text(
                                text = "Jadwal Imunisasi",
                                fontSize = 12.sp,
                                color = TextMuted
                            )
                            Text(
                                text = "Lihat Riwayat & Schedule Imunisasi",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextDarkTitle
                            )
                        }
                    }
                }
            }

            OutlinedButton(
                onClick = onClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp),
                shape = RoundedCornerShape(22.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, BorderLight)
            ) {
                Text(
                    text = "Detail & Reminder",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextDarkTitle
                )
            }
        }
    }
}
