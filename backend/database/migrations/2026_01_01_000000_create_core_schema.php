<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Schema;

/**
 * Base schema for a fresh database, matching backend/database/schema_psikonutrisense.sql
 * in its original (pre-restructure) form. Later migrations in this folder alter these
 * tables incrementally (birth fields on children, nutrition_records restructure, the
 * wellbeing score breakdown, and the new health_conditions/main_goals/food_group_frequencies/
 * favorite_local_foods tables) — replaying that history is what brings a fresh database
 * to the current shape.
 */
return new class extends Migration
{
    public function up(): void
    {
        Schema::create('users', function (Blueprint $table) {
            $table->id();
            $table->string('name');
            $table->string('email')->unique();
            $table->string('password');
            $table->string('role', 50)->default('ibu');
            $table->timestamps();
        });

        if (!Schema::hasTable('personal_access_tokens')) {
            Schema::create('personal_access_tokens', function (Blueprint $table) {
                $table->id();
                $table->morphs('tokenable');
                $table->string('name');
                $table->string('token', 64)->unique();
                $table->text('abilities')->nullable();
                $table->timestamp('last_used_at')->nullable();
                $table->timestamp('expires_at')->nullable();
                $table->timestamps();
            });
        }

        Schema::create('mothers', function (Blueprint $table) {
            $table->id();
            $table->foreignId('user_id')->nullable()->constrained('users')->nullOnDelete();
            $table->string('nama_ibu');
            $table->string('nik_ibu', 16)->unique();
            $table->date('tanggal_lahir_ibu');
            $table->string('pendidikan_terakhir', 50);
            $table->string('pekerjaan', 100);
            $table->string('alamat_dusun_rt_rw');
            $table->string('no_hp_whatsapp', 20);
            $table->string('foto_buku_kia')->nullable();
            $table->integer('poin_ibu_aktif')->default(1250);
            $table->boolean('lencana_aktif')->default(true);
            $table->timestamps();
        });

        Schema::create('children', function (Blueprint $table) {
            $table->id();
            $table->foreignId('mother_id')->constrained('mothers')->cascadeOnDelete();
            $table->string('nama_balita');
            $table->date('tanggal_lahir_balita');
            $table->string('jenis_kelamin', 1);
            $table->integer('anak_ke')->default(1);
            $table->integer('jumlah_saudara')->default(0);
            $table->decimal('bb_lahir_kg', 4, 2)->nullable();
            $table->decimal('pb_lahir_cm', 4, 2)->nullable();
            $table->boolean('lahir_prematur')->default(false);
            $table->timestamps();
        });

        Schema::create('growth_records', function (Blueprint $table) {
            $table->id();
            $table->foreignId('child_id')->constrained('children')->cascadeOnDelete();
            $table->date('tanggal_penimbangan');
            $table->decimal('berat_badan_kg', 4, 2);
            $table->decimal('tinggi_badan_cm', 4, 2);
            $table->decimal('lingkar_kepala_cm', 4, 2);
            $table->decimal('lila_cm', 4, 2);
            $table->string('status_penimbangan', 50)->default('Naik');
            $table->string('status_gizi', 50)->default('Normal');
            $table->decimal('zscore_bbu', 4, 2)->default(0.20);
            $table->decimal('zscore_tbu', 4, 2)->default(0.10);
            $table->jsonb('kpsp_checklist_json')->nullable();
            $table->timestamps();
        });

        Schema::create('vaccination_records', function (Blueprint $table) {
            $table->id();
            $table->foreignId('child_id')->constrained('children')->cascadeOnDelete();
            $table->string('jenis_imunisasi', 100);
            $table->date('tanggal_jadwal');
            $table->date('tanggal_pemberian')->nullable();
            $table->string('status', 50)->default('Belum');
            $table->text('alasan_belum')->nullable();
            $table->timestamps();
        });

        Schema::create('nutrition_records', function (Blueprint $table) {
            $table->id();
            $table->foreignId('child_id')->constrained('children')->cascadeOnDelete();
            $table->boolean('asi_eksklusif')->default(true);
            $table->integer('usia_mulai_mpasi_bulan')->default(6);
            $table->integer('frekuensi_makan_per_hari')->default(3);
            $table->integer('frekuensi_protein_hewani_per_minggu')->default(7);
            $table->text('recall_24jam_menu')->nullable();
            $table->string('pangan_lokal_favorit')->nullable();
            $table->string('kendala_pemberian_makan')->nullable();
            $table->timestamps();
        });

        Schema::create('pmt_logs', function (Blueprint $table) {
            $table->id();
            $table->foreignId('child_id')->constrained('children')->cascadeOnDelete();
            $table->date('tanggal');
            $table->string('status_konsumsi', 50);
            $table->string('qr_code_scanned')->nullable();
            $table->timestamps();
        });

        Schema::create('mother_wellbeing_screenings', function (Blueprint $table) {
            $table->id();
            $table->foreignId('mother_id')->constrained('mothers')->cascadeOnDelete();
            $table->integer('frekuensi_interaksi_bermain')->nullable();
            $table->string('pengasuh_utama', 100)->default('Ibu Kandung');
            $table->integer('skor_dukungan_keluarga')->nullable();
            $table->integer('epds_skor_5item')->default(0);
            $table->string('kategori_kesejahteraan', 100)->default('Tenang & Bahagia');
            $table->string('cukup_informasi_gizi', 20)->default('Ya');
            $table->string('sumber_informasi_terpercaya', 100)->default('Posyandu');
            $table->timestamps();
        });

        Schema::create('sanitation_facilities', function (Blueprint $table) {
            $table->id();
            $table->foreignId('mother_id')->constrained('mothers')->cascadeOnDelete();
            $table->string('sumber_air_minum', 100);
            $table->boolean('jamban_sehat')->default(true);
            $table->decimal('jarak_posyandu_km', 4, 2)->default(0.5);
            $table->boolean('kepesertaan_bpjs')->default(true);
            $table->timestamps();
        });

        Schema::create('local_recipes', function (Blueprint $table) {
            $table->id();
            $table->string('nama_resep');
            $table->string('bahan_lokal_utama');
            $table->string('usia_rekomendasi_bulan', 50);
            $table->text('deskripsi_gizi');
            $table->jsonb('langkah_memasak_json');
            $table->string('video_url')->nullable();
            $table->timestamps();
        });

        DB::table('local_recipes')->insert([
            [
                'nama_resep' => 'Puree Kelor Lele Ngeposari',
                'bahan_lokal_utama' => 'Daun Kelor, Ikan Lele, Telur',
                'usia_rekomendasi_bulan' => '6-8 Bulan',
                'deskripsi_gizi' => 'Kaya akan Protein Hewani, Zat Besi & Vitamin A untuk mencegah stunting.',
                'langkah_memasak_json' => json_encode(['Kukus ikan lele dan ambil dagingnya tanpa duri', 'Rebus daun kelor sebentar', 'Haluskan bersama nasi tim dan telur rebus']),
                'created_at' => now(),
                'updated_at' => now(),
            ],
            [
                'nama_resep' => 'Sup Tim Tempe Telur Kelor',
                'bahan_lokal_utama' => 'Tempe, Telur Ayam, Daun Kelor',
                'usia_rekomendasi_bulan' => '9-11 Bulan',
                'deskripsi_gizi' => 'Tinggi kalori dan lemak sehat untuk mengoptimalkan BB balita.',
                'langkah_memasak_json' => json_encode(['Cincang halus tempe dan telur', 'Tumis dengan sedikit minyak kelapa', 'Tambahkan kaldu dan daun kelor cincang']),
                'created_at' => now(),
                'updated_at' => now(),
            ],
        ]);
    }

    public function down(): void
    {
        Schema::dropIfExists('local_recipes');
        Schema::dropIfExists('sanitation_facilities');
        Schema::dropIfExists('mother_wellbeing_screenings');
        Schema::dropIfExists('pmt_logs');
        Schema::dropIfExists('nutrition_records');
        Schema::dropIfExists('vaccination_records');
        Schema::dropIfExists('growth_records');
        Schema::dropIfExists('children');
        Schema::dropIfExists('mothers');
        Schema::dropIfExists('personal_access_tokens');
        Schema::dropIfExists('users');
    }
};
