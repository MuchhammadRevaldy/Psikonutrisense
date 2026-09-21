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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.psikonutrisense.app.data.model.GrowthRecord
import com.psikonutrisense.app.ui.UiState
import com.psikonutrisense.app.ui.components.DatePickerField
import com.psikonutrisense.app.ui.theme.*

private val PILL_FIELD_SHAPE = RoundedCornerShape(28.dp)
private val STATUS_OPTIONS = listOf("Naik", "Tidak Naik", "Tidak Menimbang")

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
    var weight by remember(existing) { mutableStateOf(existing?.beratBadanKg?.takeIf { it != 0.0 }?.toString() ?: "") }
    var height by remember(existing) { mutableStateOf(existing?.tinggiBadanCm?.takeIf { it != 0.0 }?.toString() ?: "") }
    var lika by remember(existing) { mutableStateOf(existing?.lingkarKepalaCm?.takeIf { it != 0.0 }?.toString() ?: "") }
    var lila by remember(existing) { mutableStateOf(existing?.lilaCm?.takeIf { it != 0.0 }?.toString() ?: "") }
    var status by remember(existing) { mutableStateOf(existing?.statusPenimbangan?.takeIf { it.isNotBlank() } ?: "Naik") }

    LaunchedEffect(actionState) {
        if (actionState is UiState.Success) {
            Toast.makeText(context, actionState.data, Toast.LENGTH_SHORT).show()
            onBackClick()
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
        IconButton(onClick = onBackClick, modifier = Modifier.padding(bottom = 4.dp)) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali", tint = PrimaryRose)
        }

        Text(
            text = "Data Antropometri",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = PrimaryRose,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(24.dp))

        DatePickerField(label = "Tanggal Penimbangan", value = date, onDateSelected = { date = it }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(16.dp))

        Text("Berat Badan (kg) bulan ini", fontSize = 14.sp, color = TextDarkTitle)
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = weight,
            onValueChange = { weight = it },
            modifier = Modifier.fillMaxWidth(),
            shape = PILL_FIELD_SHAPE,
            colors = OutlinedTextFieldDefaults.colors(unfocusedContainerColor = Color.White, focusedContainerColor = Color.White, unfocusedBorderColor = Color.Transparent)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text("Panjang/Tinggi Badan (cm) bulan ini", fontSize = 14.sp, color = TextDarkTitle)
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = height,
            onValueChange = { height = it },
            modifier = Modifier.fillMaxWidth(),
            shape = PILL_FIELD_SHAPE,
            colors = OutlinedTextFieldDefaults.colors(unfocusedContainerColor = Color.White, focusedContainerColor = Color.White, unfocusedBorderColor = Color.Transparent)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text("Lingkar Kepala (cm)", fontSize = 14.sp, color = TextDarkTitle)
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = lika,
            onValueChange = { lika = it },
            modifier = Modifier.fillMaxWidth(),
            shape = PILL_FIELD_SHAPE,
            colors = OutlinedTextFieldDefaults.colors(unfocusedContainerColor = Color.White, focusedContainerColor = Color.White, unfocusedBorderColor = Color.Transparent)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text("Lingkar Lengan Atas / LiLA (cm)", fontSize = 14.sp, color = TextDarkTitle)
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = lila,
            onValueChange = { lila = it },
            modifier = Modifier.fillMaxWidth(),
            shape = PILL_FIELD_SHAPE,
            colors = OutlinedTextFieldDefaults.colors(unfocusedContainerColor = Color.White, focusedContainerColor = Color.White, unfocusedBorderColor = Color.Transparent)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text("Status penimbangan bulan ini", fontSize = 14.sp, color = TextDarkTitle)
        Spacer(modifier = Modifier.height(6.dp))
        Column {
            for (option in STATUS_OPTIONS) {
                Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                    RadioButton(selected = status == option, onClick = { status = option }, colors = RadioButtonDefaults.colors(selectedColor = PrimaryRose))
                    Text(option, fontSize = 14.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        Button(
            onClick = {
                if (date.isBlank()) {
                    Toast.makeText(context, "Mohon pilih Tanggal Penimbangan", Toast.LENGTH_SHORT).show()
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
                    statusPenimbangan = status,
                    statusGizi = existing?.statusGizi ?: "Normal",
                    zscoreBbu = existing?.zscoreBbu ?: 0.0,
                    zscoreTbu = existing?.zscoreTbu ?: 0.0
                )
                onSaveClick(record)
            },
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryRoseDark),
            shape = RoundedCornerShape(28.dp),
            enabled = actionState !is UiState.Loading,
            modifier = Modifier.fillMaxWidth().height(54.dp)
        ) {
            if (actionState is UiState.Loading) {
                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
            } else {
                Text("Simpan", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
        }
    }
}
