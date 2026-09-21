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
import com.psikonutrisense.app.data.model.LocalRecipe
import com.psikonutrisense.app.ui.UiState
import com.psikonutrisense.app.ui.theme.*

private val TAB_OPTIONS = listOf("Semua", "MPASI", "Balita", "Keluarga")

/** Recipes don't carry an explicit category from the backend, so it's derived from the age-recommendation text. */
private fun recipeCategory(recipe: LocalRecipe): String = when {
    recipe.usiaRekomendasiBulan.contains("bulan", ignoreCase = true) -> "MPASI"
    recipe.usiaRekomendasiBulan.contains("tahun", ignoreCase = true) -> "Balita"
    recipe.usiaRekomendasiBulan.contains("keluarga", ignoreCase = true) -> "Keluarga"
    else -> "Keluarga"
}

private data class DummyRecipe(val emoji: String, val recipe: LocalRecipe)

private val SAMPLE_RECIPES = listOf(
    DummyRecipe("🥣", LocalRecipe(
        id = 1,
        namaResep = "Bubur Kelor & Ikan Nila",
        usiaRekomendasiBulan = "6-8 Bulan",
        bahanLokalUtama = "Daun Kelor, Ikan Nila, Beras Putih, Kaldu Ayam",
        deskripsiGizi = "Tinggi protein, zat besi, vitamin A",
        langkahMemasakJson = listOf(
            "Rebus beras dengan kaldu ayam hingga menjadi bubur.",
            "Kukus ikan nila, suwir halus tanpa duri.",
            "Campurkan bubur, suwiran ikan, dan daun kelor cincang. Masak sebentar hingga layu."
        )
    )),
    DummyRecipe("🍚", LocalRecipe(
        id = 2,
        namaResep = "Nasi Tim Telur Bayam Ikan Kembung",
        usiaRekomendasiBulan = "6-11 Bulan",
        bahanLokalUtama = "Nasi, Telur Ayam, Ikan Kembung, Bayam, Minyak Kelapa",
        deskripsiGizi = "Tinggi protein hewani dan asam lemak omega-3",
        langkahMemasakJson = listOf(
            "Kukus nasi dan ikan kembung hingga matang.",
            "Rebus bayam sebentar, tiriskan.",
            "Haluskan semua bahan bersama telur rebus sesuai tekstur usia anak."
        )
    )),
    DummyRecipe("🥣", LocalRecipe(
        id = 3,
        namaResep = "Puree Kelor Lele Ngeposari",
        usiaRekomendasiBulan = "6-8 Bulan",
        bahanLokalUtama = "Daun Kelor, Ikan Lele, Telur",
        deskripsiGizi = "Kaya protein hewani, zat besi & vitamin A untuk cegah stunting",
        langkahMemasakJson = listOf(
            "Kukus ikan lele dan ambil dagingnya tanpa duri.",
            "Rebus daun kelor sebentar.",
            "Haluskan bersama nasi tim dan telur rebus."
        )
    )),
    DummyRecipe("🍲", LocalRecipe(
        id = 4,
        namaResep = "Sup Tim Tempe Telur Kelor",
        usiaRekomendasiBulan = "9-11 Bulan",
        bahanLokalUtama = "Tempe, Telur Ayam, Daun Kelor",
        deskripsiGizi = "Tinggi kalori dan lemak sehat untuk optimalkan berat badan",
        langkahMemasakJson = listOf(
            "Cincang halus tempe dan telur.",
            "Tumis dengan sedikit minyak kelapa.",
            "Tambahkan kaldu dan daun kelor cincang, masak hingga matang."
        )
    )),
    DummyRecipe("🍢", LocalRecipe(
        id = 5,
        namaResep = "Perkedel Tempe + Wortel",
        usiaRekomendasiBulan = "1-5 tahun",
        bahanLokalUtama = "Tempe, Wortel, Telur, Tepung Terigu",
        deskripsiGizi = "Sumber protein nabati dan serat",
        langkahMemasakJson = listOf(
            "Haluskan tempe, campur dengan wortel parut dan telur.",
            "Bentuk bulat pipih, goreng dengan sedikit minyak hingga kecokelatan."
        )
    )),
    DummyRecipe("🍛", LocalRecipe(
        id = 6,
        namaResep = "Bubur Singkong Daging Ayam Kampung",
        usiaRekomendasiBulan = "1-5 tahun",
        bahanLokalUtama = "Singkong Parut, Daging Ayam Cincang, Santan, Daun Salam",
        deskripsiGizi = "Kaya energi dan kalori dari santan murni",
        langkahMemasakJson = listOf(
            "Rebus santan dan daging ayam hingga empuk.",
            "Masukkan singkong parut, aduk hingga kental dan matang."
        )
    )),
    DummyRecipe("🍛", LocalRecipe(
        id = 7,
        namaResep = "Nasi Goreng Sayur Telur Puyuh",
        usiaRekomendasiBulan = "1-3 tahun",
        bahanLokalUtama = "Nasi, Telur Puyuh, Wortel, Buncis",
        deskripsiGizi = "Praktis, tinggi protein dan serat sayur",
        langkahMemasakJson = listOf(
            "Tumis wortel dan buncis cincang hingga layu.",
            "Masukkan nasi dan telur puyuh rebus, aduk rata."
        )
    )),
    DummyRecipe("🥗", LocalRecipe(
        id = 8,
        namaResep = "Sayur Bayam + Jagung Manis",
        usiaRekomendasiBulan = "Keluarga",
        bahanLokalUtama = "Bayam, Jagung Manis, Bawang Merah, Bawang Putih",
        deskripsiGizi = "Sumber serat dan vitamin untuk seluruh keluarga",
        langkahMemasakJson = listOf(
            "Rebus air dengan bawang merah dan bawang putih hingga harum.",
            "Masukkan jagung manis, masak hingga empuk.",
            "Tambahkan bayam, masak sebentar hingga layu."
        )
    )),
    DummyRecipe("🍲", LocalRecipe(
        id = 9,
        namaResep = "Sup Ikan Kembung Pangan Lokal",
        usiaRekomendasiBulan = "Keluarga",
        bahanLokalUtama = "Ikan Kembung, Tomat, Daun Kemangi, Belimbing Wuluh",
        deskripsiGizi = "Segar, tinggi protein dan rendah lemak",
        langkahMemasakJson = listOf(
            "Rebus ikan kembung dengan tomat dan belimbing wuluh hingga matang.",
            "Tambahkan daun kemangi sebelum diangkat."
        )
    ))
)

