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
import com.psikonutrisense.app.data.model.FoodGroupFrequency
import com.psikonutrisense.app.ui.UiState
import com.psikonutrisense.app.ui.components.DropdownField
import com.psikonutrisense.app.ui.theme.*
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val FREQ_OPTIONS = listOf("Tidak pernah", "1-2 kali", "2-3 kali", "3-4 kali", ">4 kali")

@Composable
fun AsupanPanganScreen(
    actionState: UiState<String>,
    childId: Int,
    existing: FoodGroupFrequency? = null,
    onNextClick: () -> Unit,
    onSaveClick: (FoodGroupFrequency) -> Unit
) {
    val context = LocalContext.current
    var nasiBubur by remember(existing) { mutableStateOf(existing?.nasiBubur?.takeIf { it.isNotBlank() } ?: "2-3 kali") }
    var laukHewani by remember(existing) { mutableStateOf(existing?.laukHewani?.takeIf { it.isNotBlank() } ?: "1-2 kali") }
    var laukNabati by remember(existing) { mutableStateOf(existing?.laukNabati?.takeIf { it.isNotBlank() } ?: "1-2 kali") }
    var sayur by remember(existing) { mutableStateOf(existing?.sayur?.takeIf { it.isNotBlank() } ?: "1-2 kali") }
    var buah by remember(existing) { mutableStateOf(existing?.buah?.takeIf { it.isNotBlank() } ?: "1-2 kali") }
    var susu by remember(existing) { mutableStateOf(existing?.susu?.takeIf { it.isNotBlank() } ?: "1-2 kali") }

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
            "Frekuensi Asupan (per hari)",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = PrimaryRose,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(24.dp))

        DropdownField("Nasi/Bubur", nasiBubur, FREQ_OPTIONS, { nasiBubur = it })
        Spacer(modifier = Modifier.height(16.dp))
        DropdownField("Lauk Hewani (telur, ikan, daging)", laukHewani, FREQ_OPTIONS, { laukHewani = it })
        Spacer(modifier = Modifier.height(16.dp))
        DropdownField("Lauk Nabati (tempe, tahu)", laukNabati, FREQ_OPTIONS, { laukNabati = it })
        Spacer(modifier = Modifier.height(16.dp))
        DropdownField("Sayur", sayur, FREQ_OPTIONS, { sayur = it })
        Spacer(modifier = Modifier.height(16.dp))
        DropdownField("Buah", buah, FREQ_OPTIONS, { buah = it })
        Spacer(modifier = Modifier.height(16.dp))
        DropdownField("Susu", susu, FREQ_OPTIONS, { susu = it })

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                onSaveClick(
                    FoodGroupFrequency(
                        id = existing?.id ?: 0,
                        childId = childId,
                        tanggalPencatatan = existing?.tanggalPencatatan?.takeIf { it.isNotBlank() } ?: today,
                        nasiBubur = nasiBubur,
                        laukHewani = laukHewani,
                        laukNabati = laukNabati,
                        sayur = sayur,
                        buah = buah,
                        susu = susu
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
