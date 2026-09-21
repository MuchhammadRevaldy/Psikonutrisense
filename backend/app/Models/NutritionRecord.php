<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class NutritionRecord extends Model
{
    use HasFactory;

    protected $fillable = [
        'child_id',
        'asi_eksklusif',
        'masih_menyusui',
        'usia_mulai_mpasi_bulan',
        'jenis_mpasi_json',
        'frekuensi_makan_per_hari',
        'frekuensi_camilan_per_hari',
        'recall_24jam_menu',
        'kendala_pemberian_makan'
    ];

    protected $casts = [
        'jenis_mpasi_json' => 'array',
    ];

    public function child()
    {
        return $this->belongsTo(Child::class);
    }
}
