package com.psikonutrisense.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.psikonutrisense.app.ui.theme.*

private val TAB_OPTIONS = listOf("Psikososial", "Gizi", "Aktivitas")

private data class IntervensiItem(val emoji: String, val title: String, val subtitle: String, val category: String)

private val ITEMS = listOf(
    IntervensiItem("🧑‍🤝‍🧑", "Konseling Psikososial", "Dukungan untuk dan keluarga", "Psikososial"),
    IntervensiItem("🥗", "Edukasi Gizi Seimbang", "Panduan gizi dan pangan lokal", "Gizi"),
    IntervensiItem("🧘", "Manajemen Stres", "Kelola stres dan emosi positif", "Psikososial"),
    IntervensiItem("👨‍👩‍👧", "Aktivitas Keluarga", "Aktivitas seru untuk tumbuh kembang anak", "Aktivitas")
)

@Composable
fun IntervensiScreen(
    onBackClick: () -> Unit
) {
    var selectedTab by remember { mutableStateOf("Gizi") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .background(BackgroundSoftPink)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali", tint = PrimaryRose)
            }
            Text("Intervensi", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = PrimaryRose)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(24.dp))
                    .padding(4.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                for (tab in TAB_OPTIONS) {
                    val isSelected = tab == selectedTab
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(20.dp))
                            .background(if (isSelected) ButtonPinkBg else Color.Transparent)
                            .clickable { selectedTab = tab }
                            .padding(vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            tab,
                            fontSize = 13.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) ButtonPinkText else TextMuted
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            for (item in ITEMS) {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .clickable { }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .clip(RoundedCornerShape(14.dp))
                                .background(CardPinkBg),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(item.emoji, fontSize = 28.sp)
                        }
                        Spacer(modifier = Modifier.width(14.dp))
                        Column {
                            Text(item.title, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = TextDarkTitle)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(item.subtitle, fontSize = 12.sp, color = TextMuted)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
