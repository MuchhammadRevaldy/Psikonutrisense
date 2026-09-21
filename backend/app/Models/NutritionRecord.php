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
        'usia_mulai_mpasi_bulan',
        'frekuensi_makan_per_hari',
        'frekuensi_protein_hewani_per_minggu',
        'recall_24jam_menu',
        'pangan_lokal_favorit',
        'kendala_pemberian_makan'
    ];

    public function child()
    {
        return $this->belongsTo(Child::class);
    }
}
