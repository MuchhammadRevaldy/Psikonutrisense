-- PostgreSQL Database Schema for Psikonutrisense
-- Database: psikonutrisense_db

DROP TABLE IF EXISTS local_recipes CASCADE;
DROP TABLE IF EXISTS sanitation_facilities CASCADE;
DROP TABLE IF EXISTS mother_wellbeing_screenings CASCADE;
DROP TABLE IF EXISTS pmt_logs CASCADE;
DROP TABLE IF EXISTS nutrition_records CASCADE;
DROP TABLE IF EXISTS vaccination_records CASCADE;
DROP TABLE IF EXISTS growth_records CASCADE;
DROP TABLE IF EXISTS children CASCADE;
DROP TABLE IF EXISTS mothers CASCADE;
DROP TABLE IF EXISTS users CASCADE;

-- 1. Users Table
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) DEFAULT 'ibu',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 2. Mothers Table (Modul A & E)
CREATE TABLE mothers (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id) ON DELETE SET NULL,
    nama_ibu VARCHAR(255) NOT NULL,
    nik_ibu VARCHAR(16) UNIQUE NOT NULL,
    tanggal_lahir_ibu DATE NOT NULL,
    pendidikan_terakhir VARCHAR(50) NOT NULL,
    pekerjaan VARCHAR(100) NOT NULL,
    alamat_dusun_rt_rw VARCHAR(255) NOT NULL,
    no_hp_whatsapp VARCHAR(20) NOT NULL,
    foto_buku_kia VARCHAR(255) NULL,
    poin_ibu_aktif INT DEFAULT 1250,
    lencana_aktif BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 3. Children Table (Modul A)
