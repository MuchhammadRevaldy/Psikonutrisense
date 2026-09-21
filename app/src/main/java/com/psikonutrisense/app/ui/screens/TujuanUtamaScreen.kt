package com.psikonutrisense.app.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import com.psikonutrisense.app.data.model.MainGoal
import com.psikonutrisense.app.ui.UiState
import com.psikonutrisense.app.ui.components.DropdownField
import com.psikonutrisense.app.ui.theme.*

private val TUJUAN_OPTIONS = listOf(
    "Anak sehat & tidak stunting",
    "Berat badan anak naik optimal",
    "Ibu lebih tenang & percaya diri mengasuh",
    "Keluarga lebih paham gizi seimbang"
)

@Composable
fun TujuanUtamaScreen(
    actionState: UiState<String>,
    motherId: Int,
    existing: MainGoal? = null,
    onFinishClick: () -> Unit,
    onSaveClick: (MainGoal) -> Unit
) {
    val context = LocalContext.current
    var tujuan by remember(existing) { mutableStateOf(existing?.tujuanUtama?.takeIf { it.isNotBlank() } ?: TUJUAN_OPTIONS.first()) }
    var harapan by remember(existing) { mutableStateOf(existing?.harapanUntukAnak ?: "") }
    var bersedia by remember(existing) { mutableStateOf(existing?.bersediaIkutIntervensi ?: true) }

    LaunchedEffect(actionState) {
        if (actionState is UiState.Success) {
            Toast.makeText(context, "Terima kasih! Profil kesehatan lengkap sudah tersimpan.", Toast.LENGTH_LONG).show()
            onFinishClick()
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
            "Tujuan Utama",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = PrimaryRose,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(24.dp))

        DropdownField(
            label = "Tujuan Utama",
            selectedValue = tujuan,
            options = TUJUAN_OPTIONS,
            onValueSelected = { tujuan = it }
        )
        Spacer(modifier = Modifier.height(20.dp))

        Text("Harapan untuk si Kecil", fontSize = 14.sp, color = TextDarkTitle)
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = harapan,
            onValueChange = { harapan = it },
            placeholder = { Text("Anak tumbuh sehat, cerdas, dan bahagia") },
            modifier = Modifier.fillMaxWidth().height(110.dp),
            shape = RoundedCornerShape(14.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))

        Text("Bersedia mengikuti program intervensi Psikonutrisense?", fontSize = 14.sp, color = TextDarkTitle)
        Spacer(modifier = Modifier.height(8.dp))
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { bersedia = true }
            ) {
                RadioButton(selected = bersedia, onClick = { bersedia = true }, colors = RadioButtonDefaults.colors(selectedColor = PrimaryRose))
                Text("Ya, saya bersedia", fontSize = 14.sp)
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { bersedia = false }
            ) {
                RadioButton(selected = !bersedia, onClick = { bersedia = false }, colors = RadioButtonDefaults.colors(selectedColor = PrimaryRose))
                Text("Nanti dulu", fontSize = 14.sp)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                onSaveClick(
                    MainGoal(
                        id = existing?.id ?: 0,
                        motherId = motherId,
                        tujuanUtama = tujuan,
                        harapanUntukAnak = harapan.ifBlank { null },
                        bersediaIkutIntervensi = bersedia
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
