<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    public function up(): void
    {
        Schema::table('mother_wellbeing_screenings', function (Blueprint $table) {
            $table->smallInteger('skor_cemas_berlebihan')->nullable();
            $table->smallInteger('skor_mudah_lelah')->nullable();
            $table->smallInteger('skor_sedih_tanpa_sebab')->nullable();
            $table->smallInteger('dukungan_emosional')->nullable();
            $table->smallInteger('dukungan_informasi')->nullable();
            $table->smallInteger('dukungan_praktis')->nullable();
            $table->dropColumn(['epds_skor_5item', 'skor_dukungan_keluarga']);
        });

        DB::statement('ALTER TABLE mother_wellbeing_screenings ADD CONSTRAINT chk_skor_cemas CHECK (skor_cemas_berlebihan BETWEEN 0 AND 4)');
        DB::statement('ALTER TABLE mother_wellbeing_screenings ADD CONSTRAINT chk_skor_lelah CHECK (skor_mudah_lelah BETWEEN 0 AND 4)');
        DB::statement('ALTER TABLE mother_wellbeing_screenings ADD CONSTRAINT chk_skor_sedih CHECK (skor_sedih_tanpa_sebab BETWEEN 0 AND 4)');
        DB::statement('ALTER TABLE mother_wellbeing_screenings ADD CONSTRAINT chk_dukungan_emosional CHECK (dukungan_emosional BETWEEN 0 AND 4)');
        DB::statement('ALTER TABLE mother_wellbeing_screenings ADD CONSTRAINT chk_dukungan_informasi CHECK (dukungan_informasi BETWEEN 0 AND 4)');
        DB::statement('ALTER TABLE mother_wellbeing_screenings ADD CONSTRAINT chk_dukungan_praktis CHECK (dukungan_praktis BETWEEN 0 AND 4)');
    }

    public function down(): void
    {
        Schema::table('mother_wellbeing_screenings', function (Blueprint $table) {
            $table->dropColumn([
                'skor_cemas_berlebihan', 'skor_mudah_lelah', 'skor_sedih_tanpa_sebab',
                'dukungan_emosional', 'dukungan_informasi', 'dukungan_praktis',
            ]);
            $table->integer('epds_skor_5item')->default(0);
            $table->integer('skor_dukungan_keluarga')->nullable();
        });
    }
};
