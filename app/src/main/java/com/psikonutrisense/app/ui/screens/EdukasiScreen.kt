package com.psikonutrisense.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Vaccines
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

private val TAB_OPTIONS = listOf("Semua", "Gizi", "Kesehatan", "Psikososial")

data class ArticleItem(
    val id: Int,
    val emoji: String,
    val title: String,
    val category: String,
    val readTime: String,
    val summary: String,
    val bodyParagraphs: List<String>
)

val EDUKASI_ARTICLES = listOf(
    ArticleItem(
        id = 1,
        emoji = "🍽️",
        title = "Tips MPASI Kaya Protein Hewani untuk Si Kecil",
        category = "Gizi",
        readTime = "3 menit baca",
        summary = "Protein hewani adalah kunci pencegahan stunting sejak MPASI dimulai.",
        bodyParagraphs = listOf(
            "Protein hewani seperti telur, ikan, ayam, dan daging mengandung asam amino esensial lengkap yang sangat dibutuhkan untuk pertumbuhan otak dan fisik anak di 1000 Hari Pertama Kehidupan.",
            "Mulai usia 6 bulan, pastikan setiap sesi MPASI mengandung minimal satu sumber protein hewani. Telur rebus yang dihaluskan atau ikan kukus tanpa duri adalah pilihan yang mudah dan terjangkau.",
            "Di Ngeposari, ikan lele dan nila mudah didapat dengan harga terjangkau — keduanya kaya protein dan omega-3 yang baik untuk perkembangan otak anak.",
            "Hindari menunda pemberian protein hewani karena takut alergi tanpa alasan medis yang jelas. Konsultasikan dengan kader atau bidan jika ragu."
        )
    ),
    ArticleItem(
        id = 2,
        emoji = "🥣",
        title = "Cara Cegah Stunting dari Sekarang",
        category = "Kesehatan",
        readTime = "4 menit baca",
        summary = "Pencegahan stunting paling efektif dimulai sejak 1000 Hari Pertama Kehidupan.",
        bodyParagraphs = listOf(
            "Stunting adalah kondisi gagal tumbuh pada anak akibat kekurangan gizi kronis, terutama pada 1000 Hari Pertama Kehidupan (sejak dalam kandungan hingga usia 2 tahun).",
            "Langkah pencegahan utama: pastikan ibu hamil mendapat gizi seimbang, ASI eksklusif 0-6 bulan, MPASI bergizi tepat waktu, imunisasi lengkap, dan pemantauan tumbuh kembang rutin di Posyandu.",
            "Timbang dan ukur tinggi badan anak setiap bulan untuk mendeteksi dini risiko gizi kurang sebelum menjadi stunting permanen.",
            "Sanitasi lingkungan yang bersih dan akses air minum yang aman juga berperan besar mencegah infeksi berulang yang mengganggu penyerapan gizi anak."
        )
    ),
    ArticleItem(
        id = 3,
        emoji = "🧘‍♀️",
        title = "Mengelola Stres Untuk Ibu",
        category = "Psikososial",
        readTime = "3 menit baca",
        summary = "Kesehatan mental ibu berdampak langsung pada kualitas pengasuhan dan tumbuh kembang anak.",
        bodyParagraphs = listOf(
            "Mengasuh balita bisa melelahkan secara fisik dan emosional. Ibu yang stres berkepanjangan berisiko lebih sulit memberikan pengasuhan responsif yang dibutuhkan anak untuk tumbuh optimal.",
            "Luangkan waktu singkat setiap hari untuk diri sendiri — bahkan 10 menit menarik napas dalam atau berjalan santai bisa membantu meredakan ketegangan.",
            "Jangan ragu berbagi cerita dengan pasangan, keluarga, atau kader Posyandu. Dukungan sosial adalah salah satu faktor pelindung terkuat kesejahteraan psikologis ibu.",
            "Jika perasaan cemas atau sedih terasa berat dan berkepanjangan, segera konsultasikan ke bidan atau tenaga kesehatan terdekat — bukan tanda kelemahan, tapi langkah menjaga diri dan anak."
        )
    ),
    ArticleItem(
        id = 4,
        emoji = "🍲",
        title = "Porsi Makan Ideal Balita Sesuai Usia",
        category = "Gizi",
        readTime = "3 menit baca",
        summary = "Ukuran porsi yang tepat membantu anak mendapat kalori cukup tanpa berlebihan.",
        bodyParagraphs = listOf(
            "Kapasitas lambung anak masih kecil, sehingga porsi makan perlu disesuaikan bertahap sesuai usia — bukan langsung disamakan dengan porsi orang dewasa.",
            "Usia 6-8 bulan: 2-3 sendok makan per sesi, 2-3 kali sehari. Usia 9-11 bulan: setengah mangkuk kecil, 3-4 kali sehari. Usia 12-23 bulan: 3/4 mangkuk, 3-4 kali sehari plus 1-2 camilan.",
            "Jangan memaksa anak menghabiskan porsi jika sudah menunjukkan tanda kenyang — lebih baik makan sedikit tapi sering daripada memaksakan porsi besar sekali makan."
        )
    ),
    ArticleItem(
        id = 5,
        emoji = "🌿",
        title = "Mengenal Pangan Lokal Kaya Zat Besi",
        category = "Gizi",
        readTime = "2 menit baca",
        summary = "Zat besi dari bahan lokal Ngeposari mudah didapat dan terjangkau.",
        bodyParagraphs = listOf(
            "Daun kelor, kacang hijau, dan hati ayam adalah sumber zat besi lokal yang mudah ditemukan di Ngeposari dan sekitarnya, jauh lebih terjangkau dibanding suplemen.",
            "Kekurangan zat besi pada anak dapat menyebabkan anemia yang berdampak pada konsentrasi, energi, dan pertumbuhan kognitif.",
            "Kombinasikan pangan tinggi zat besi dengan sumber vitamin C (seperti jambu atau tomat) untuk meningkatkan penyerapan zat besi dalam tubuh anak."
        )
    ),
    ArticleItem(
        id = 6,
        emoji = "💉",
        title = "Jadwal Imunisasi Dasar Lengkap",
        category = "Kesehatan",
        readTime = "4 menit baca",
        summary = "Imunisasi tepat waktu melindungi anak dari penyakit berbahaya yang bisa dicegah.",
        bodyParagraphs = listOf(
            "Imunisasi dasar lengkap meliputi Hepatitis B, BCG, Polio, DPT-HB-Hib, dan Campak/MR, diberikan bertahap sejak lahir hingga usia 9 bulan.",
            "Keterlambatan jadwal imunisasi meningkatkan risiko anak tertular penyakit serius seperti difteri, pertusis, dan campak yang bisa berakibat fatal.",
            "Bawa anak ke Posyandu atau Puskesmas sesuai jadwal, dan simpan kartu/buku imunisasi dengan baik sebagai catatan riwayat kesehatan anak."
        )
    ),
    ArticleItem(
        id = 7,
        emoji = "📏",
        title = "Pentingnya Pemantauan Tumbuh Kembang di Posyandu",
        category = "Kesehatan",
        readTime = "3 menit baca",
        summary = "Penimbangan rutin adalah cara paling awal mendeteksi risiko gangguan pertumbuhan.",
        bodyParagraphs = listOf(
            "Penimbangan dan pengukuran tinggi badan setiap bulan di Posyandu membantu mendeteksi dini apakah pertumbuhan anak sesuai dengan kurva WHO.",
            "Jika berat badan anak tidak naik dua bulan berturut-turut, ini adalah sinyal awal yang perlu segera ditindaklanjuti bersama kader atau bidan.",
            "Jangan menunggu anak terlihat 'kurus' baru khawatir — grafik pertumbuhan bisa mendeteksi masalah jauh lebih awal daripada mata telanjang."
        )
    ),
    ArticleItem(
        id = 8,
        emoji = "🤱",
        title = "Membangun Kedekatan Emosional dengan Anak",
        category = "Psikososial",
        readTime = "3 menit baca",
        summary = "Interaksi sederhana sehari-hari membentuk fondasi perkembangan emosi anak.",
        bodyParagraphs = listOf(
            "Bermain, bicara, dan bernyanyi bersama anak setiap hari bukan sekadar hiburan — ini adalah stimulasi penting untuk perkembangan bahasa dan sosial-emosional anak.",
            "Respons yang hangat terhadap tangisan atau celotehan anak mengajarkan mereka bahwa dunia adalah tempat yang aman, membangun rasa percaya diri sejak dini.",
            "Sisihkan minimal 20-30 menit sehari untuk waktu berkualitas tanpa gangguan gawai — kualitas interaksi lebih penting daripada durasinya."
        )
    ),
    ArticleItem(
        id = 9,
        emoji = "👨‍👩‍👧",
        title = "Pentingnya Dukungan Keluarga Bagi Ibu Baru",
        category = "Psikososial",
        readTime = "3 menit baca",
        summary = "Dukungan emosional, informasi, dan praktis dari keluarga mempercepat pemulihan ibu.",
        bodyParagraphs = listOf(
            "Masa awal menjadi ibu penuh tantangan fisik dan emosional. Dukungan keluarga — baik berupa perhatian, informasi, maupun bantuan praktis mengurus rumah — sangat berpengaruh pada kesejahteraan ibu.",
            "Suami dan anggota keluarga lain bisa berperan dengan cara sederhana: mendengarkan tanpa menghakimi, membantu pekerjaan rumah, atau sekadar menemani saat ibu lelah.",
            "Ibu yang merasa didukung cenderung lebih mampu memberikan pengasuhan yang responsif, yang pada akhirnya berdampak positif pada tumbuh kembang anak."
        )
    )
)

