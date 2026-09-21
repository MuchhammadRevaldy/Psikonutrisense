package com.psikonutrisense.app.ui.whostandards

import kotlin.math.exp
import kotlin.math.pow

/**
 * Smooth approximations of the WHO child growth standard curve *shapes*
 * (weight/height/BMI-for-age, 0-60 months), tuned to pass close to the
 * commonly published WHO median values at a few checkpoints (birth, 6, 12,
 * 24, 60 months). These are NOT the official WHO LMS reference tables —
 * they exist to render a visually faithful chart (design/02_Grafik
 * Pertumbuhan.jpg). Swap in the real WHO LMS tables here before using this
 * for clinical decisions.
 */

enum class GrowthIndicator(val label: String, val title: String, val yAxisUnit: String) {
    BB_U("BB/U", "Berat Badan Menurut Umur (BB/U)", "kg"),
    TB_U("TB/U", "Tinggi Badan Menurut Umur (TB/U)", "cm"),
    BB_TB("BB/TB", "Berat Badan Menurut Tinggi Badan (BB/TB)", "kg"),
    IMT_U("IMT/U", "Indeks Massa Tubuh Menurut Umur (IMT/U)", "kg/m²")
}

enum class SdBand(val multiplier: Double, val label: String) {
    PLUS_3(3.0, "+3 SD"),
    PLUS_2(2.0, "+2 SD"),
    NORMAL(0.0, "Normal"),
    MINUS_2(-2.0, "-2 SD"),
    MINUS_3(-3.0, "-3 SD")
}

private fun isBoy(gender: String): Boolean = gender.trim().lowercase() in listOf("l", "laki-laki")

private fun asymptotic(t: Double, birth: Double, asymptote: Double, rate: Double): Double =
    birth + (asymptote - birth) * (1 - exp(-rate * t))

private fun weightMedianKg(t: Double, boy: Boolean): Double =
    if (boy) asymptotic(t, 3.3, 22.0, 0.035) else asymptotic(t, 3.2, 20.5, 0.033)

private fun heightMedianCm(t: Double, boy: Boolean): Double =
    if (boy) asymptotic(t, 49.9, 125.0, 0.028) else asymptotic(t, 49.1, 123.0, 0.028)

private fun bmiMedian(t: Double, boy: Boolean): Double {
    val base = if (boy) 14.8 else 14.5
    val bump = 2.6 * exp(-((t - 6.0).pow(2)) / (2 * 8.0.pow(2)))
    val decline = 0.45 * (t / 60.0)
    return base + bump - decline
}

/** Fractional SD width relative to the median, tuned so ±2SD/±3SD bands fan out like the WHO charts. */
private const val WEIGHT_SD_FRACTION = 0.14
private const val HEIGHT_SD_FRACTION = 0.045
private const val BMI_SD_FRACTION = 0.12

data class CurvePoint(val x: Float, val y: Float)

/**
 * Returns the 5 z-score band curves for [indicator]/[gender] sampled every [stepMonths]
 * from 0 to [maxMonths]. For BB/TB the x-axis is height (derived from the median
 * height-for-age curve) rather than age, matching how WHO weight-for-height charts work.
 */
fun referenceCurves(
    indicator: GrowthIndicator,
    gender: String,
    maxMonths: Int = 60,
    stepMonths: Int = 2
): Map<SdBand, List<CurvePoint>> {
    val boy = isBoy(gender)
    val months = (0..maxMonths step stepMonths).map { it.toDouble() }

    return SdBand.entries.associateWith { band ->
        months.map { t ->
            when (indicator) {
                GrowthIndicator.BB_U -> {
                    val y = weightMedianKg(t, boy) * (1 + band.multiplier * WEIGHT_SD_FRACTION / 3.0)
                    CurvePoint(t.toFloat(), y.toFloat())
                }
                GrowthIndicator.TB_U -> {
                    val y = heightMedianCm(t, boy) * (1 + band.multiplier * HEIGHT_SD_FRACTION / 3.0)
                    CurvePoint(t.toFloat(), y.toFloat())
                }
                GrowthIndicator.IMT_U -> {
                    val y = bmiMedian(t, boy) * (1 + band.multiplier * BMI_SD_FRACTION / 3.0)
                    CurvePoint(t.toFloat(), y.toFloat())
                }
                GrowthIndicator.BB_TB -> {
                    val x = heightMedianCm(t, boy)
                    val y = weightMedianKg(t, boy) * (1 + band.multiplier * WEIGHT_SD_FRACTION / 3.0)
                    CurvePoint(x.toFloat(), y.toFloat())
                }
            }
        }
    }
}

fun ageInMonths(birthDateIso: String, measurementDateIso: String): Float {
    return try {
        val birth = birthDateIso.split("-").map { it.toInt() }
        val measured = measurementDateIso.split("-").map { it.toInt() }
        val totalMonthsBirth = birth[0] * 12 + birth[1]
        val totalMonthsMeasured = measured[0] * 12 + measured[1]
        var months = (totalMonthsMeasured - totalMonthsBirth).toFloat()
        months += (measured[2] - birth[2]) / 30.0f
        months.coerceAtLeast(0f)
    } catch (e: Exception) {
        0f
    }
}