CREATE TABLE children (
    id BIGSERIAL PRIMARY KEY,
    mother_id BIGINT NOT NULL REFERENCES mothers(id) ON DELETE CASCADE,
    nama_balita VARCHAR(255) NOT NULL,
    tanggal_lahir_balita DATE NOT NULL,
    jenis_kelamin VARCHAR(1) NOT NULL CHECK (jenis_kelamin IN ('L', 'P')),
    anak_ke INT NOT NULL DEFAULT 1,
    jumlah_saudara INT NOT NULL DEFAULT 0,
    bb_lahir_kg NUMERIC(4,2) NULL,
    pb_lahir_cm NUMERIC(4,2) NULL,
    lahir_prematur BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 4. Growth Records Table (Modul B - Antropometri & Kurva WHO)
CREATE TABLE growth_records (
    id BIGSERIAL PRIMARY KEY,
    child_id BIGINT NOT NULL REFERENCES children(id) ON DELETE CASCADE,
    tanggal_penimbangan DATE NOT NULL,
    berat_badan_kg NUMERIC(4,2) NOT NULL,
    tinggi_badan_cm NUMERIC(4,2) NOT NULL,
    lingkar_kepala_cm NUMERIC(4,2) NOT NULL,
    lila_cm NUMERIC(4,2) NOT NULL,
    status_penimbangan VARCHAR(50) DEFAULT 'Naik',
    status_gizi VARCHAR(50) DEFAULT 'Normal',
    zscore_bbu NUMERIC(4,2) DEFAULT 0.20,
    zscore_tbu NUMERIC(4,2) DEFAULT 0.10,
    kpsp_checklist_json JSONB NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 5. Vaccination Records Table (Modul C - Imunisasi Buku KIA)
CREATE TABLE vaccination_records (
    id BIGSERIAL PRIMARY KEY,
    child_id BIGINT NOT NULL REFERENCES children(id) ON DELETE CASCADE,
    jenis_imunisasi VARCHAR(100) NOT NULL,
    tanggal_jadwal DATE NOT NULL,
    tanggal_pemberian DATE NULL,
    status VARCHAR(50) DEFAULT 'Belum' CHECK (status IN ('Sudah', 'Belum', 'Mendekati', 'Terlambat')),
    alasan_belum TEXT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 6. Nutrition Records Table (Modul D - Gizi & MP-ASI)
CREATE TABLE nutrition_records (
    id BIGSERIAL PRIMARY KEY,
    child_id BIGINT NOT NULL REFERENCES children(id) ON DELETE CASCADE,
    asi_eksklusif BOOLEAN DEFAULT TRUE,
    usia_mulai_mpasi_bulan INT NOT NULL DEFAULT 6,
    frekuensi_makan_per_hari INT NOT NULL DEFAULT 3,
    frekuensi_protein_hewani_per_minggu INT NOT NULL DEFAULT 7,
    recall_24jam_menu TEXT NULL,
    pangan_lokal_favorit VARCHAR(255) NULL,
    kendala_pemberian_makan VARCHAR(255) NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 7. PMT Logs Table (Modul D - Modul Harian PMT)
CREATE TABLE pmt_logs (
    id BIGSERIAL PRIMARY KEY,
    child_id BIGINT NOT NULL REFERENCES children(id) ON DELETE CASCADE,
    tanggal DATE NOT NULL,
    status_konsumsi VARCHAR(50) NOT NULL CHECK (status_konsumsi IN ('Habis', 'Sebagian', 'Tidak')),
    qr_code_scanned VARCHAR(255) NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 8. Mother Wellbeing Screenings Table (Modul E - Barometer Kesejahteraan Ibu)
CREATE TABLE mother_wellbeing_screenings (
    id BIGSERIAL PRIMARY KEY,
    mother_id BIGINT NOT NULL REFERENCES mothers(id) ON DELETE CASCADE,
    frekuensi_interaksi_bermain INT CHECK (frekuensi_interaksi_bermain BETWEEN 1 AND 5),
    pengasuh_utama VARCHAR(100) DEFAULT 'Ibu Kandung',
    skor_dukungan_keluarga INT CHECK (skor_dukungan_keluarga BETWEEN 1 AND 5),
    epds_skor_5item INT DEFAULT 0,
    kategori_kesejahteraan VARCHAR(100) DEFAULT 'Tenang & Bahagia',
    cukup_informasi_gizi VARCHAR(20) DEFAULT 'Ya',
    sumber_informasi_terpercaya VARCHAR(100) DEFAULT 'Posyandu',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 9. Sanitation Facilities Table (Modul F - Sanitasi & Akses Layanan)
CREATE TABLE sanitation_facilities (
    id BIGSERIAL PRIMARY KEY,
    mother_id BIGINT NOT NULL REFERENCES mothers(id) ON DELETE CASCADE,
    sumber_air_minum VARCHAR(100) NOT NULL,
    jamban_sehat BOOLEAN DEFAULT TRUE,
    jarak_posyandu_km NUMERIC(4,2) DEFAULT 0.5,
    kepesertaan_bpjs BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 10. Local Recipes Table (Resep Pangan Lokal Ngeposari)
CREATE TABLE local_recipes (
    id BIGSERIAL PRIMARY KEY,
    nama_resep VARCHAR(255) NOT NULL,
    bahan_lokal_utama VARCHAR(255) NOT NULL,
    usia_rekomendasi_bulan VARCHAR(50) NOT NULL,
    deskripsi_gizi TEXT NOT NULL,
    langkah_memasak_json JSONB NOT NULL,
    video_url VARCHAR(255) NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- SEED INITIAL DATA FOR NGEPOSARI
INSERT INTO local_recipes (nama_resep, bahan_lokal_utama, usia_rekomendasi_bulan, deskripsi_gizi, langkah_memasak_json) VALUES 
('Puree Kelor Lele Ngeposari', 'Daun Kelor, Ikan Lele, Telur', '6-8 Bulan', 'Kaya akan Protein Hewani, Zat Besi & Vitamin A untuk mencegah stunting.', '["Kukus ikan lele dan ambil dagingnya tanpa duri", "Rebus daun kelor sebentar", "Haluskan bersama nasi tim dan telur rebus"]'),
('Sup Tim Tempe Telur Kelor', 'Tempe, Telur Ayam, Daun Kelor', '9-11 Bulan', 'Tinggi kalori dan lemak sehat untuk mengoptimalkan BB balita.', '["Cincang halus tempe dan telur", "Tumis dengan sedikit minyak kelapa", "Tambahkan kaldu dan daun kelor cincang"]');
