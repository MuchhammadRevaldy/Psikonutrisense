<?php

namespace App\Http\Controllers;

use App\Models\Mother;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Laravel\Sanctum\PersonalAccessToken;

class MotherController extends Controller
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

    // GET /api/mothers
    public function index(Request $request)
    {
        $user = $this->getUser($request);
        if (!$user) {
            return response()->json(['status' => 'error', 'message' => 'Unauthenticated.'], 401);
        }

        $mothers = Mother::with(['children', 'wellbeingScreenings', 'sanitationFacility'])
            ->where('user_id', $user->id)
            ->get();
        return response()->json([
            'status' => 'success',
            'data' => $mothers
        ], 200);
    }

    // POST /api/mothers
    public function store(Request $request)
    {
        $user = $this->getUser($request);
        if (!$user) {
            return response()->json(['status' => 'error', 'message' => 'Unauthenticated.'], 401);
        }

        $validated = $request->validate([
            'nama_ibu' => 'required|string|max:255',
            'nik_ibu' => 'required|string|max:16',
            'tanggal_lahir_ibu' => 'required|date',
            'pendidikan_terakhir' => 'required|string',
            'pekerjaan' => 'required|string',
            'alamat_dusun_rt_rw' => 'required|string',
            'no_hp_whatsapp' => 'required|string',
            'foto_buku_kia' => 'nullable|string'
        ]);

        $validated['user_id'] = $user->id;

        $mother = Mother::where('user_id', $user->id)->first();
        if ($mother) {
            $mother->update($validated);
        } else {
            $mother = Mother::create($validated);
        }

        return response()->json([
            'status' => 'success',
            'message' => 'Data profil Ibu berhasil disimpan.',
            'data' => $mother
        ], 200);
    }

    // GET /api/mothers/{id}
    public function show($id)
    {
        $mother = Mother::with(['children', 'wellbeingScreenings', 'sanitationFacility'])->find($id);
        if (!$mother) {
            return response()->json(['status' => 'error', 'message' => 'Data Ibu tidak ditemukan.'], 404);
        }

        return response()->json(['status' => 'success', 'data' => $mother], 200);
    }

    // PUT/PATCH /api/mothers/{id}
    public function update(Request $request, $id)
    {
        $mother = Mother::find($id);
        if (!$mother) {
            return response()->json(['status' => 'error', 'message' => 'Data Ibu tidak ditemukan.'], 404);
        }

        $validated = $request->validate([
            'nama_ibu' => 'sometimes|string|max:255',
            'nik_ibu' => 'sometimes|string|max:16',
            'tanggal_lahir_ibu' => 'sometimes|date',
            'pendidikan_terakhir' => 'sometimes|string',
            'pekerjaan' => 'sometimes|string',
            'alamat_dusun_rt_rw' => 'sometimes|string',
            'no_hp_whatsapp' => 'sometimes|string',
            'foto_buku_kia' => 'nullable|string',
            'poin_ibu_aktif' => 'sometimes|integer',
            'lencana_aktif' => 'sometimes|boolean'
        ]);

        $mother->update($validated);

        return response()->json([
            'status' => 'success',
            'message' => 'Data profil Ibu berhasil diperbarui.',
            'data' => $mother
        ], 200);
    }

    // DELETE /api/mothers/{id}
    public function destroy($id)
    {
        $mother = Mother::find($id);
        if (!$mother) {
            return response()->json(['status' => 'error', 'message' => 'Data Ibu tidak ditemukan.'], 404);
        }

        $mother->delete();

        return response()->json([
            'status' => 'success',
            'message' => 'Data profil Ibu berhasil dihapus.'
        ], 200);
    }
}
