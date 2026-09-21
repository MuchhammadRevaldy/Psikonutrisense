<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    public function up(): void
    {
        Schema::create('health_conditions', function (Blueprint $table) {
            $table->id();
            $table->foreignId('child_id')->constrained('children')->onDelete('cascade');
            $table->boolean('pernah_dirawat_rs')->default(false);
            $table->boolean('sering_sakit_3bulan')->default(false);
            $table->boolean('alergi_makanan_obat')->default(false);
            $table->string('keterangan_alergi', 255)->nullable();
            $table->string('status_imunisasi_ringkasan', 30)->default('Lengkap');
            $table->timestamps();
        });
    }

    public function down(): void
    {
        Schema::dropIfExists('health_conditions');
    }
};
