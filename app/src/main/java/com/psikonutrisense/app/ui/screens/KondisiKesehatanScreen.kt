package com.psikonutrisense.app.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.psikonutrisense.app.data.model.HealthCondition
import com.psikonutrisense.app.ui.UiState
import com.psikonutrisense.app.ui.components.DropdownField
import com.psikonutrisense.app.ui.components.YaTidakRadioRow
import com.psikonutrisense.app.ui.theme.*

@Composable
fun KondisiKesehatanScreen(
    actionState: UiState<String>,
    childId: Int,
    existing: HealthCondition? = null,
    onNextClick: () -> Unit,
    onSaveClick: (HealthCondition) -> Unit
) {
    val context = LocalContext.current
    var pernahDirawat by remember(existing) { mutableStateOf(existing?.pernahDirawatRs ?: false) }
    var seringSakit by remember(existing) { mutableStateOf(existing?.seringSakit3bulan ?: false) }
    var alergi by remember(existing) { mutableStateOf(existing?.alergiMakananObat ?: false) }
    var keteranganAlergi by remember(existing) { mutableStateOf(existing?.keteranganAlergi ?: "") }
    var statusImunisasi by remember(existing) { mutableStateOf(existing?.statusImunisasiRingkasan?.takeIf { it.isNotBlank() } ?: "Lengkap") }

    LaunchedEffect(actionState) {
        if (actionState is UiState.Success) {
            Toast.makeText(context, actionState.data, Toast.LENGTH_SHORT).show()
            onNextClick()
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
        Text(
            text = "Kondisi Kesehatan",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = PrimaryRose,
            modifier = Modifier.fillMaxWidth(),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
        Spacer(modifier = Modifier.height(24.dp))

        YaTidakRadioRow(
            label = "Apakah anak pernah dirawat di rumah sakit?",
            value = pernahDirawat,
            onChange = { pernahDirawat = it }
        )
        Spacer(modifier = Modifier.height(20.dp))

        YaTidakRadioRow(
            label = "Apakah anak sering sakit dalam 3 bulan terakhir?",
            value = seringSakit,
            onChange = { seringSakit = it }
        )
        Spacer(modifier = Modifier.height(20.dp))

        YaTidakRadioRow(
            label = "Apakah anak memiliki alergi makanan/obat?",
            value = alergi,
            onChange = { alergi = it }
        )

        if (alergi) {
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = keteranganAlergi,
                onValueChange = { keteranganAlergi = it },
                label = { Text("Sebutkan alergi apa saja") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        DropdownField(
            label = "Status Imunisasi",
            selectedValue = statusImunisasi,
            options = listOf("Lengkap", "Belum Lengkap", "Tidak Diimunisasi"),
            onValueSelected = { statusImunisasi = it }
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                onSaveClick(
                    HealthCondition(
                        id = existing?.id ?: 0,
                        childId = childId,
                        pernahDirawatRs = pernahDirawat,
                        seringSakit3bulan = seringSakit,
                        alergiMakananObat = alergi,
                        keteranganAlergi = if (alergi) keteranganAlergi.ifBlank { null } else null,
                        statusImunisasiRingkasan = statusImunisasi
                    )
                )
            },
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryRose),
            shape = RoundedCornerShape(26.dp),
            enabled = actionState !is UiState.Loading,
            modifier = Modifier.fillMaxWidth().height(52.dp)
        ) {
            if (actionState is UiState.Loading) {
                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
            } else {
                Text("Simpan", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
        }
    }
}
