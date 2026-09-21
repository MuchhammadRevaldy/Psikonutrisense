<?php

namespace App\Http\Controllers;

use App\Http\Controllers\Concerns\ScopesToOwnedChildren;
use App\Models\MainGoal;
use Illuminate\Http\Request;

class MainGoalController extends Controller
{
    use ScopesToOwnedChildren;

    // GET /api/main-goals
    public function index(Request $request)
    {
        $motherIds = $this->ownedMotherIds($request);

        $query = MainGoal::with('mother')->whereIn('mother_id', $motherIds);
        if ($request->has('mother_id')) {
            $query->where('mother_id', $request->mother_id);
        }

        return response()->json(['status' => 'success', 'data' => $query->get()], 200);
    }

    // POST /api/main-goals
    public function store(Request $request)
    {
        $validated = $request->validate([
            'mother_id' => 'required|exists:mothers,id',
            'tujuan_utama' => 'required|string|max:100',
            'harapan_untuk_anak' => 'nullable|string',
            'bersedia_ikut_intervensi' => 'required|boolean',
        ]);

        if (!$this->ownedMotherIds($request)->contains((int) $validated['mother_id'])) {
            return response()->json(['status' => 'error', 'message' => 'Anda tidak berhak mengakses data ibu ini.'], 403);
        }

        $record = MainGoal::create($validated);
        return response()->json(['status' => 'success', 'message' => 'Tujuan Utama berhasil disimpan.', 'data' => $record], 201);
    }

    // GET /api/main-goals/{id}
    public function show(Request $request, $id)
    {
        $record = MainGoal::with('mother')->find($id);
        if (!$record) {
            return response()->json(['status' => 'error', 'message' => 'Data tidak ditemukan.'], 404);
        }
        if (!$this->ownedMotherIds($request)->contains($record->mother_id)) {
            return response()->json(['status' => 'error', 'message' => 'Anda tidak berhak mengakses data ini.'], 403);
        }

        return response()->json(['status' => 'success', 'data' => $record], 200);
    }

    // PUT/PATCH /api/main-goals/{id}
    public function update(Request $request, $id)
    {
        $record = MainGoal::find($id);
        if (!$record) {
            return response()->json(['status' => 'error', 'message' => 'Data tidak ditemukan.'], 404);
        }
        if (!$this->ownedMotherIds($request)->contains($record->mother_id)) {
            return response()->json(['status' => 'error', 'message' => 'Anda tidak berhak mengakses data ini.'], 403);
        }

        $validated = $request->validate([
            'tujuan_utama' => 'sometimes|string|max:100',
            'harapan_untuk_anak' => 'nullable|string',
            'bersedia_ikut_intervensi' => 'sometimes|boolean',
        ]);

        $record->update($validated);
        return response()->json(['status' => 'success', 'message' => 'Tujuan Utama berhasil diperbarui.', 'data' => $record], 200);
    }

    // DELETE /api/main-goals/{id}
    public function destroy(Request $request, $id)
    {
        $record = MainGoal::find($id);
        if (!$record) {
            return response()->json(['status' => 'error', 'message' => 'Data tidak ditemukan.'], 404);
        }
        if (!$this->ownedMotherIds($request)->contains($record->mother_id)) {
            return response()->json(['status' => 'error', 'message' => 'Anda tidak berhak mengakses data ini.'], 403);
        }

        $record->delete();
        return response()->json(['status' => 'success', 'message' => 'Tujuan Utama dihapus.'], 200);
    }
}
