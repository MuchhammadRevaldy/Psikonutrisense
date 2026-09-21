<?php

namespace App\Http\Controllers;

use App\Http\Controllers\Concerns\ScopesToOwnedChildren;
use App\Models\VaccinationRecord;
use Illuminate\Http\Request;

class VaccinationRecordController extends Controller
{
    use ScopesToOwnedChildren;

    // GET /api/vaccination-records
    public function index(Request $request)
    {
        $childIds = $this->ownedChildIds($request);

        $query = VaccinationRecord::with('child')->whereIn('child_id', $childIds);
        if ($request->has('child_id')) {
            $query->where('child_id', $request->child_id);
        }
        $records = $query->orderBy('tanggal_jadwal', 'asc')->get();

        return response()->json([
            'status' => 'success',
            'data' => $records
        ], 200);
    }

    // POST /api/vaccination-records
    public function store(Request $request)
    {
        $validated = $request->validate([
            'child_id' => 'required|exists:children,id',
            'jenis_imunisasi' => 'required|string',
            'tanggal_jadwal' => 'required|date',
            'tanggal_pemberian' => 'nullable|date',
            'status' => 'required|in:Sudah,Belum,Mendekati,Terlambat',
            'alasan_belum' => 'nullable|string'
        ]);

        if (!$this->ownedChildIds($request)->contains((int) $validated['child_id'])) {
            return response()->json(['status' => 'error', 'message' => 'Anda tidak berhak mengakses data anak ini.'], 403);
        }

        $record = VaccinationRecord::create($validated);

        return response()->json([
            'status' => 'success',
            'message' => 'Jadwal Imunisasi berhasil disimpan.',
            'data' => $record
        ], 201);
    }

    // GET /api/vaccination-records/{id}
    public function show(Request $request, $id)
    {
        $record = VaccinationRecord::with('child')->find($id);
        if (!$record) {
            return response()->json(['status' => 'error', 'message' => 'Catatan imunisasi tidak ditemukan.'], 404);
        }
        if (!$this->ownedChildIds($request)->contains($record->child_id)) {
            return response()->json(['status' => 'error', 'message' => 'Anda tidak berhak mengakses data ini.'], 403);
        }

        return response()->json(['status' => 'success', 'data' => $record], 200);
    }

    // PUT/PATCH /api/vaccination-records/{id}
    public function update(Request $request, $id)
    {
        $record = VaccinationRecord::find($id);
        if (!$record) {
            return response()->json(['status' => 'error', 'message' => 'Catatan imunisasi tidak ditemukan.'], 404);
        }
        if (!$this->ownedChildIds($request)->contains($record->child_id)) {
            return response()->json(['status' => 'error', 'message' => 'Anda tidak berhak mengakses data ini.'], 403);
        }

        $validated = $request->validate([
            'jenis_imunisasi' => 'sometimes|string',
            'tanggal_jadwal' => 'sometimes|date',
            'tanggal_pemberian' => 'nullable|date',
            'status' => 'sometimes|in:Sudah,Belum,Mendekati,Terlambat',
            'alasan_belum' => 'nullable|string'
        ]);

        $record->update($validated);

        return response()->json([
            'status' => 'success',
            'message' => 'Status imunisasi berhasil diperbarui.',
            'data' => $record
        ], 200);
    }

    // DELETE /api/vaccination-records/{id}
    public function destroy(Request $request, $id)
    {
        $record = VaccinationRecord::find($id);
        if (!$record) {
            return response()->json(['status' => 'error', 'message' => 'Catatan imunisasi tidak ditemukan.'], 404);
        }
        if (!$this->ownedChildIds($request)->contains($record->child_id)) {
            return response()->json(['status' => 'error', 'message' => 'Anda tidak berhak mengakses data ini.'], 403);
        }

        $record->delete();

        return response()->json([
            'status' => 'success',
            'message' => 'Catatan imunisasi berhasil dihapus.'
        ], 200);
    }
}
