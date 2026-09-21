package com.psikonutrisense.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.psikonutrisense.app.ui.theme.*

@Composable
fun EdukasiDetailScreen(
    article: ArticleItem?,
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
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(CardPinkBg),
            contentAlignment = Alignment.Center
        ) {
            Text(article?.emoji ?: "📖", fontSize = 88.sp)

            IconButton(
                onClick = onBackClick,
                modifier = Modifier
                    .padding(16.dp)
                    .size(40.dp)
                    .background(Color.White, androidx.compose.foundation.shape.CircleShape)
                    .align(Alignment.TopStart)
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali", tint = PrimaryRose, modifier = Modifier.size(20.dp))
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .padding(horizontal = 20.dp, vertical = 24.dp)
        ) {
            Row {
                Surface(shape = RoundedCornerShape(16.dp), color = ButtonPinkBg) {
                    Text(
                        article?.category ?: "Edukasi",
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = ButtonPinkText
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.AccessTime, contentDescription = null, tint = TextMuted, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(article?.readTime ?: "3 menit baca", fontSize = 12.sp, color = TextMuted)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = article?.title ?: "Artikel Edukasi",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = TextDarkTitle,
                lineHeight = 28.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = article?.summary ?: "",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = PrimaryRoseDark,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            val paragraphs = article?.bodyParagraphs ?: listOf("Konten artikel tidak tersedia.")
            paragraphs.forEach { paragraph ->
                Text(
                    text = paragraph,
                    fontSize = 14.sp,
                    color = TextDarkTitle,
                    lineHeight = 22.sp,
                    modifier = Modifier.padding(bottom = 14.dp)
                )
            }
        }
    }
}
