package com.psikonutrisense.app.ui.whostandards;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000@\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\u001a\u0016\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0007\u001a(\u0010\t\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\u00012\u0006\u0010\r\u001a\u00020\u0001H\u0002\u001a\u0018\u0010\u000e\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u0010H\u0002\u001a\u0018\u0010\u0011\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u0010H\u0002\u001a\u0010\u0010\u0012\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\u0007H\u0002\u001a<\u0010\u0014\u001a\u0014\u0012\u0004\u0012\u00020\u0016\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00180\u00170\u00152\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0013\u001a\u00020\u00072\b\b\u0002\u0010\u001b\u001a\u00020\u001c2\b\b\u0002\u0010\u001d\u001a\u00020\u001c\u001a\u0018\u0010\u001e\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u0010H\u0002\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0082T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0003\u001a\u00020\u0001X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001f"}, d2 = {"BMI_SD_FRACTION", "", "HEIGHT_SD_FRACTION", "WEIGHT_SD_FRACTION", "ageInMonths", "", "birthDateIso", "", "measurementDateIso", "asymptotic", "t", "birth", "asymptote", "rate", "bmiMedian", "boy", "", "heightMedianCm", "isBoy", "gender", "referenceCurves", "", "Lcom/psikonutrisense/app/ui/whostandards/SdBand;", "", "Lcom/psikonutrisense/app/ui/whostandards/CurvePoint;", "indicator", "Lcom/psikonutrisense/app/ui/whostandards/GrowthIndicator;", "maxMonths", "", "stepMonths", "weightMedianKg", "app_debug"})
public final class WhoGrowthReferenceKt {
    
    /**
     * Fractional SD width relative to the median, tuned so ±2SD/±3SD bands fan out like the WHO charts.
     */
    private static final double WEIGHT_SD_FRACTION = 0.14;
    private static final double HEIGHT_SD_FRACTION = 0.045;
    private static final double BMI_SD_FRACTION = 0.12;
    
    private static final boolean isBoy(java.lang.String gender) {
        return false;
    }
    
    private static final double asymptotic(double t, double birth, double asymptote, double rate) {
        return 0.0;
    }
    
    private static final double weightMedianKg(double t, boolean boy) {
        return 0.0;
    }
    
    private static final double heightMedianCm(double t, boolean boy) {
        return 0.0;
    }
    
    private static final double bmiMedian(double t, boolean boy) {
        return 0.0;
    }
    
    /**
     * Returns the 5 z-score band curves for [indicator]/[gender] sampled every [stepMonths]
     * from 0 to [maxMonths]. For BB/TB the x-axis is height (derived from the median
     * height-for-age curve) rather than age, matching how WHO weight-for-height charts work.
     */
    @org.jetbrains.annotations.NotNull()
    public static final java.util.Map<com.psikonutrisense.app.ui.whostandards.SdBand, java.util.List<com.psikonutrisense.app.ui.whostandards.CurvePoint>> referenceCurves(@org.jetbrains.annotations.NotNull()
    com.psikonutrisense.app.ui.whostandards.GrowthIndicator indicator, @org.jetbrains.annotations.NotNull()
    java.lang.String gender, int maxMonths, int stepMonths) {
        return null;
    }
    
    public static final float ageInMonths(@org.jetbrains.annotations.NotNull()
    java.lang.String birthDateIso, @org.jetbrains.annotations.NotNull()
    java.lang.String measurementDateIso) {
        return 0.0F;
    }
}