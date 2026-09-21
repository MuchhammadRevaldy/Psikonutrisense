<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class VaccinationRecord extends Model
{
    use HasFactory;

    protected $fillable = [
        'child_id',
        'jenis_imunisasi',
        'tanggal_jadwal',
        'tanggal_pemberian',
        'status',
        'alasan_belum'
    ];

    public function child()
    {
        return $this->belongsTo(Child::class);
    }
}
