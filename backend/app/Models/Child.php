<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Child extends Model
{
    use HasFactory;

    protected $fillable = [
        'mother_id',
        'nama_balita',
        'tanggal_lahir_balita',
        'jenis_kelamin',
        'anak_ke',
        'jumlah_saudara',
        'bb_lahir_kg',
        'pb_lahir_cm',
        'lahir_prematur'
    ];

    public function mother()
    {
        return $this->belongsTo(Mother::class);
    }

    public function growthRecords()
    {
        return $this->hasMany(GrowthRecord::class);
    }

    public function vaccinationRecords()
    {
        return $this->hasMany(VaccinationRecord::class);
    }

    public function nutritionRecord()
    {
        return $this->hasOne(NutritionRecord::class);
    }

    public function pmtLogs()
    {
        return $this->hasMany(PmtLog::class);
    }
}
