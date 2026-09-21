<?php

namespace App\Http\Controllers;

use App\Http\Controllers\Concerns\ScopesToOwnedChildren;
use App\Models\FoodGroupFrequency;
use Illuminate\Http\Request;

class FoodGroupFrequencyController extends Controller
{
    use ScopesToOwnedChildren;

    private const FREQ_RULE = 'required|string|max:20';

    // GET /api/food-group-frequencies
    public function index(Request $request)
    {
        $childIds = $this->ownedChildIds($request);

        $query = FoodGroupFrequency::with('child')->whereIn('child_id', $childIds);
        if ($request->has('child_id')) {
            $query->where('child_id', $request->child_id);
        }

        return response()->json(['status' => 'success', 'data' => $query->orderBy('tanggal_pencatatan', 'desc')->get()], 200);
    }

    // POST /api/food-group-frequencies
    public function store(Request $request)
    {
        $validated = $request->validate([
            'child_id' => 'required|exists:children,id',
            'tanggal_pencatatan' => 'required|date',
            'nasi_bubur' => self::FREQ_RULE,
            'lauk_hewani' => self::FREQ_RULE,
            'lauk_nabati' => self::FREQ_RULE,
            'sayur' => self::FREQ_RULE,
            'buah' => self::FREQ_RULE,
            'susu' => self::FREQ_RULE,
        ]);

        if (!$this->ownedChildIds($request)->contains((int) $validated['child_id'])) {
            return response()->json(['status' => 'error', 'message' => 'Anda tidak berhak mengakses data anak ini.'], 403);
        }

        $record = FoodGroupFrequency::create($validated);
        return response()->json(['status' => 'success', 'message' => 'Data Asupan Pangan berhasil disimpan.', 'data' => $record], 201);
    }

    // GET /api/food-group-frequencies/{id}
    public function show(Request $request, $id)
    {
        $record = FoodGroupFrequency::with('child')->find($id);
        if (!$record) {
            return response()->json(['status' => 'error', 'message' => 'Data tidak ditemukan.'], 404);
        }
        if (!$this->ownedChildIds($request)->contains($record->child_id)) {
            return response()->json(['status' => 'error', 'message' => 'Anda tidak berhak mengakses data ini.'], 403);
        }

        return response()->json(['status' => 'success', 'data' => $record], 200);
    }

    // PUT/PATCH /api/food-group-frequencies/{id}
    public function update(Request $request, $id)
    {
        $record = FoodGroupFrequency::find($id);
        if (!$record) {
            return response()->json(['status' => 'error', 'message' => 'Data tidak ditemukan.'], 404);
        }
        if (!$this->ownedChildIds($request)->contains($record->child_id)) {
            return response()->json(['status' => 'error', 'message' => 'Anda tidak berhak mengakses data ini.'], 403);
        }

        $validated = $request->validate([
            'tanggal_pencatatan' => 'sometimes|date',
            'nasi_bubur' => 'sometimes|string|max:20',
            'lauk_hewani' => 'sometimes|string|max:20',
            'lauk_nabati' => 'sometimes|string|max:20',
            'sayur' => 'sometimes|string|max:20',
            'buah' => 'sometimes|string|max:20',
            'susu' => 'sometimes|string|max:20',
        ]);

        $record->update($validated);
        return response()->json(['status' => 'success', 'message' => 'Data Asupan Pangan berhasil diperbarui.', 'data' => $record], 200);
    }

    // DELETE /api/food-group-frequencies/{id}
    public function destroy(Request $request, $id)
    {
        $record = FoodGroupFrequency::find($id);
        if (!$record) {
            return response()->json(['status' => 'error', 'message' => 'Data tidak ditemukan.'], 404);
        }
        if (!$this->ownedChildIds($request)->contains($record->child_id)) {
            return response()->json(['status' => 'error', 'message' => 'Anda tidak berhak mengakses data ini.'], 403);
        }

        $record->delete();
        return response()->json(['status' => 'success', 'message' => 'Data Asupan Pangan dihapus.'], 200);
    }
}
