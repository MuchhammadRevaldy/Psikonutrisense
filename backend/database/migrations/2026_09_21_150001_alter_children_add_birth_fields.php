<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    public function up(): void
    {
        Schema::table('children', function (Blueprint $table) {
            $table->string('jenis_persalinan', 20)->nullable()->after('lahir_prematur');
            $table->integer('usia_kehamilan_minggu')->nullable()->after('jenis_persalinan');
            $table->boolean('status_kepemilikan_kia')->default(false)->after('usia_kehamilan_minggu');
        });
    }

    public function down(): void
    {
        Schema::table('children', function (Blueprint $table) {
            $table->dropColumn(['jenis_persalinan', 'usia_kehamilan_minggu', 'status_kepemilikan_kia']);
        });
    }
};
