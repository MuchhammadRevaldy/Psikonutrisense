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
        'skor_cemas_berlebihan',
        'skor_mudah_lelah',
        'skor_sedih_tanpa_sebab',
        'skor_didukung_keluarga_umum',
        'dukungan_emosional',
        'dukungan_informasi',
        'dukungan_praktis',
        'kategori_kesejahteraan',
        'cukup_informasi_gizi',
        'sumber_informasi_terpercaya'
    ];

    public function mother()
    {
        return $this->belongsTo(Mother::class);
    }
}
