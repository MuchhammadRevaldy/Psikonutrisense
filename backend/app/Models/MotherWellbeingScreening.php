<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class MotherWellbeingScreening extends Model
{
    use HasFactory;

    protected $fillable = [
        'mother_id',
        'frekuensi_interaksi_bermain',
        'pengasuh_utama',
        'skor_dukungan_keluarga',
        'epds_skor_5item',
        'kategori_kesejahteraan',
        'cukup_informasi_gizi',
        'sumber_informasi_terpercaya'
    ];

    public function mother()
    {
        return $this->belongsTo(Mother::class);
    }
}
