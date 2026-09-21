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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.psikonutrisense.app.data.model.WellbeingScreening
import com.psikonutrisense.app.ui.UiState
import com.psikonutrisense.app.ui.components.ScaleSelector0to4
import com.psikonutrisense.app.ui.theme.*

@Composable
fun KondisiPsikososialScreen(
    actionState: UiState<String>,
    motherId: Int,
    existing: WellbeingScreening? = null,
    onNextClick: () -> Unit,
    onSaveClick: (WellbeingScreening) -> Unit
) {
    val context = LocalContext.current
    var cemas by remember(existing) { mutableStateOf(existing?.skorCemasBerlebihan ?: 0) }
    var lelah by remember(existing) { mutableStateOf(existing?.skorMudahLelah ?: 0) }
    var sedih by remember(existing) { mutableStateOf(existing?.skorSedihTanpaSebab ?: 0) }
    var didukung by remember(existing) { mutableStateOf(existing?.skorDidukungKeluargaUmum ?: 0) }

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
            "Kondisi Psikososial Ibu",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = PrimaryRose,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "Dalam 2 minggu terakhir",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = PrimaryRoseDark,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(24.dp))

        ScaleSelector0to4("Saya merasa cemas berlebihan", cemas, { cemas = it })
        Spacer(modifier = Modifier.height(20.dp))
        ScaleSelector0to4("Saya merasa mudah lelah", lelah, { lelah = it })
        Spacer(modifier = Modifier.height(20.dp))
        ScaleSelector0to4("Saya merasa sedih tanpa sebab", sedih, { sedih = it })
        Spacer(modifier = Modifier.height(20.dp))
        ScaleSelector0to4("Saya merasa didukung keluarga", didukung, { didukung = it })

        Spacer(modifier = Modifier.height(16.dp))
        Text("Keterangan:", fontSize = 12.sp, color = TextMuted)
        Text("0 = Tidak pernah      4 = Sangat sering", fontSize = 12.sp, color = TextMuted)

        Spacer(modifier = Modifier.height(28.dp))

        Button(
            onClick = {
                onSaveClick(
                    WellbeingScreening(
                        id = existing?.id ?: 0,
                        motherId = motherId,
                        frekuensiInteraksiBermain = existing?.frekuensiInteraksiBermain ?: 3,
                        pengasuhUtama = existing?.pengasuhUtama ?: "Ibu Kandung",
                        skorCemasBerlebihan = cemas,
                        skorMudahLelah = lelah,
                        skorSedihTanpaSebab = sedih,
                        skorDidukungKeluargaUmum = didukung,
                        dukunganEmosional = existing?.dukunganEmosional ?: 0,
                        dukunganInformasi = existing?.dukunganInformasi ?: 0,
                        dukunganPraktis = existing?.dukunganPraktis ?: 0,
                        kategoriKesejahteraan = existing?.kategoriKesejahteraan ?: "Tenang & Bahagia",
                        cukupInformasiGizi = existing?.cukupInformasiGizi ?: "Ya",
                        sumberInformasiTerpercaya = existing?.sumberInformasiTerpercaya ?: "Posyandu"
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
