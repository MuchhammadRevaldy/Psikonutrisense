package com.psikonutrisense.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocalDining
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.psikonutrisense.app.data.model.LocalRecipe
import com.psikonutrisense.app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResepDetailScreen(
    recipe: LocalRecipe?,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(recipe?.namaResep ?: "Detail Resep", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Kembali")
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
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = ButtonPinkBg
                    ) {
                        Text(
                            text = "Rekomendasi Usia: ${recipe?.usiaRekomendasiBulan ?: "6-24 Bulan"}",
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            color = ButtonPinkText,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = recipe?.namaResep ?: "Resep Pangan Lokal",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryRose
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = recipe?.deskripsiGizi ?: "Nilai gizi seimbang untuk pertumbuhan balita.",
                        fontSize = 13.sp,
                        color = TextMuted
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "Bahan Utama Pangan Lokal",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDarkTitle
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = recipe?.bahanLokalUtama ?: "Bahan tidak tersedia",
                        fontSize = 14.sp,
                        color = TextDarkTitle
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "Langkah Pembuatan",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDarkTitle
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    val steps = recipe?.langkahMemasakJson
                    if (!steps.isNullOrEmpty()) {
                        steps.forEachIndexed { index, step ->
                            Text(
                                text = "${index + 1}. $step",
                                fontSize = 14.sp,
                                color = TextDarkTitle,
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }
                    } else {
                        Text(
                            text = "Petunjuk pembuatan tidak tersedia",
                            fontSize = 14.sp,
                            color = TextDarkTitle
                        )
                    }
                }
            }
        }
    }
}
