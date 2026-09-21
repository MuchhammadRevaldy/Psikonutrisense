<?php

namespace App\Http\Controllers;

use App\Models\MotherWellbeingScreening;
use Illuminate\Http\Request;

class MotherWellbeingController extends Controller
{
    // GET /api/wellbeing-screenings
    public function index(Request $request)
    {
        $query = MotherWellbeingScreening::with('mother');
        if ($request->has('mother_id')) {
            $query->where('mother_id', $request->mother_id);
        }
        $screenings = $query->orderBy('created_at', 'desc')->get();

        return response()->json([
            'status' => 'success',
            'data' => $screenings
        ], 200);
    }

    // POST /api/wellbeing-screenings
    public function store(Request $request)
    {
        $validated = $request->validate([
            'mother_id' => 'required|exists:mothers,id',
            'frekuensi_interaksi_bermain' => 'required|integer|between:1,5',
            'pengasuh_utama' => 'required|string',
            'skor_dukungan_keluarga' => 'required|integer|between:1,5',
            'epds_skor_5item' => 'required|integer',
            'kategori_kesejahteraan' => 'nullable|string',
            'cukup_informasi_gizi' => 'required|in:Ya,Tidak,Ragu',
            'sumber_informasi_terpercaya' => 'required|string'
        ]);

        $screening = MotherWellbeingScreening::create($validated);

        return response()->json([
            'status' => 'success',
            'message' => 'Skrining Barometer Kesejahteraan Ibu berhasil disimpan.',
            'data' => $screening
        ], 201);
    }

    // GET /api/wellbeing-screenings/{id}
    public function show($id)
    {
        $screening = MotherWellbeingScreening::with('mother')->find($id);
        if (!$screening) {
            return response()->json(['status' => 'error', 'message' => 'Data skrining tidak ditemukan.'], 404);
        }

        return response()->json(['status' => 'success', 'data' => $screening], 200);
    }

    // PUT/PATCH /api/wellbeing-screenings/{id}
    public function update(Request $request, $id)
    {
        $screening = MotherWellbeingScreening::find($id);
        if (!$screening) {
            return response()->json(['status' => 'error', 'message' => 'Data skrining tidak ditemukan.'], 404);
        }

        $validated = $request->validate([
            'frekuensi_interaksi_bermain' => 'sometimes|integer|between:1,5',
            'pengasuh_utama' => 'sometimes|string',
            'skor_dukungan_keluarga' => 'sometimes|integer|between:1,5',
            'epds_skor_5item' => 'sometimes|integer',
            'kategori_kesejahteraan' => 'nullable|string',
            'cukup_informasi_gizi' => 'sometimes|in:Ya,Tidak,Ragu',
            'sumber_informasi_terpercaya' => 'sometimes|string'
        ]);

        $screening->update($validated);

        return response()->json([
            'status' => 'success',
            'message' => 'Data skrining kesejahteraan berhasil diperbarui.',
            'data' => $screening
        ], 200);
    }

    // DELETE /api/wellbeing-screenings/{id}
    public function destroy($id)
    {
        $screening = MotherWellbeingScreening::find($id);
        if (!$screening) {
            return response()->json(['status' => 'error', 'message' => 'Data skrining tidak ditemukan.'], 404);
        }

        $screening->delete();

        return response()->json([
            'status' => 'success',
            'message' => 'Data skrining berhasil dihapus.'
        ], 200);
    }
}
