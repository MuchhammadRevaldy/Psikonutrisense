<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    public function up(): void
    {
        Schema::create('food_group_frequencies', function (Blueprint $table) {
            $table->id();
            $table->foreignId('child_id')->constrained('children')->onDelete('cascade');
            $table->date('tanggal_pencatatan');
            $table->string('nasi_bubur', 20);
            $table->string('lauk_hewani', 20);
            $table->string('lauk_nabati', 20);
            $table->string('sayur', 20);
            $table->string('buah', 20);
            $table->string('susu', 20);
            $table->timestamps();
        });
    }

    public function down(): void
    {
        Schema::dropIfExists('food_group_frequencies');
    }
};