@Composable
fun EdukasiScreen(
    onBackClick: () -> Unit,
    onArticleClick: (ArticleItem) -> Unit = {}
) {
    var selectedTab by remember { mutableStateOf(TAB_OPTIONS.first()) }
    var pengingatExpanded by remember { mutableStateOf(true) }

    val filteredArticles = remember(selectedTab) {
        if (selectedTab == "Semua") EDUKASI_ARTICLES else EDUKASI_ARTICLES.filter { it.category == selectedTab }
    }

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
            Text("Edukasi & Pengingat", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = PrimaryRose)
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

            if (filteredArticles.isEmpty()) {
                Text(
                    "Belum ada artikel untuk kategori ini.",
                    fontSize = 13.sp,
                    color = TextMuted,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            } else {
                for (article in filteredArticles) {
                    Card(
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                            .clickable { onArticleClick(article) }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(56.dp)
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(CardPinkBg),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(article.emoji, fontSize = 26.sp)
                            }
                            Spacer(modifier = Modifier.width(14.dp))
                            Text(
                                article.title,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextDarkTitle,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { pengingatExpanded = !pengingatExpanded },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Pengingat", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = PrimaryRoseDark)
                Icon(Icons.Default.KeyboardArrowDown, contentDescription = null, tint = PrimaryRoseDark)
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (pengingatExpanded) {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = CardLightPink),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier.size(44.dp).clip(RoundedCornerShape(14.dp)).background(Color.White),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Vaccines, contentDescription = null, tint = PrimaryRose)
                        }
                        Spacer(modifier = Modifier.width(14.dp))
                        Column {
                            Text("IMUNISASI BERIKUTNYA", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = PrimaryRose, letterSpacing = 0.5.sp)
                            Text("DPT-HB-Hib 3", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextDarkTitle)
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.CalendarToday, contentDescription = null, tint = TextMuted, modifier = Modifier.size(13.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("12 Mei 2026", fontSize = 12.sp, color = TextMuted)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
