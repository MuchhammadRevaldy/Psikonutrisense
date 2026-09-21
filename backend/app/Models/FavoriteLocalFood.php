<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class FavoriteLocalFood extends Model
{
    use HasFactory;

    protected $table = 'favorite_local_foods';

    protected $fillable = [
        'child_id',
        'bahan_json',
        'bahan_lainnya'
    ];

    protected $casts = [
        'bahan_json' => 'array',
    ];

    public function child()
    {
        return $this->belongsTo(Child::class);
    }
}
