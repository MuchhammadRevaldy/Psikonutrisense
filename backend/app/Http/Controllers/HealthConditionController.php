<?php

namespace App\Http\Controllers;

use App\Http\Controllers\Concerns\ScopesToOwnedChildren;
use App\Models\HealthCondition;
use Illuminate\Http\Request;

class HealthConditionController extends Controller
{
    use ScopesToOwnedChildren;

    // GET /api/health-conditions
    public function index(Request $request)
    {
        $childIds = $this->ownedChildIds($request);

        $query = HealthCondition::with('child')->whereIn('child_id', $childIds);
        if ($request->has('child_id')) {
            $query->where('child_id', $request->child_id);
        }

        return response()->json(['status' => 'success', 'data' => $query->get()], 200);
    }

    // POST /api/health-conditions
    public function store(Request $request)
    {
        $validated = $request->validate([
            'child_id' => 'required|exists:children,id',
            'pernah_dirawat_rs' => 'required|boolean',
            'sering_sakit_3bulan' => 'required|boolean',
            'alergi_makanan_obat' => 'required|boolean',
            'keterangan_alergi' => 'nullable|string',
            'status_imunisasi_ringkasan' => 'required|in:Lengkap,Belum Lengkap,Tidak Diimunisasi',
        ]);

        if (!$this->ownedChildIds($request)->contains((int) $validated['child_id'])) {
            return response()->json(['status' => 'error', 'message' => 'Anda tidak berhak mengakses data anak ini.'], 403);
        }

        $record = HealthCondition::create($validated);
        return response()->json(['status' => 'success', 'message' => 'Data Kondisi Kesehatan berhasil disimpan.', 'data' => $record], 201);
    }

    // GET /api/health-conditions/{id}
    public function show(Request $request, $id)
    {
        $record = HealthCondition::with('child')->find($id);
        if (!$record) {
            return response()->json(['status' => 'error', 'message' => 'Data tidak ditemukan.'], 404);
        }
        if (!$this->ownedChildIds($request)->contains($record->child_id)) {
            return response()->json(['status' => 'error', 'message' => 'Anda tidak berhak mengakses data ini.'], 403);
        }

        return response()->json(['status' => 'success', 'data' => $record], 200);
    }

    // PUT/PATCH /api/health-conditions/{id}
    public function update(Request $request, $id)
    {
        $record = HealthCondition::find($id);
        if (!$record) {
            return response()->json(['status' => 'error', 'message' => 'Data tidak ditemukan.'], 404);
        }
        if (!$this->ownedChildIds($request)->contains($record->child_id)) {
            return response()->json(['status' => 'error', 'message' => 'Anda tidak berhak mengakses data ini.'], 403);
        }

        $validated = $request->validate([
            'pernah_dirawat_rs' => 'sometimes|boolean',
            'sering_sakit_3bulan' => 'sometimes|boolean',
            'alergi_makanan_obat' => 'sometimes|boolean',
            'keterangan_alergi' => 'nullable|string',
            'status_imunisasi_ringkasan' => 'sometimes|in:Lengkap,Belum Lengkap,Tidak Diimunisasi',
        ]);

        $record->update($validated);
        return response()->json(['status' => 'success', 'message' => 'Data Kondisi Kesehatan berhasil diperbarui.', 'data' => $record], 200);
    }

    // DELETE /api/health-conditions/{id}
    public function destroy(Request $request, $id)
    {
        $record = HealthCondition::find($id);
        if (!$record) {
            return response()->json(['status' => 'error', 'message' => 'Data tidak ditemukan.'], 404);
        }
        if (!$this->ownedChildIds($request)->contains($record->child_id)) {
            return response()->json(['status' => 'error', 'message' => 'Anda tidak berhak mengakses data ini.'], 403);
        }

        $record->delete();
        return response()->json(['status' => 'success', 'message' => 'Data Kondisi Kesehatan dihapus.'], 200);
    }
}
