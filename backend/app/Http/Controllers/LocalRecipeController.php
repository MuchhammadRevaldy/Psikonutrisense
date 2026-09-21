<?php

namespace App\Http\Controllers;

use App\Models\LocalRecipe;
use Illuminate\Http\Request;

class LocalRecipeController extends Controller
{
    // GET /api/local-recipes
    public function index()
    {
        $recipes = LocalRecipe::orderBy('created_at', 'desc')->get();
        return response()->json([
            'status' => 'success',
            'data' => $recipes
        ], 200);
    }

    // POST /api/local-recipes
    public function store(Request $request)
    {
        $validated = $request->validate([
            'nama_resep' => 'required|string|max:255',
            'bahan_lokal_utama' => 'required|string',
            'usia_rekomendasi_bulan' => 'required|string',
            'deskripsi_gizi' => 'required|string',
            'langkah_memasak_json' => 'required|array',
            'video_url' => 'nullable|string'
        ]);

        $recipe = LocalRecipe::create($validated);

        return response()->json([
            'status' => 'success',
            'message' => 'Resep Pangan Lokal Ngeposari berhasil ditambahkan.',
            'data' => $recipe
        ], 201);
    }

    // GET /api/local-recipes/{id}
    public function show($id)
    {
        $recipe = LocalRecipe::find($id);
        if (!$recipe) {
            return response()->json(['status' => 'error', 'message' => 'Resep tidak ditemukan.'], 404);
        }

        return response()->json(['status' => 'success', 'data' => $recipe], 200);
    }

    // PUT/PATCH /api/local-recipes/{id}
    public function update(Request $request, $id)
    {
        $recipe = LocalRecipe::find($id);
        if (!$recipe) {
            return response()->json(['status' => 'error', 'message' => 'Resep tidak ditemukan.'], 404);
        }

        $validated = $request->validate([
            'nama_resep' => 'sometimes|string|max:255',
            'bahan_lokal_utama' => 'sometimes|string',
            'usia_rekomendasi_bulan' => 'sometimes|string',
            'deskripsi_gizi' => 'sometimes|string',
            'langkah_memasak_json' => 'sometimes|array',
            'video_url' => 'nullable|string'
        ]);

        $recipe->update($validated);

        return response()->json([
            'status' => 'success',
            'message' => 'Resep Pangan Lokal berhasil diperbarui.',
            'data' => $recipe
        ], 200);
    }

    // DELETE /api/local-recipes/{id}
    public function destroy($id)
    {
        $recipe = LocalRecipe::find($id);
        if (!$recipe) {
            return response()->json(['status' => 'error', 'message' => 'Resep tidak ditemukan.'], 404);
        }

        $recipe->delete();

        return response()->json([
            'status' => 'success',
            'message' => 'Resep Pangan Lokal berhasil dihapus.'
        ], 200);
    }
}
