package com.psikonutrisense.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.draw.clip
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.psikonutrisense.app.data.model.Child
import com.psikonutrisense.app.data.model.GrowthRecord
import com.psikonutrisense.app.ui.UiState
import com.psikonutrisense.app.ui.theme.*
import com.psikonutrisense.app.ui.whostandards.GrowthIndicator
import com.psikonutrisense.app.ui.whostandards.SdBand
import com.psikonutrisense.app.ui.whostandards.ageInMonths
import com.psikonutrisense.app.ui.whostandards.referenceCurves
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GrowthChartScreen(
    growthState: UiState<List<GrowthRecord>>,
    onBackClick: () -> Unit,
    onAddRecordClick: () -> Unit,
    child: Child? = null,
    onEditRecordClick: (GrowthRecord) -> Unit = {},
    onDeleteRecordClick: (GrowthRecord) -> Unit = {}
) {
    var selectedIndicator by remember { mutableStateOf(GrowthIndicator.BB_U) }
    var showHistorySheet by remember { mutableStateOf(false) }
    var pendingDelete by remember { mutableStateOf<GrowthRecord?>(null) }

    Scaffold(
        containerColor = BackgroundSoftPink,
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(BackgroundSoftPink)
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali", tint = PrimaryRose)
                }
                Text(
                    text = "Grafik Pertumbuhan",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryRose
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddRecordClick,
                containerColor = PrimaryRose,
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Tambah Antropometri")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(BackgroundSoftPink)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            IndicatorTabRow(
                selected = selectedIndicator,
                onSelect = { selectedIndicator = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            val records = (growthState as? UiState.Success)?.data.orEmpty()

            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = selectedIndicator.title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDarkTitle,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    when (growthState) {
                        is UiState.Loading -> {
                            Box(modifier = Modifier.fillMaxWidth().height(240.dp), contentAlignment = Alignment.Center) {
                                CircularProgressIndicator(color = PrimaryRose)
                            }
                        }
                        else -> {
                            WhoGrowthChart(
                                indicator = selectedIndicator,
                                gender = child?.jenisKelamin ?: "L",
                                birthDate = child?.tanggalLahirBalita ?: "",
                                records = records,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(240.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            ZScoreLegend()

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Riwayat Pengukuran Antropometri",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDarkTitle
                )
                TextButton(onClick = { showHistorySheet = true }) {
                    Icon(Icons.Default.History, contentDescription = null, tint = PrimaryRose, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Riwayat", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = PrimaryRose)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            when (growthState) {
                is UiState.Success -> {
                    if (records.isEmpty()) {
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                        ) {
                            Text(
                                "Belum ada data antropometri yang dicatat. Klik tombol + untuk menambahkan.",
                                modifier = Modifier.padding(16.dp),
                                fontSize = 13.sp,
                                color = TextMuted
                            )
                        }
                    } else {
                        val latest = records.takeLast(2).reversed()
                        for (item in latest) {
                            GrowthRecordCard(
                                item = item,
                                onEditClick = { onEditRecordClick(item) },
                                onDeleteClick = { pendingDelete = item }
                            )
                        }
                    }
                }
                is UiState.Error -> {
                    Text(growthState.message, color = Color.Red, fontSize = 13.sp)
                }
                else -> {}
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }

    if (showHistorySheet) {
        val records = (growthState as? UiState.Success)?.data.orEmpty().reversed()
        ModalBottomSheet(onDismissRequest = { showHistorySheet = false }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Riwayat Pengukuran Antropometri",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDarkTitle
                )
                Spacer(modifier = Modifier.height(12.dp))

                if (records.isEmpty()) {
                    Text(
                        "Belum ada riwayat pengukuran.",
                        fontSize = 13.sp,
                        color = TextMuted,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                } else {
                    for (item in records) {
                        GrowthRecordCard(
                            item = item,
                            onEditClick = {
                                showHistorySheet = false
                                onEditRecordClick(item)
                            },
                            onDeleteClick = { pendingDelete = item }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

    val toDelete = pendingDelete
    if (toDelete != null) {
        AlertDialog(
            onDismissRequest = { pendingDelete = null },
            title = { Text("Hapus Catatan Ini?") },
            text = { Text("Data antropometri tanggal ${toDelete.tanggalPenimbangan} akan dihapus secara permanen.") },
            confirmButton = {
                TextButton(onClick = {
                    pendingDelete = null
                    onDeleteRecordClick(toDelete)
                }) {
                    Text("Hapus", color = Color.Red, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { pendingDelete = null }) {
                    Text("Batal")
                }
            }
        )
    }
}

@Composable
private fun IndicatorTabRow(
    selected: GrowthIndicator,
    onSelect: (GrowthIndicator) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(24.dp))
            .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        for (indicator in GrowthIndicator.entries) {
            val isSelected = indicator == selected
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(20.dp))
                    .background(if (isSelected) ButtonPinkBg else Color.Transparent)
                    .clickable { onSelect(indicator) }
                    .padding(vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = indicator.label,
                    fontSize = 13.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                    color = if (isSelected) ButtonPinkText else TextMuted
                )
            }
        }
    }
}

@Composable
private fun ZScoreLegend() {
    Column {
        Text("Z-score", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextDarkTitle)
        Spacer(modifier = Modifier.height(8.dp))
        val leftColumn = listOf(SdBand.PLUS_3, SdBand.NORMAL, SdBand.MINUS_2)
        val rightColumn = listOf(SdBand.PLUS_2, SdBand.NORMAL, SdBand.MINUS_3)
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.weight(1f)) {
                for (band in leftColumn) LegendRow(band)
            }
            Column(modifier = Modifier.weight(1f)) {
                for (band in rightColumn) LegendRow(band)
            }
        }
    }
}

private fun sdBandColor(band: SdBand): Color = when (band) {
    SdBand.PLUS_3 -> ChartSdPlus3
    SdBand.PLUS_2 -> ChartSdPlus2
    SdBand.NORMAL -> ChartSdNormal
    SdBand.MINUS_2 -> ChartSdMinus2
    SdBand.MINUS_3 -> ChartSdMinus3
}

@Composable
private fun LegendRow(band: SdBand) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .width(20.dp)
                .height(3.dp)
                .background(sdBandColor(band), RoundedCornerShape(2.dp))
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(band.label, fontSize = 13.sp, color = TextMuted)
    }
}

@Composable
private fun WhoGrowthChart(
    indicator: GrowthIndicator,
    gender: String,
    birthDate: String,
    records: List<GrowthRecord>,
    modifier: Modifier = Modifier
) {
    val bandCurves = remember(indicator, gender) { referenceCurves(indicator, gender) }

    val dataPoints: List<Entry> = remember(indicator, gender, birthDate, records) {
        records.mapNotNull { record ->
            when (indicator) {
                GrowthIndicator.BB_U -> {
                    val age = ageInMonths(birthDate, record.tanggalPenimbangan)
                    Entry(age, record.beratBadanKg.toFloat())
                }
                GrowthIndicator.TB_U -> {
                    val age = ageInMonths(birthDate, record.tanggalPenimbangan)
                    Entry(age, record.tinggiBadanCm.toFloat())
                }
                GrowthIndicator.IMT_U -> {
                    if (record.tinggiBadanCm <= 0.0) return@mapNotNull null
                    val age = ageInMonths(birthDate, record.tanggalPenimbangan)
                    val heightM = record.tinggiBadanCm / 100.0
                    val bmi = record.beratBadanKg / (heightM * heightM)
                    Entry(age, bmi.toFloat())
                }
                GrowthIndicator.BB_TB -> {
                    if (record.tinggiBadanCm <= 0.0) return@mapNotNull null
                    Entry(record.tinggiBadanCm.toFloat(), record.beratBadanKg.toFloat())
                }
            }
        }.sortedBy { it.x }
    }

    val xAxisLabel = if (indicator == GrowthIndicator.BB_TB) "Tinggi Badan (cm)" else "Bulan (Usia)"
    val textColorArgb = TextMuted.toArgb()
    val gridColorArgb = BorderLight.toArgb()

    AndroidView(
        modifier = modifier,
        factory = { context ->
            LineChart(context).apply {
                description.isEnabled = false
                legend.isEnabled = false
                setTouchEnabled(true)
                setPinchZoom(true)
                axisRight.isEnabled = false
                extraBottomOffset = 8f
            }
        },
        update = { chart ->
            val bandSets = bandCurves.map { (band, points) ->
                LineDataSet(points.map { Entry(it.x, it.y) }, band.label).apply {
                    color = sdBandColor(band).toArgb()
                    lineWidth = 1.6f
                    setDrawCircles(false)
                    setDrawValues(false)
                    mode = LineDataSet.Mode.CUBIC_BEZIER
                }
            }

            val actualDataSet = LineDataSet(dataPoints, "Data Anak").apply {
                color = ChartDataPoint.toArgb()
                setCircleColor(ChartDataPoint.toArgb())
                circleRadius = 4f
                setDrawCircleHole(false)
                lineWidth = 1.5f
                setDrawValues(false)
                enableDashedLine(6f, 4f, 0f)
            }

            chart.data = LineData(bandSets + actualDataSet)

            chart.xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                textColor = textColorArgb
                gridColor = gridColorArgb
                axisLineColor = gridColorArgb
                granularity = 1f
                setDrawGridLines(true)
                valueFormatter = object : ValueFormatter() {
                    override fun getFormattedValue(value: Float): String = value.roundToInt().toString()
                }
            }

            chart.axisLeft.apply {
                textColor = textColorArgb
                gridColor = gridColorArgb
                axisLineColor = gridColorArgb
                setDrawGridLines(true)
                axisMinimum = 0f
            }

            chart.invalidate()
        }
    )
}

@Composable
private fun GrowthRecordCard(
    item: GrowthRecord,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text("Tanggal: ${item.tanggalPenimbangan}", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text("BB: ${item.beratBadanKg} kg | TB: ${item.tinggiBadanCm} cm", fontSize = 13.sp, color = TextMuted)
                Text("LILA: ${item.lilaCm} cm | LiKa: ${item.lingkarKepalaCm} cm", fontSize = 12.sp, color = TextMuted)
                Spacer(modifier = Modifier.height(4.dp))
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = GreenStatusBg
                ) {
                    Text(
                        text = item.statusGizi,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        color = GreenStatusText,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Row {
                IconButton(onClick = onEditClick) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit", tint = PrimaryRose, modifier = Modifier.size(20.dp))
                }
                IconButton(onClick = onDeleteClick) {
                    Icon(Icons.Default.Delete, contentDescription = "Hapus", tint = Color.Red, modifier = Modifier.size(20.dp))
                }
            }
        }
    }
}
