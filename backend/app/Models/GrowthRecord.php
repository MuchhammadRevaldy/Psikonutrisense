<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class GrowthRecord extends Model
{
    use HasFactory;

    protected $fillable = [
        'child_id',
        'tanggal_penimbangan',
        'berat_badan_kg',
        'tinggi_badan_cm',
        'lingkar_kepala_cm',
        'lila_cm',
        'status_penimbangan',
        'status_gizi',
        'zscore_bbu',
        'zscore_tbu',
        'kpsp_checklist_json'
    ];

    protected $casts = [
        'kpsp_checklist_json' => 'array',
    ];

    public function child()
    {
        return $this->belongsTo(Child::class);
    }
}
