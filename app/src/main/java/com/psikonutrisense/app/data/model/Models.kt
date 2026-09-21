package com.psikonutrisense.app.data.model

import com.google.gson.annotations.SerializedName

// ===========================
// Auth
// ===========================
data class LoginRequest(val email: String, val password: String)
data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
    @SerializedName("password_confirmation") val passwordConfirmation: String,
    val role: String = "ibu"
)
data class AuthResponse(
    val status: String,
    val message: String,
    val user: User?,
    val token: String?
)
data class User(
    val id: Int,
    val name: String,
    val email: String,
    val role: String,
    @SerializedName("created_at") val createdAt: String?
)

// ===========================
// Mother (Profil Ibu)
// ===========================
data class Mother(
    val id: Int = 0,
    @SerializedName("user_id") val userId: Int? = null,
    @SerializedName("nama_ibu") val namaIbu: String = "",
    @SerializedName("nik_ibu") val nikIbu: String = "",
    @SerializedName("tanggal_lahir_ibu") val tanggalLahirIbu: String = "",
    @SerializedName("pendidikan_terakhir") val pendidikanTerakhir: String = "",
    @SerializedName("pekerjaan") val pekerjaan: String = "",
    @SerializedName("alamat_dusun_rt_rw") val alamatDusunRtRw: String = "",
    @SerializedName("no_hp_whatsapp") val noHpWhatsapp: String = "",
    @SerializedName("poin_ibu_aktif") val poinIbuAktif: Int = 1250,
    @SerializedName("lencana_aktif") val lencanaAktif: Boolean = true
)

// ===========================
// Child (Profil Anak)
// ===========================
data class Child(
    val id: Int = 0,
    @SerializedName("mother_id") val motherId: Int = 0,
    @SerializedName("nama_balita") val namaBalita: String = "",
    @SerializedName("tanggal_lahir_balita") val tanggalLahirBalita: String = "",
    @SerializedName("jenis_kelamin") val jenisKelamin: String = "P",
    @SerializedName("anak_ke") val anakKe: Int = 1,
    @SerializedName("jumlah_saudara") val jumlahSaudara: Int = 0,
    @SerializedName("bb_lahir_kg") val bbLahirKg: Double? = null,
    @SerializedName("pb_lahir_cm") val pbLahirCm: Double? = null,
    @SerializedName("lahir_prematur") val lahirPrematur: Boolean = false
)

// ===========================
// Growth Record (Antropometri)
// ===========================
data class GrowthRecord(
    val id: Int = 0,
    @SerializedName("child_id") val childId: Int = 0,
    @SerializedName("tanggal_penimbangan") val tanggalPenimbangan: String = "",
    @SerializedName("berat_badan_kg") val beratBadanKg: Double = 0.0,
    @SerializedName("tinggi_badan_cm") val tinggiBadanCm: Double = 0.0,
    @SerializedName("lingkar_kepala_cm") val lingkarKepalaCm: Double = 0.0,
    @SerializedName("lila_cm") val lilaCm: Double = 0.0,
    @SerializedName("status_penimbangan") val statusPenimbangan: String = "Naik",
    @SerializedName("status_gizi") val statusGizi: String = "Normal",
    @SerializedName("zscore_bbu") val zscoreBbu: Double = 0.0,
    @SerializedName("zscore_tbu") val zscoreTbu: Double = 0.0
)

// ===========================
// Vaccination Record (Imunisasi)
// ===========================
data class VaccinationRecord(
    val id: Int = 0,
    @SerializedName("child_id") val childId: Int = 0,
    @SerializedName("jenis_imunisasi") val jenisImunisasi: String = "",
    @SerializedName("tanggal_jadwal") val tanggalJadwal: String = "",
    @SerializedName("tanggal_pemberian") val tanggalPemberian: String? = null,
    val status: String = "Belum",
    @SerializedName("alasan_belum") val alasanBelum: String? = null
)

// ===========================
// PMT Log
// ===========================
data class PmtLog(
    val id: Int = 0,
    @SerializedName("child_id") val childId: Int = 0,
    val tanggal: String = "",
    @SerializedName("status_konsumsi") val statusKonsumsi: String = "Habis"
)

// ===========================
// Nutrition Record
// ===========================
data class NutritionRecord(
    val id: Int = 0,
    @SerializedName("child_id") val childId: Int = 0,
    @SerializedName("asi_eksklusif") val asiEksklusif: Boolean = true,
    @SerializedName("usia_mulai_mpasi_bulan") val usiaMulaiMpasiBulan: Int = 6,
    @SerializedName("frekuensi_makan_per_hari") val frekuensiMakanPerHari: Int = 3,
    @SerializedName("frekuensi_protein_hewani_per_minggu") val frekuensiProteinHewaniPerMinggu: Int = 0,
    @SerializedName("recall_24jam_menu") val recall24jamMenu: String? = null,
    @SerializedName("pangan_lokal_favorit") val panganLokalFavorit: String? = null,
    @SerializedName("kendala_pemberian_makan") val kendalaPemberianMakan: String? = null
)

// ===========================
// Wellbeing Screening
// ===========================
data class WellbeingScreening(
    val id: Int = 0,
    @SerializedName("mother_id") val motherId: Int = 0,
    @SerializedName("frekuensi_interaksi_bermain") val frekuensiInteraksiBermain: Int = 3,
    @SerializedName("pengasuh_utama") val pengasuhUtama: String = "Ibu Kandung",
    @SerializedName("skor_dukungan_keluarga") val skorDukunganKeluarga: Int = 3,
    @SerializedName("epds_skor_5item") val epdsSkor5item: Int = 0,
    @SerializedName("kategori_kesejahteraan") val kategoriKesejahteraan: String = "Tenang & Bahagia",
    @SerializedName("cukup_informasi_gizi") val cukupInformasiGizi: String = "Ya",
    @SerializedName("sumber_informasi_terpercaya") val sumberInformasiTerpercaya: String = "Posyandu"
)

// ===========================
// Local Recipe
// ===========================
data class LocalRecipe(
    val id: Int = 0,
    @SerializedName("nama_resep") val namaResep: String = "",
    @SerializedName("bahan_lokal_utama") val bahanLokalUtama: String = "",
    @SerializedName("usia_rekomendasi_bulan") val usiaRekomendasiBulan: String = "",
    @SerializedName("deskripsi_gizi") val deskripsiGizi: String = "",
    @SerializedName("langkah_memasak_json") val langkahMemasakJson: List<String> = emptyList(),
    @SerializedName("video_url") val videoUrl: String? = null
)

// ===========================
// Generic API Response
// ===========================
data class ApiResponse<T>(
    val status: String,
    val message: String?,
    val data: T?
)
data class ApiListResponse<T>(
    val status: String,
    val data: List<T>?
)
