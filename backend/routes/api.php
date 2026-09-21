<?php

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;
use App\Http\Controllers\AuthController;
use App\Http\Controllers\MotherController;
use App\Http\Controllers\ChildController;
use App\Http\Controllers\GrowthRecordController;
use App\Http\Controllers\VaccinationRecordController;
use App\Http\Controllers\PmtLogController;
use App\Http\Controllers\MotherWellbeingController;
use App\Http\Controllers\LocalRecipeController;
use App\Http\Controllers\NutritionRecordController;

/*
|--------------------------------------------------------------------------
| API Routes - Psikonutrisense Backend
|--------------------------------------------------------------------------
*/

// ======== AUTH ROUTES (Public) ========
Route::prefix('auth')->group(function () {
    Route::post('/register', [AuthController::class, 'register']);
    Route::post('/login', [AuthController::class, 'login']);
    Route::post('/logout', [AuthController::class, 'logout'])->middleware('auth.token');
    Route::get('/me', [AuthController::class, 'me'])->middleware('auth.token');
});

// ======== PROTECTED ROUTES ========
Route::middleware('auth.token')->group(function () {
    // Modul A: Profil Ibu & Balita
    Route::apiResource('mothers', MotherController::class);
    Route::apiResource('children', ChildController::class);

    // Modul B: Antropometri & Kurva Tumbuh Kembang WHO
    Route::apiResource('growth-records', GrowthRecordController::class);

    // Modul C: Imunisasi Digital Buku KIA
    Route::apiResource('vaccination-records', VaccinationRecordController::class);

    // Modul D: Log Harian PMT, Gizi & Resep Pangan Lokal
    Route::apiResource('pmt-logs', PmtLogController::class);
    Route::apiResource('nutrition-records', NutritionRecordController::class);
    Route::apiResource('local-recipes', LocalRecipeController::class);

    // Modul E: Barometer Kesejahteraan Psikososial Ibu
    Route::apiResource('wellbeing-screenings', MotherWellbeingController::class);
});
