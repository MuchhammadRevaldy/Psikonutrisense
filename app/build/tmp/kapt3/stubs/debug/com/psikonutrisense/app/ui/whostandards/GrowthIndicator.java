package com.psikonutrisense.app.ui.whostandards;

/**
 * Smooth approximations of the WHO child growth standard curve *shapes*
 * (weight/height/BMI-for-age, 0-60 months), tuned to pass close to the
 * commonly published WHO median values at a few checkpoints (birth, 6, 12,
 * 24, 60 months). These are NOT the official WHO LMS reference tables —
 * they exist to render a visually faithful chart (design/02_Grafik
 * Pertumbuhan.jpg). Swap in the real WHO LMS tables here before using this
 * for clinical decisions.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\f\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u001f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0006R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\bR\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\bj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000e\u00a8\u0006\u000f"}, d2 = {"Lcom/psikonutrisense/app/ui/whostandards/GrowthIndicator;", "", "label", "", "title", "yAxisUnit", "(Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getLabel", "()Ljava/lang/String;", "getTitle", "getYAxisUnit", "BB_U", "TB_U", "BB_TB", "IMT_U", "app_debug"})
public enum GrowthIndicator {
    /*public static final*/ BB_U /* = new BB_U(null, null, null) */,
    /*public static final*/ TB_U /* = new TB_U(null, null, null) */,
    /*public static final*/ BB_TB /* = new BB_TB(null, null, null) */,
    /*public static final*/ IMT_U /* = new IMT_U(null, null, null) */;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String label = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String title = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String yAxisUnit = null;
    
    GrowthIndicator(java.lang.String label, java.lang.String title, java.lang.String yAxisUnit) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getLabel() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTitle() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getYAxisUnit() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static kotlin.enums.EnumEntries<com.psikonutrisense.app.ui.whostandards.GrowthIndicator> getEntries() {
        return null;
    }
}