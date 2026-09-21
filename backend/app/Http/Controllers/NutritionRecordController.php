<?php

namespace App\Http\Controllers;

use App\Models\NutritionRecord;
use Illuminate\Http\Request;

class NutritionRecordController extends Controller
{
    public function index(Request $request)
    {
        $query = NutritionRecord::with('child');
        if ($request->has('child_id')) {
            $query->where('child_id', $request->child_id);
        }
        return response()->json(['status' => 'success', 'data' => $query->get()], 200);
    }

    public function store(Request $request)
    {
        $validated = $request->validate([
            'child_id' => 'required|exists:children,id',
            'asi_eksklusif' => 'required|boolean',
            'usia_mulai_mpasi_bulan' => 'required|integer',
            'frekuensi_makan_per_hari' => 'required|integer',
            'frekuensi_protein_hewani_per_minggu' => 'required|integer',
            'recall_24jam_menu' => 'nullable|string',
            'pangan_lokal_favorit' => 'nullable|string',
            'kendala_pemberian_makan' => 'nullable|string',
        ]);
        $record = NutritionRecord::create($validated);
        return response()->json(['status' => 'success', 'message' => 'Data gizi berhasil disimpan.', 'data' => $record], 201);
    }

    public function show($id)
    {
        $record = NutritionRecord::with('child')->find($id);
        if (!$record) return response()->json(['status' => 'error', 'message' => 'Data tidak ditemukan.'], 404);
        return response()->json(['status' => 'success', 'data' => $record], 200);
    }

    public function update(Request $request, $id)
    {
        $record = NutritionRecord::find($id);
        if (!$record) return response()->json(['status' => 'error', 'message' => 'Data tidak ditemukan.'], 404);
        $record->update($request->all());
        return response()->json(['status' => 'success', 'message' => 'Data gizi diperbarui.', 'data' => $record], 200);
    }

    public function destroy($id)
    {
        $record = NutritionRecord::find($id);
        if (!$record) return response()->json(['status' => 'error', 'message' => 'Data tidak ditemukan.'], 404);
        $record->delete();
        return response()->json(['status' => 'success', 'message' => 'Data gizi dihapus.'], 200);
    }
}
