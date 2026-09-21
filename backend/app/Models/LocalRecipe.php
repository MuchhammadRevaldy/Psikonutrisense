<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class LocalRecipe extends Model
{
    use HasFactory;

    protected $fillable = [
        'nama_resep',
        'bahan_lokal_utama',
        'usia_rekomendasi_bulan',
        'deskripsi_gizi',
        'langkah_memasak_json',
        'video_url'
    ];

    protected $casts = [
        'langkah_memasak_json' => 'array',
    ];
}
