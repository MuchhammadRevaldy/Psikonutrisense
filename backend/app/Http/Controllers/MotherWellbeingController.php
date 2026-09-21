<?php

namespace App\Http\Controllers;

use App\Http\Controllers\Concerns\ScopesToOwnedChildren;
use App\Models\MotherWellbeingScreening;
use Illuminate\Http\Request;

class MotherWellbeingController extends Controller
{
    use ScopesToOwnedChildren;

    // GET /api/wellbeing-screenings
    public function index(Request $request)
    {
        $motherIds = $this->ownedMotherIds($request);

        $query = MotherWellbeingScreening::with('mother')->whereIn('mother_id', $motherIds);
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
            'skor_cemas_berlebihan' => 'required|integer|between:0,4',
            'skor_mudah_lelah' => 'required|integer|between:0,4',
            'skor_sedih_tanpa_sebab' => 'required|integer|between:0,4',
            'skor_didukung_keluarga_umum' => 'required|integer|between:0,4',
            'dukungan_emosional' => 'required|integer|between:0,4',
            'dukungan_informasi' => 'required|integer|between:0,4',
            'dukungan_praktis' => 'required|integer|between:0,4',
            'kategori_kesejahteraan' => 'nullable|string',
            'cukup_informasi_gizi' => 'required|in:Ya,Tidak,Ragu',
            'sumber_informasi_terpercaya' => 'required|string'
        ]);

        if (!$this->ownedMotherIds($request)->contains((int) $validated['mother_id'])) {
            return response()->json(['status' => 'error', 'message' => 'Anda tidak berhak mengakses data ibu ini.'], 403);
        }

        $screening = MotherWellbeingScreening::create($validated);

        return response()->json([
            'status' => 'success',
            'message' => 'Skrining Kondisi Psikososial Ibu berhasil disimpan.',
            'data' => $screening
        ], 201);
    }

    // GET /api/wellbeing-screenings/{id}
    public function show(Request $request, $id)
    {
        $screening = MotherWellbeingScreening::with('mother')->find($id);
        if (!$screening) {
            return response()->json(['status' => 'error', 'message' => 'Data skrining tidak ditemukan.'], 404);
        }
        if (!$this->ownedMotherIds($request)->contains($screening->mother_id)) {
            return response()->json(['status' => 'error', 'message' => 'Anda tidak berhak mengakses data ini.'], 403);
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
        if (!$this->ownedMotherIds($request)->contains($screening->mother_id)) {
            return response()->json(['status' => 'error', 'message' => 'Anda tidak berhak mengakses data ini.'], 403);
        }

        $validated = $request->validate([
            'frekuensi_interaksi_bermain' => 'sometimes|integer|between:1,5',
            'pengasuh_utama' => 'sometimes|string',
            'skor_cemas_berlebihan' => 'sometimes|integer|between:0,4',
            'skor_mudah_lelah' => 'sometimes|integer|between:0,4',
            'skor_sedih_tanpa_sebab' => 'sometimes|integer|between:0,4',
            'skor_didukung_keluarga_umum' => 'sometimes|integer|between:0,4',
            'dukungan_emosional' => 'sometimes|integer|between:0,4',
            'dukungan_informasi' => 'sometimes|integer|between:0,4',
            'dukungan_praktis' => 'sometimes|integer|between:0,4',
            'kategori_kesejahteraan' => 'nullable|string',
            'cukup_informasi_gizi' => 'sometimes|in:Ya,Tidak,Ragu',
            'sumber_informasi_terpercaya' => 'sometimes|string'
        ]);

        $screening->update($validated);

        return response()->json([
            'status' => 'success',
            'message' => 'Data Kondisi Psikososial Ibu berhasil diperbarui.',
            'data' => $screening
        ], 200);
    }

    // DELETE /api/wellbeing-screenings/{id}
    public function destroy(Request $request, $id)
    {
        $screening = MotherWellbeingScreening::find($id);
        if (!$screening) {
            return response()->json(['status' => 'error', 'message' => 'Data skrining tidak ditemukan.'], 404);
        }
        if (!$this->ownedMotherIds($request)->contains($screening->mother_id)) {
            return response()->json(['status' => 'error', 'message' => 'Anda tidak berhak mengakses data ini.'], 403);
        }

        $screening->delete();

        return response()->json([
            'status' => 'success',
            'message' => 'Data skrining berhasil dihapus.'
        ], 200);
    }
}
