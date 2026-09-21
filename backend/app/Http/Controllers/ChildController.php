<?php

namespace App\Http\Controllers;

use App\Models\Child;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Laravel\Sanctum\PersonalAccessToken;

class ChildController extends Controller
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

    // GET /api/children
    public function index(Request $request)
    {
        $user = $this->getUser($request);
        $motherIds = $user ? \App\Models\Mother::where('user_id', $user->id)->pluck('id') : collect();
        $children = Child::with(['mother', 'growthRecords', 'vaccinationRecords', 'nutritionRecord', 'pmtLogs'])
            ->whereIn('mother_id', $motherIds)
            ->get();
        return response()->json([
            'status' => 'success',
            'data' => $children
        ], 200);
    }

    // POST /api/children
    public function store(Request $request)
    {
        $validated = $request->validate([
            'mother_id' => 'required|exists:mothers,id',
            'nama_balita' => 'required|string|max:255',
            'tanggal_lahir_balita' => 'required|date',
            'jenis_kelamin' => 'required|in:L,P',
            'anak_ke' => 'required|integer|min:1',
            'jumlah_saudara' => 'required|integer|min:0',
            'bb_lahir_kg' => 'nullable|numeric',
            'pb_lahir_cm' => 'nullable|numeric',
            'lahir_prematur' => 'nullable|boolean'
        ]);

        $child = Child::create($validated);

        return response()->json([
            'status' => 'success',
            'message' => 'Data Balita berhasil ditambahkan.',
            'data' => $child
        ], 201);
    }

    // GET /api/children/{id}
    public function show($id)
    {
        $child = Child::with(['mother', 'growthRecords', 'vaccinationRecords', 'nutritionRecord', 'pmtLogs'])->find($id);
        if (!$child) {
            return response()->json(['status' => 'error', 'message' => 'Data Balita tidak ditemukan.'], 404);
        }

        return response()->json(['status' => 'success', 'data' => $child], 200);
    }

    // PUT/PATCH /api/children/{id}
    public function update(Request $request, $id)
    {
        $child = Child::find($id);
        if (!$child) {
            return response()->json(['status' => 'error', 'message' => 'Data Balita tidak ditemukan.'], 404);
        }

        $validated = $request->validate([
            'mother_id' => 'sometimes|exists:mothers,id',
            'nama_balita' => 'sometimes|string|max:255',
            'tanggal_lahir_balita' => 'sometimes|date',
            'jenis_kelamin' => 'sometimes|in:L,P',
            'anak_ke' => 'sometimes|integer|min:1',
            'jumlah_saudara' => 'sometimes|integer|min:0',
            'bb_lahir_kg' => 'nullable|numeric',
            'pb_lahir_cm' => 'nullable|numeric',
            'lahir_prematur' => 'sometimes|boolean'
        ]);

        $child->update($validated);

        return response()->json([
            'status' => 'success',
            'message' => 'Data Balita berhasil diperbarui.',
            'data' => $child
        ], 200);
    }

    // DELETE /api/children/{id}
    public function destroy($id)
    {
        $child = Child::find($id);
        if (!$child) {
            return response()->json(['status' => 'error', 'message' => 'Data Balita tidak ditemukan.'], 404);
        }

        $child->delete();

        return response()->json([
            'status' => 'success',
            'message' => 'Data Balita berhasil dihapus.'
        ], 200);
    }
}
