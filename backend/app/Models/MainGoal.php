<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class MainGoal extends Model
{
    use HasFactory;

    protected $fillable = [
        'mother_id',
        'tujuan_utama',
        'harapan_untuk_anak',
        'bersedia_ikut_intervensi'
    ];

    public function mother()
    {
        return $this->belongsTo(Mother::class);
    }
}
