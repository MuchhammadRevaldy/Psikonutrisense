package com.psikonutrisense.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.psikonutrisense.app.data.model.LocalRecipe
import com.psikonutrisense.app.ui.theme.*

@Composable
fun ResepDetailScreen(
    recipe: LocalRecipe?,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .background(BackgroundSoftPink)
            .verticalScroll(rememberScrollState())
    ) {
        Box(modifier = Modifier.fillMaxWidth().height(260.dp)) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(listOf(GreenStatusAccent.copy(alpha = 0.5f), CardPinkBg))
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text("🥣", fontSize = 96.sp)
            }

            IconButton(
                onClick = onBackClick,
                modifier = Modifier
                    .padding(16.dp)
                    .size(40.dp)
                    .background(Color.White, CircleShape)
                    .align(Alignment.TopStart)
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali", tint = PrimaryRose, modifier = Modifier.size(20.dp))
            }

            IconButton(
                onClick = { },
                modifier = Modifier
                    .padding(16.dp)
                    .size(40.dp)
                    .background(Color.White, CircleShape)
                    .align(Alignment.TopEnd)
            ) {
                Icon(Icons.Default.Share, contentDescription = "Bagikan", tint = PrimaryRose, modifier = Modifier.size(20.dp))
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .padding(horizontal = 20.dp, vertical = 20.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(40.dp)
                    .height(4.dp)
                    .align(Alignment.CenterHorizontally)
                    .background(BorderLight, RoundedCornerShape(2.dp))
            )
            Spacer(modifier = Modifier.height(16.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Surface(shape = RoundedCornerShape(16.dp), color = CardLightPink) {
                    Text(
                        recipe?.usiaRekomendasiBulan?.takeIf { it.isNotBlank() } ?: "6+ Bulan",
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryRoseDark
                    )
                }
                Surface(shape = RoundedCornerShape(16.dp), color = ImunisasiBlueBg) {
                    Text(
                        "Tinggi Gizi",
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = ImunisasiBlueIcon
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = recipe?.namaResep ?: "Resep Pangan Lokal",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = TextDarkTitle
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = recipe?.deskripsiGizi ?: "Nilai gizi seimbang untuk pertumbuhan balita, dibuat dari bahan pangan lokal yang mudah didapat.",
                fontSize = 14.sp,
                color = TextMuted,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                RecipeStat(Icons.Default.AccessTime, "25 Menit", CardLightPink)
                RecipeStat(Icons.Default.Restaurant, "1 Porsi", ImunisasiBlueBg)
                RecipeStat(Icons.Default.LocalFireDepartment, "110 kkal", GreenStatusBg)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Surface(
                shape = RoundedCornerShape(16.dp),
                color = CardPinkBg,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Bahan-bahan", fontSize = 17.sp, fontWeight = FontWeight.Bold, color = TextDarkTitle)
                    Spacer(modifier = Modifier.height(12.dp))
                    val ingredients = recipe?.bahanLokalUtama?.split(",")?.map { it.trim() }?.filter { it.isNotBlank() }
                        ?: listOf("Bahan tidak tersedia")
                    ingredients.forEachIndexed { index, item ->
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 6.dp)) {
                            Box(
                                modifier = Modifier.size(28.dp).background(Color.White, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(listOf("🌿", "🐟", "🌾", "💧")[index % 4], fontSize = 14.sp)
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(item, fontSize = 14.sp, color = TextDarkTitle)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text("Langkah Pembuatan", fontSize = 17.sp, fontWeight = FontWeight.Bold, color = TextDarkTitle)
            Spacer(modifier = Modifier.height(12.dp))

            val steps = recipe?.langkahMemasakJson?.takeIf { it.isNotEmpty() } ?: listOf("Petunjuk pembuatan tidak tersedia")
            steps.forEachIndexed { index, step ->
                StepRow(number = index + 1, text = step, isLast = index == steps.lastIndex)
            }
        }
    }
}

@Composable
private fun RecipeStat(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, bg: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier.size(56.dp).background(bg, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = PrimaryRoseDark, modifier = Modifier.size(22.dp))
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(label, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = TextDarkTitle)
    }
}

@Composable
private fun StepRow(number: Int, text: String, isLast: Boolean) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier.size(28.dp).background(if (number % 2 == 1) PrimaryRoseDark else PrimaryRoseLight, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(number.toString(), color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
            }
            if (!isLast) {
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .height(48.dp)
                        .background(BorderLight)
                )
            }
        }
        Spacer(modifier = Modifier.width(14.dp))
        Text(
            text,
            fontSize = 14.sp,
            color = TextDarkTitle,
            lineHeight = 20.sp,
            modifier = Modifier.padding(bottom = 20.dp)
        )
    }
}
