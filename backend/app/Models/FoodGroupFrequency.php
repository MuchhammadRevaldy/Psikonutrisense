<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class FoodGroupFrequency extends Model
{
    use HasFactory;

    protected $fillable = [
        'child_id',
        'tanggal_pencatatan',
        'nasi_bubur',
        'lauk_hewani',
        'lauk_nabati',
        'sayur',
        'buah',
        'susu'
    ];

    public function child()
    {
        return $this->belongsTo(Child::class);
    }
}
