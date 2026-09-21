<?php

namespace App\Http\Controllers;

use App\Models\Child;
use App\Models\GrowthRecord;
use App\Models\Mother;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Laravel\Sanctum\PersonalAccessToken;

class GrowthRecordController extends Controller
{
    private function getUser(Request $request)
    {
        $token = $request->bearerToken();
        if ($token) {
            $accessToken = PersonalAccessToken::findToken($token);
            if ($accessToken && $accessToken->tokenable) {
                return $accessToken->tokenable;
            }
        }
        return Auth::user();
    }

    private function ownedChildIds(Request $request)
    {
        $user = $this->getUser($request);
        if (!$user) {
            return collect();
        }
        $motherIds = Mother::where('user_id', $user->id)->pluck('id');
        return Child::whereIn('mother_id', $motherIds)->pluck('id');
    }

    // GET /api/growth-records
    public function index(Request $request)
    {
        $childIds = $this->ownedChildIds($request);

        $query = GrowthRecord::with('child')->whereIn('child_id', $childIds);
        if ($request->has('child_id')) {
            $query->where('child_id', $request->child_id);
        }
        $records = $query->orderBy('tanggal_penimbangan', 'desc')->get();

        return response()->json([
            'status' => 'success',
            'data' => $records
        ], 200);
    }

    // POST /api/growth-records
    public function store(Request $request)
    {
        $validated = $request->validate([
            'child_id' => 'required|exists:children,id',
            'tanggal_penimbangan' => 'required|date',
            'berat_badan_kg' => 'required|numeric',
            'tinggi_badan_cm' => 'required|numeric',
            'lingkar_kepala_cm' => 'required|numeric',
            'lila_cm' => 'required|numeric',
            'status_penimbangan' => 'nullable|string',
            'status_gizi' => 'nullable|string',
            'zscore_bbu' => 'nullable|numeric',
            'zscore_tbu' => 'nullable|numeric',
            'kpsp_checklist_json' => 'nullable|array'
        ]);

        if (!$this->ownedChildIds($request)->contains((int) $validated['child_id'])) {
            return response()->json(['status' => 'error', 'message' => 'Anda tidak berhak mengakses data anak ini.'], 403);
        }

        $record = GrowthRecord::create($validated);

        return response()->json([
            'status' => 'success',
            'message' => 'Catatan pertumbuhan (Antropometri) berhasil disimpan.',
            'data' => $record
        ], 201);
    }

    // GET /api/growth-records/{id}
    public function show(Request $request, $id)
    {
        $record = GrowthRecord::with('child')->find($id);
        if (!$record) {
            return response()->json(['status' => 'error', 'message' => 'Catatan pertumbuhan tidak ditemukan.'], 404);
        }
        if (!$this->ownedChildIds($request)->contains($record->child_id)) {
            return response()->json(['status' => 'error', 'message' => 'Anda tidak berhak mengakses data ini.'], 403);
        }

        return response()->json(['status' => 'success', 'data' => $record], 200);
    }

    // PUT/PATCH /api/growth-records/{id}
    public function update(Request $request, $id)
    {
        $record = GrowthRecord::find($id);
        if (!$record) {
            return response()->json(['status' => 'error', 'message' => 'Catatan pertumbuhan tidak ditemukan.'], 404);
        }
        if (!$this->ownedChildIds($request)->contains($record->child_id)) {
            return response()->json(['status' => 'error', 'message' => 'Anda tidak berhak mengakses data ini.'], 403);
        }

        $validated = $request->validate([
            'tanggal_penimbangan' => 'sometimes|date',
            'berat_badan_kg' => 'sometimes|numeric',
            'tinggi_badan_cm' => 'sometimes|numeric',
            'lingkar_kepala_cm' => 'sometimes|numeric',
            'lila_cm' => 'sometimes|numeric',
            'status_penimbangan' => 'nullable|string',
            'status_gizi' => 'nullable|string',
            'zscore_bbu' => 'nullable|numeric',
            'zscore_tbu' => 'nullable|numeric',
            'kpsp_checklist_json' => 'nullable|array'
        ]);

        $record->update($validated);

        return response()->json([
            'status' => 'success',
            'message' => 'Catatan pertumbuhan berhasil diperbarui.',
            'data' => $record
        ], 200);
    }

    // DELETE /api/growth-records/{id}
    public function destroy(Request $request, $id)
    {
        $record = GrowthRecord::find($id);
        if (!$record) {
            return response()->json(['status' => 'error', 'message' => 'Catatan pertumbuhan tidak ditemukan.'], 404);
        }
        if (!$this->ownedChildIds($request)->contains($record->child_id)) {
            return response()->json(['status' => 'error', 'message' => 'Anda tidak berhak mengakses data ini.'], 403);
        }

        $record->delete();

        return response()->json([
            'status' => 'success',
            'message' => 'Catatan pertumbuhan berhasil dihapus.'
        ], 200);
    }
}
