<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class HealthCondition extends Model
{
    use HasFactory;

    protected $fillable = [
        'child_id',
        'pernah_dirawat_rs',
        'sering_sakit_3bulan',
        'alergi_makanan_obat',
        'keterangan_alergi',
        'status_imunisasi_ringkasan'
    ];

    public function child()
    {
        return $this->belongsTo(Child::class);
    }
}
