<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Mother extends Model
{
    use HasFactory;

    protected $fillable = [
        'user_id',
        'nama_ibu',
        'nik_ibu',
        'tanggal_lahir_ibu',
        'pendidikan_terakhir',
        'pekerjaan',
        'alamat_dusun_rt_rw',
        'no_hp_whatsapp',
        'foto_buku_kia',
        'poin_ibu_aktif',
        'lencana_aktif'
    ];

    public function children()
    {
        return $this->hasMany(Child::class);
    }

    public function wellbeingScreenings()
    {
        return $this->hasMany(MotherWellbeingScreening::class);
    }

    public function sanitationFacility()
    {
        return $this->hasOne(SanitationFacility::class);
    }
}
