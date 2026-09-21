<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    public function up(): void
    {
        Schema::create('main_goals', function (Blueprint $table) {
            $table->id();
            $table->foreignId('mother_id')->constrained('mothers')->onDelete('cascade');
            $table->string('tujuan_utama', 100);
            $table->text('harapan_untuk_anak')->nullable();
            $table->boolean('bersedia_ikut_intervensi')->default(true);
            $table->timestamps();
        });
    }

    public function down(): void
    {
        Schema::dropIfExists('main_goals');
    }
};
