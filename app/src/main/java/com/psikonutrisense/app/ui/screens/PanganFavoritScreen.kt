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
import com.psikonutrisense.app.data.model.FavoriteLocalFood
import com.psikonutrisense.app.ui.UiState
import com.psikonutrisense.app.ui.theme.*

private val BAHAN_OPTIONS = listOf("Daun Kelor", "Jagung", "Ubi Jalar", "Tahu/Tempe", "Ikan (Nila/Lele)", "Kacang Hijau")

@Composable
fun PanganFavoritScreen(
    actionState: UiState<String>,
    childId: Int,
    existing: FavoriteLocalFood? = null,
    onNextClick: () -> Unit,
    onSaveClick: (FavoriteLocalFood) -> Unit
) {
    val context = LocalContext.current
    var selected by remember(existing) { mutableStateOf(existing?.bahanJson?.filter { it in BAHAN_OPTIONS }?.toSet() ?: BAHAN_OPTIONS.toSet()) }
    var lainnyaChecked by remember(existing) { mutableStateOf(!existing?.bahanLainnya.isNullOrBlank()) }
    var lainnyaText by remember(existing) { mutableStateOf(existing?.bahanLainnya ?: "") }

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
            "Bahan Makan Favorit",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = PrimaryRose,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            "Pilih bahan pangan lokal yang sering dikonsumsi keluarga",
            fontSize = 14.sp,
            color = TextMuted,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(20.dp))

        for (option in BAHAN_OPTIONS) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = selected.contains(option),
                    onCheckedChange = { checked -> selected = if (checked) selected + option else selected - option },
                    colors = CheckboxDefaults.colors(checkedColor = PrimaryRose)
                )
                Text(option, fontSize = 15.sp)
            }
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = lainnyaChecked,
                onCheckedChange = { lainnyaChecked = it },
                colors = CheckboxDefaults.colors(checkedColor = PrimaryRose)
            )
            Text("Lainnya", fontSize = 15.sp)
        }

        if (lainnyaChecked) {
            Spacer(modifier = Modifier.height(8.dp))
            Text("Sebutkan (jika ada)", fontSize = 13.sp, color = TextMuted)
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = lainnyaText,
                onValueChange = { lainnyaText = it },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                onSaveClick(
                    FavoriteLocalFood(
                        id = existing?.id ?: 0,
                        childId = childId,
                        bahanJson = selected.toList(),
                        bahanLainnya = if (lainnyaChecked) lainnyaText.ifBlank { null } else null
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
