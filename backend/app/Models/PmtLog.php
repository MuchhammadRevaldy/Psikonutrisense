<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class PmtLog extends Model
{
    use HasFactory;

    protected $fillable = [
        'child_id',
        'tanggal',
        'status_konsumsi',
        'qr_code_scanned'
    ];

    public function child()
    {
        return $this->belongsTo(Child::class);
    }
}
