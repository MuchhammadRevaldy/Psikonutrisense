package com.psikonutrisense.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.psikonutrisense.app.data.model.LocalRecipe
import com.psikonutrisense.app.ui.UiState
import com.psikonutrisense.app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResepListScreen(
    recipesState: UiState<List<LocalRecipe>>,
    onBackClick: () -> Unit,
    onRecipeClick: (LocalRecipe) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Resep Pangan Lokal PMT", fontWeight = FontWeight.Bold) },
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
            Text(
                text = "Resep Bergizi Kaya Protein Hewani",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TextDarkTitle
            )

            Spacer(modifier = Modifier.height(12.dp))

            val sampleRecipes = listOf(
                LocalRecipe(
                    id = 1,
                    namaResep = "Nasi Tim Telur Bayam Ikan Kembung",
                    usiaRekomendasiBulan = "6-11 Bulan",
                    bahanLokalUtama = "Nasi, Telur Ayam, Ikan Kembung, Bayam, Minyak Kelapa",
                    deskripsiGizi = "Tinggi protein hewani dan asam lemak omega-3",
                    langkahMemasakJson = listOf(
                        "Kukus nasi dan ikan kembung.",
                        "Rebus bayam sebentar.",
                        "Campurkan telur dan haluskan semua bahan."
                    )
                ),
                LocalRecipe(
                    id = 2,
                    namaResep = "Bubur Singkong Daging Ayam Kampung",
                    usiaRekomendasiBulan = "12-23 Bulan",
                    bahanLokalUtama = "Singkong Parut, Daging Ayam Cincang, Santan, Daun Salam",
                    deskripsiGizi = "Kaya energi dan kalori dari santan murni",
                    langkahMemasakJson = listOf(
                        "Rebus santan dan daging ayam.",
                        "Masukkan singkong parut, aduk hingga kental."
                    )
                )
            )

            when (recipesState) {
                is UiState.Loading -> {
                    Box(modifier = Modifier.fillMaxWidth().height(120.dp), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = PrimaryRose)
                    }
                }
                is UiState.Success -> {
                    val recipes = if (recipesState.data.isEmpty()) sampleRecipes else recipesState.data
                    for (recipe in recipes) {
                        RecipeItemCard(recipe = recipe, onClick = { onRecipeClick(recipe) })
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
                is UiState.Error -> {
                    for (recipe in sampleRecipes) {
                        RecipeItemCard(recipe = recipe, onClick = { onRecipeClick(recipe) })
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
                else -> {
                    for (recipe in sampleRecipes) {
                        RecipeItemCard(recipe = recipe, onClick = { onRecipeClick(recipe) })
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun RecipeItemCard(
    recipe: LocalRecipe,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = CardPinkBg,
                modifier = Modifier.size(56.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.Restaurant, contentDescription = null, tint = PrimaryRose)
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = ButtonPinkBg
                ) {
                    Text(
                        text = recipe.usiaRekomendasiBulan.ifEmpty { "MPASI" },
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = ButtonPinkText,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = recipe.namaResep,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDarkTitle
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = recipe.bahanLokalUtama,
                    fontSize = 12.sp,
                    color = TextMuted,
                    maxLines = 1
                )
            }
        }
    }
}
