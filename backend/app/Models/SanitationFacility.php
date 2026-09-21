<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class SanitationFacility extends Model
{
    use HasFactory;

    protected $fillable = [
        'mother_id',
        'sumber_air_minum',
        'jamban_sehat',
        'jarak_posyandu_km',
        'kepesertaan_bpjs'
    ];

    public function mother()
    {
        return $this->belongsTo(Mother::class);
    }
}
