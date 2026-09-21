package com.psikonutrisense.app.ui.screens;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000T\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001ap\u0010\u0000\u001a\u00020\u00012\u0012\u0010\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u00032\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n2\u0014\b\u0002\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\f2\u0014\b\u0002\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\fH\u0007\u001a,\u0010\u000e\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u00052\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007H\u0003\u001a$\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00142\u0012\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00010\fH\u0003\u001a\u0010\u0010\u0016\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0018H\u0003\u001a8\u0010\u0019\u001a\u00020\u00012\u0006\u0010\u001a\u001a\u00020\u00142\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001c2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\b\b\u0002\u0010\u001f\u001a\u00020 H\u0003\u001a\b\u0010!\u001a\u00020\u0001H\u0003\u001a\u0015\u0010\"\u001a\u00020#2\u0006\u0010\u0017\u001a\u00020\u0018H\u0002\u00a2\u0006\u0002\u0010$\u00a8\u0006%"}, d2 = {"GrowthChartScreen", "", "growthState", "Lcom/psikonutrisense/app/ui/UiState;", "", "Lcom/psikonutrisense/app/data/model/GrowthRecord;", "onBackClick", "Lkotlin/Function0;", "onAddRecordClick", "child", "Lcom/psikonutrisense/app/data/model/Child;", "onEditRecordClick", "Lkotlin/Function1;", "onDeleteRecordClick", "GrowthRecordCard", "item", "onEditClick", "onDeleteClick", "IndicatorTabRow", "selected", "Lcom/psikonutrisense/app/ui/whostandards/GrowthIndicator;", "onSelect", "LegendRow", "band", "Lcom/psikonutrisense/app/ui/whostandards/SdBand;", "WhoGrowthChart", "indicator", "gender", "", "birthDate", "records", "modifier", "Landroidx/compose/ui/Modifier;", "ZScoreLegend", "sdBandColor", "Landroidx/compose/ui/graphics/Color;", "(Lcom/psikonutrisense/app/ui/whostandards/SdBand;)J", "app_debug"})
public final class GrowthChartScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void GrowthChartScreen(@org.jetbrains.annotations.NotNull()
    com.psikonutrisense.app.ui.UiState<? extends java.util.List<com.psikonutrisense.app.data.model.GrowthRecord>> growthState, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBackClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onAddRecordClick, @org.jetbrains.annotations.Nullable()
    com.psikonutrisense.app.data.model.Child child, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.psikonutrisense.app.data.model.GrowthRecord, kotlin.Unit> onEditRecordClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.psikonutrisense.app.data.model.GrowthRecord, kotlin.Unit> onDeleteRecordClick) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void IndicatorTabRow(com.psikonutrisense.app.ui.whostandards.GrowthIndicator selected, kotlin.jvm.functions.Function1<? super com.psikonutrisense.app.ui.whostandards.GrowthIndicator, kotlin.Unit> onSelect) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ZScoreLegend() {
    }
    
    private static final long sdBandColor(com.psikonutrisense.app.ui.whostandards.SdBand band) {
        return 0L;
    }
    
    @androidx.compose.runtime.Composable()
    private static final void LegendRow(com.psikonutrisense.app.ui.whostandards.SdBand band) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void WhoGrowthChart(com.psikonutrisense.app.ui.whostandards.GrowthIndicator indicator, java.lang.String gender, java.lang.String birthDate, java.util.List<com.psikonutrisense.app.data.model.GrowthRecord> records, androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void GrowthRecordCard(com.psikonutrisense.app.data.model.GrowthRecord item, kotlin.jvm.functions.Function0<kotlin.Unit> onEditClick, kotlin.jvm.functions.Function0<kotlin.Unit> onDeleteClick) {
    }
}