package com.psikonutrisense.app.data.model;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u001e\b\u0087\b\u0018\u00002\u00020\u0001Be\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0003\u0012\b\b\u0002\u0010\b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\t\u001a\u00020\u0003\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u000b\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000b\u00a2\u0006\u0002\u0010\u000eJ\t\u0010\u001b\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001c\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001d\u001a\u00020\u0006H\u00c6\u0003J\t\u0010\u001e\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010 \u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010!\u001a\u0004\u0018\u00010\u000bH\u00c6\u0003J\u000b\u0010\"\u001a\u0004\u0018\u00010\u000bH\u00c6\u0003J\u000b\u0010#\u001a\u0004\u0018\u00010\u000bH\u00c6\u0003Ji\u0010$\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\u00032\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u000b2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000bH\u00c6\u0001J\u0013\u0010%\u001a\u00020\u00062\b\u0010&\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\'\u001a\u00020\u0003H\u00d6\u0001J\t\u0010(\u001a\u00020\u000bH\u00d6\u0001R\u0016\u0010\u0005\u001a\u00020\u00068\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0016\u0010\u0004\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0016\u0010\b\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0012R\u0016\u0010\t\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0012R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0012R\u0018\u0010\r\u001a\u0004\u0018\u00010\u000b8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0018\u0010\f\u001a\u0004\u0018\u00010\u000b8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0017R\u0018\u0010\n\u001a\u0004\u0018\u00010\u000b8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0017R\u0016\u0010\u0007\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0012\u00a8\u0006)"}, d2 = {"Lcom/psikonutrisense/app/data/model/NutritionRecord;", "", "id", "", "childId", "asiEksklusif", "", "usiaMulaiMpasiBulan", "frekuensiMakanPerHari", "frekuensiProteinHewaniPerMinggu", "recall24jamMenu", "", "panganLokalFavorit", "kendalaPemberianMakan", "(IIZIIILjava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getAsiEksklusif", "()Z", "getChildId", "()I", "getFrekuensiMakanPerHari", "getFrekuensiProteinHewaniPerMinggu", "getId", "getKendalaPemberianMakan", "()Ljava/lang/String;", "getPanganLokalFavorit", "getRecall24jamMenu", "getUsiaMulaiMpasiBulan", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "toString", "app_debug"})
public final class NutritionRecord {
    private final int id = 0;
    @com.google.gson.annotations.SerializedName(value = "child_id")
    private final int childId = 0;
    @com.google.gson.annotations.SerializedName(value = "asi_eksklusif")
    private final boolean asiEksklusif = false;
    @com.google.gson.annotations.SerializedName(value = "usia_mulai_mpasi_bulan")
    private final int usiaMulaiMpasiBulan = 0;
    @com.google.gson.annotations.SerializedName(value = "frekuensi_makan_per_hari")
    private final int frekuensiMakanPerHari = 0;
    @com.google.gson.annotations.SerializedName(value = "frekuensi_protein_hewani_per_minggu")
    private final int frekuensiProteinHewaniPerMinggu = 0;
    @com.google.gson.annotations.SerializedName(value = "recall_24jam_menu")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String recall24jamMenu = null;
    @com.google.gson.annotations.SerializedName(value = "pangan_lokal_favorit")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String panganLokalFavorit = null;
    @com.google.gson.annotations.SerializedName(value = "kendala_pemberian_makan")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String kendalaPemberianMakan = null;
    
    public NutritionRecord(int id, int childId, boolean asiEksklusif, int usiaMulaiMpasiBulan, int frekuensiMakanPerHari, int frekuensiProteinHewaniPerMinggu, @org.jetbrains.annotations.Nullable()
    java.lang.String recall24jamMenu, @org.jetbrains.annotations.Nullable()
    java.lang.String panganLokalFavorit, @org.jetbrains.annotations.Nullable()
    java.lang.String kendalaPemberianMakan) {
        super();
    }
    
    public final int getId() {
        return 0;
    }
    
    public final int getChildId() {
        return 0;
    }
    
    public final boolean getAsiEksklusif() {
        return false;
    }
    
    public final int getUsiaMulaiMpasiBulan() {
        return 0;
    }
    
    public final int getFrekuensiMakanPerHari() {
        return 0;
    }
    
    public final int getFrekuensiProteinHewaniPerMinggu() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getRecall24jamMenu() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getPanganLokalFavorit() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getKendalaPemberianMakan() {
        return null;
    }
    
    public NutritionRecord() {
        super();
    }
    
    public final int component1() {
        return 0;
    }
    
    public final int component2() {
        return 0;
    }
    
    public final boolean component3() {
        return false;
    }
    
    public final int component4() {
        return 0;
    }
    
    public final int component5() {
        return 0;
    }
    
    public final int component6() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component7() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component8() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.psikonutrisense.app.data.model.NutritionRecord copy(int id, int childId, boolean asiEksklusif, int usiaMulaiMpasiBulan, int frekuensiMakanPerHari, int frekuensiProteinHewaniPerMinggu, @org.jetbrains.annotations.Nullable()
    java.lang.String recall24jamMenu, @org.jetbrains.annotations.Nullable()
    java.lang.String panganLokalFavorit, @org.jetbrains.annotations.Nullable()
    java.lang.String kendalaPemberianMakan) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
}