@Composable
fun ResepListScreen(
    recipesState: UiState<List<LocalRecipe>>,
    onBackClick: () -> Unit,
    onRecipeClick: (LocalRecipe) -> Unit
) {
    var selectedTab by remember { mutableStateOf(TAB_OPTIONS.first()) }

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
            Text("Resep", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = PrimaryRose)
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

            val fromApi = (recipesState as? UiState.Success)?.data?.takeIf { it.isNotEmpty() }
            val allRecipes: List<DummyRecipe> = fromApi?.mapIndexed { index, r -> DummyRecipe(SAMPLE_RECIPES[index % SAMPLE_RECIPES.size].emoji, r) }
                ?: SAMPLE_RECIPES

            val filtered = if (selectedTab == "Semua") allRecipes else allRecipes.filter { recipeCategory(it.recipe) == selectedTab }

            when (recipesState) {
                is UiState.Loading -> {
                    Box(modifier = Modifier.fillMaxWidth().height(120.dp), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = PrimaryRose)
                    }
                }
                else -> {
                    if (filtered.isEmpty()) {
                        Text(
                            "Belum ada resep untuk kategori ini.",
                            fontSize = 13.sp,
                            color = TextMuted,
                            modifier = Modifier.padding(vertical = 16.dp)
                        )
                    } else {
                        for (item in filtered) {
                            RecipeItemCard(recipe = item.recipe, emoji = item.emoji, onClick = { onRecipeClick(item.recipe) })
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun RecipeItemCard(
    recipe: LocalRecipe,
    emoji: String = "🥣",
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(84.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(CardPinkBg),
                contentAlignment = Alignment.Center
            ) {
                Text(emoji, fontSize = 36.sp)
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = recipe.namaResep,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDarkTitle
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = recipe.deskripsiGizi.ifBlank { recipe.bahanLokalUtama },
                    fontSize = 12.sp,
                    color = TextMuted,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Untuk: ${recipe.usiaRekomendasiBulan.ifBlank { "Semua usia" }}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextDarkTitle
                )
            }
        }
    }
}
