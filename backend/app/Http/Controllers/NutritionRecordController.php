<?php

namespace App\Http\Controllers;

use App\Http\Controllers\Concerns\ScopesToOwnedChildren;
use App\Models\NutritionRecord;
use Illuminate\Http\Request;

class NutritionRecordController extends Controller
{
    use ScopesToOwnedChildren;

    // GET /api/nutrition-records
    public function index(Request $request)
    {
        $childIds = $this->ownedChildIds($request);

        $query = NutritionRecord::with('child')->whereIn('child_id', $childIds);
        if ($request->has('child_id')) {
            $query->where('child_id', $request->child_id);
        }

        return response()->json(['status' => 'success', 'data' => $query->get()], 200);
    }

    // POST /api/nutrition-records
    public function store(Request $request)
    {
        $validated = $request->validate([
            'child_id' => 'required|exists:children,id',
            'asi_eksklusif' => 'required|boolean',
            'masih_menyusui' => 'required|boolean',
            'usia_mulai_mpasi_bulan' => 'required|integer',
            'jenis_mpasi_json' => 'nullable|array',
            'frekuensi_makan_per_hari' => 'required|integer',
            'frekuensi_camilan_per_hari' => 'required|integer',
            'recall_24jam_menu' => 'nullable|string',
            'kendala_pemberian_makan' => 'nullable|string',
        ]);

        if (!$this->ownedChildIds($request)->contains((int) $validated['child_id'])) {
            return response()->json(['status' => 'error', 'message' => 'Anda tidak berhak mengakses data anak ini.'], 403);
        }

        $record = NutritionRecord::create($validated);
        return response()->json(['status' => 'success', 'message' => 'Data menyusui & MPASI berhasil disimpan.', 'data' => $record], 201);
    }

    // GET /api/nutrition-records/{id}
    public function show(Request $request, $id)
    {
        $record = NutritionRecord::with('child')->find($id);
        if (!$record) {
            return response()->json(['status' => 'error', 'message' => 'Data tidak ditemukan.'], 404);
        }
        if (!$this->ownedChildIds($request)->contains($record->child_id)) {
            return response()->json(['status' => 'error', 'message' => 'Anda tidak berhak mengakses data ini.'], 403);
        }

        return response()->json(['status' => 'success', 'data' => $record], 200);
    }

    // PUT/PATCH /api/nutrition-records/{id}
    public function update(Request $request, $id)
    {
        $record = NutritionRecord::find($id);
        if (!$record) {
            return response()->json(['status' => 'error', 'message' => 'Data tidak ditemukan.'], 404);
        }
        if (!$this->ownedChildIds($request)->contains($record->child_id)) {
            return response()->json(['status' => 'error', 'message' => 'Anda tidak berhak mengakses data ini.'], 403);
        }

        $validated = $request->validate([
            'asi_eksklusif' => 'sometimes|boolean',
            'masih_menyusui' => 'sometimes|boolean',
            'usia_mulai_mpasi_bulan' => 'sometimes|integer',
            'jenis_mpasi_json' => 'nullable|array',
            'frekuensi_makan_per_hari' => 'sometimes|integer',
            'frekuensi_camilan_per_hari' => 'sometimes|integer',
            'recall_24jam_menu' => 'nullable|string',
            'kendala_pemberian_makan' => 'nullable|string',
        ]);

        $record->update($validated);
        return response()->json(['status' => 'success', 'message' => 'Data menyusui & MPASI diperbarui.', 'data' => $record], 200);
    }

    // DELETE /api/nutrition-records/{id}
    public function destroy(Request $request, $id)
    {
        $record = NutritionRecord::find($id);
        if (!$record) {
            return response()->json(['status' => 'error', 'message' => 'Data tidak ditemukan.'], 404);
        }
        if (!$this->ownedChildIds($request)->contains($record->child_id)) {
            return response()->json(['status' => 'error', 'message' => 'Anda tidak berhak mengakses data ini.'], 403);
        }

        $record->delete();
        return response()->json(['status' => 'success', 'message' => 'Data gizi dihapus.'], 200);
    }
}
