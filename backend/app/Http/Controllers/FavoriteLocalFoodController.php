<?php

namespace App\Http\Controllers;

use App\Http\Controllers\Concerns\ScopesToOwnedChildren;
use App\Models\FavoriteLocalFood;
use Illuminate\Http\Request;

class FavoriteLocalFoodController extends Controller
{
    use ScopesToOwnedChildren;

    // GET /api/favorite-local-foods
    public function index(Request $request)
    {
        $childIds = $this->ownedChildIds($request);

        $query = FavoriteLocalFood::with('child')->whereIn('child_id', $childIds);
        if ($request->has('child_id')) {
            $query->where('child_id', $request->child_id);
        }

        return response()->json(['status' => 'success', 'data' => $query->get()], 200);
    }

    // POST /api/favorite-local-foods
    public function store(Request $request)
    {
        $validated = $request->validate([
            'child_id' => 'required|exists:children,id',
            'bahan_json' => 'required|array',
            'bahan_lainnya' => 'nullable|string|max:255',
        ]);

        if (!$this->ownedChildIds($request)->contains((int) $validated['child_id'])) {
            return response()->json(['status' => 'error', 'message' => 'Anda tidak berhak mengakses data anak ini.'], 403);
        }

        $record = FavoriteLocalFood::create($validated);
        return response()->json(['status' => 'success', 'message' => 'Pangan Favorit berhasil disimpan.', 'data' => $record], 201);
    }

    // GET /api/favorite-local-foods/{id}
    public function show(Request $request, $id)
    {
        $record = FavoriteLocalFood::with('child')->find($id);
        if (!$record) {
            return response()->json(['status' => 'error', 'message' => 'Data tidak ditemukan.'], 404);
        }
        if (!$this->ownedChildIds($request)->contains($record->child_id)) {
            return response()->json(['status' => 'error', 'message' => 'Anda tidak berhak mengakses data ini.'], 403);
        }

        return response()->json(['status' => 'success', 'data' => $record], 200);
    }

    // PUT/PATCH /api/favorite-local-foods/{id}
    public function update(Request $request, $id)
    {
        $record = FavoriteLocalFood::find($id);
        if (!$record) {
            return response()->json(['status' => 'error', 'message' => 'Data tidak ditemukan.'], 404);
        }
        if (!$this->ownedChildIds($request)->contains($record->child_id)) {
            return response()->json(['status' => 'error', 'message' => 'Anda tidak berhak mengakses data ini.'], 403);
        }

        $validated = $request->validate([
            'bahan_json' => 'sometimes|array',
            'bahan_lainnya' => 'nullable|string|max:255',
        ]);

        $record->update($validated);
        return response()->json(['status' => 'success', 'message' => 'Pangan Favorit berhasil diperbarui.', 'data' => $record], 200);
    }

    // DELETE /api/favorite-local-foods/{id}
    public function destroy(Request $request, $id)
    {
        $record = FavoriteLocalFood::find($id);
        if (!$record) {
            return response()->json(['status' => 'error', 'message' => 'Data tidak ditemukan.'], 404);
        }
        if (!$this->ownedChildIds($request)->contains($record->child_id)) {
            return response()->json(['status' => 'error', 'message' => 'Anda tidak berhak mengakses data ini.'], 403);
        }

        $record->delete();
        return response()->json(['status' => 'success', 'message' => 'Pangan Favorit dihapus.'], 200);
    }
}
