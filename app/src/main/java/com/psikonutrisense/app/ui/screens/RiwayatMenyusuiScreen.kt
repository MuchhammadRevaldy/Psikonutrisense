package com.psikonutrisense.app.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.psikonutrisense.app.data.model.NutritionRecord
import com.psikonutrisense.app.ui.UiState
import com.psikonutrisense.app.ui.components.DropdownField
import com.psikonutrisense.app.ui.components.YaTidakRadioRow
import com.psikonutrisense.app.ui.theme.*

private val JENIS_MPASI_OPTIONS = listOf("Bubur Tim", "Makanan Lumat", "Makanan Keluarga")
private val USIA_MPASI_OPTIONS = (4..8).map { "$it bulan" }
private val FREKUENSI_OPTIONS = (1..5).map { "$it kali" }
private val KENDALA_OPTIONS = listOf("Tidak ada kendala", "Anak susah makan", "Keterbatasan bahan", "Keterbatasan waktu", "Lainnya")

@Composable
fun RiwayatMenyusuiScreen(
    actionState: UiState<String>,
    childId: Int,
    existing: NutritionRecord? = null,
    onNextClick: () -> Unit,
    onSaveClick: (NutritionRecord) -> Unit
) {
    val context = LocalContext.current
    var masihMenyusui by remember(existing) { mutableStateOf(existing?.masihMenyusui ?: true) }
    var usiaMpasi by remember(existing) { mutableStateOf("${existing?.usiaMulaiMpasiBulan ?: 6} bulan") }
    var jenisMpasi by remember(existing) { mutableStateOf(existing?.jenisMpasiJson?.toSet() ?: emptySet()) }
    var frekuensiMakan by remember(existing) { mutableStateOf("${existing?.frekuensiMakanPerHari ?: 3} kali") }
    var frekuensiCamilan by remember(existing) { mutableStateOf("${existing?.frekuensiCamilanPerHari ?: 2} kali") }
    var kendala by remember(existing) { mutableStateOf(existing?.kendalaPemberianMakan?.takeIf { it.isNotBlank() } ?: "Tidak ada kendala") }

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
            "Menyusui & MPASI",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = PrimaryRose,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(24.dp))

        YaTidakRadioRow(
            label = "Apakah anak masih disusui?",
            value = masihMenyusui,
            onChange = { masihMenyusui = it }
        )
        Spacer(modifier = Modifier.height(20.dp))

        DropdownField(
            label = "Usia mulai MPASI",
            selectedValue = usiaMpasi,
            options = USIA_MPASI_OPTIONS,
            onValueSelected = { usiaMpasi = it }
        )
        Spacer(modifier = Modifier.height(20.dp))

        Text("Jenis MPASI yang diberikan", fontSize = 14.sp, color = TextDarkTitle)
        Spacer(modifier = Modifier.height(8.dp))
        Column {
            for (option in JENIS_MPASI_OPTIONS) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = jenisMpasi.contains(option),
                        onCheckedChange = { checked ->
                            jenisMpasi = if (checked) jenisMpasi + option else jenisMpasi - option
                        },
                        colors = CheckboxDefaults.colors(checkedColor = PrimaryRose)
                    )
                    Text(option, fontSize = 14.sp)
                }
            }
        }
        Spacer(modifier = Modifier.height(12.dp))

        DropdownField(
            label = "Frekuensi makan utama per hari",
            selectedValue = frekuensiMakan,
            options = FREKUENSI_OPTIONS,
            onValueSelected = { frekuensiMakan = it }
        )
        Spacer(modifier = Modifier.height(20.dp))

        DropdownField(
            label = "Frekuensi camilan per hari",
            selectedValue = frekuensiCamilan,
            options = FREKUENSI_OPTIONS,
            onValueSelected = { frekuensiCamilan = it }
        )
        Spacer(modifier = Modifier.height(20.dp))

        DropdownField(
            label = "Kendala utama pemberian makan anak",
            selectedValue = kendala,
            options = KENDALA_OPTIONS,
            onValueSelected = { kendala = it }
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                onSaveClick(
                    NutritionRecord(
                        id = existing?.id ?: 0,
                        childId = childId,
                        asiEksklusif = existing?.asiEksklusif ?: true,
                        masihMenyusui = masihMenyusui,
                        usiaMulaiMpasiBulan = usiaMpasi.removeSuffix(" bulan").toIntOrNull() ?: 6,
                        jenisMpasiJson = jenisMpasi.toList(),
                        frekuensiMakanPerHari = frekuensiMakan.removeSuffix(" kali").toIntOrNull() ?: 3,
                        frekuensiCamilanPerHari = frekuensiCamilan.removeSuffix(" kali").toIntOrNull() ?: 2,
                        recall24jamMenu = existing?.recall24jamMenu,
                        kendalaPemberianMakan = kendala
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
