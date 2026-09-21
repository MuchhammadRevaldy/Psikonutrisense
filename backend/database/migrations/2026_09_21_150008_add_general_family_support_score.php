<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Schema;

/**
 * Design #17 asks a general "saya merasa didukung keluarga" question (0-4),
 * separate from the 3-dimension breakdown (emosional/informasi/praktis) in
 * design #18. The earlier restructure migration only added the 3-dimension
 * columns — this adds the missing general one so #17 doesn't lose a field.
 */
return new class extends Migration
{
    public function up(): void
    {
        Schema::table('mother_wellbeing_screenings', function (Blueprint $table) {
            $table->smallInteger('skor_didukung_keluarga_umum')->nullable();
        });

        DB::statement('ALTER TABLE mother_wellbeing_screenings ADD CONSTRAINT chk_didukung_keluarga_umum CHECK (skor_didukung_keluarga_umum BETWEEN 0 AND 4)');
    }

    public function down(): void
    {
        Schema::table('mother_wellbeing_screenings', function (Blueprint $table) {
            $table->dropColumn('skor_didukung_keluarga_umum');
        });
    }
};
