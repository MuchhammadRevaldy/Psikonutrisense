<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    public function up(): void
    {
        Schema::table('nutrition_records', function (Blueprint $table) {
            $table->boolean('masih_menyusui')->default(true)->after('asi_eksklusif');
            $table->jsonb('jenis_mpasi_json')->nullable()->after('usia_mulai_mpasi_bulan');
            $table->integer('frekuensi_camilan_per_hari')->default(0)->after('frekuensi_makan_per_hari');
            $table->dropColumn(['pangan_lokal_favorit', 'frekuensi_protein_hewani_per_minggu']);
        });
    }

    public function down(): void
    {
        Schema::table('nutrition_records', function (Blueprint $table) {
            $table->dropColumn(['masih_menyusui', 'jenis_mpasi_json', 'frekuensi_camilan_per_hari']);
            $table->string('pangan_lokal_favorit', 255)->nullable();
            $table->integer('frekuensi_protein_hewani_per_minggu')->default(7);
        });
    }
};
