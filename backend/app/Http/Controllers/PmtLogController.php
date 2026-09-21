<?php

namespace App\Http\Controllers;

use App\Models\PmtLog;
use Illuminate\Http\Request;

class PmtLogController extends Controller
{
    // GET /api/pmt-logs
    public function index(Request $request)
    {
        $query = PmtLog::with('child');
        if ($request->has('child_id')) {
            $query->where('child_id', $request->child_id);
        }
        $logs = $query->orderBy('tanggal', 'desc')->get();

        return response()->json([
            'status' => 'success',
            'data' => $logs
        ], 200);
    }

    // POST /api/pmt-logs
    public function store(Request $request)
    {
        $validated = $request->validate([
            'child_id' => 'required|exists:children,id',
            'tanggal' => 'required|date',
            'status_konsumsi' => 'required|in:Habis,Sebagian,Tidak',
            'qr_code_scanned' => 'nullable|string'
        ]);

        $log = PmtLog::create($validated);

        return response()->json([
            'status' => 'success',
            'message' => 'Log konsumsi PMT harian berhasil disimpan.',
            'data' => $log
        ], 201);
    }

    // GET /api/pmt-logs/{id}
    public function show($id)
    {
        $log = PmtLog::with('child')->find($id);
        if (!$log) {
            return response()->json(['status' => 'error', 'message' => 'Log PMT tidak ditemukan.'], 404);
        }

        return response()->json(['status' => 'success', 'data' => $log], 200);
    }

    // PUT/PATCH /api/pmt-logs/{id}
    public function update(Request $request, $id)
    {
        $log = PmtLog::find($id);
        if (!$log) {
            return response()->json(['status' => 'error', 'message' => 'Log PMT tidak ditemukan.'], 404);
        }

        $validated = $request->validate([
            'tanggal' => 'sometimes|date',
            'status_konsumsi' => 'sometimes|in:Habis,Sebagian,Tidak',
            'qr_code_scanned' => 'nullable|string'
        ]);

        $log->update($validated);

        return response()->json([
            'status' => 'success',
            'message' => 'Log PMT berhasil diperbarui.',
            'data' => $log
        ], 200);
    }

    // DELETE /api/pmt-logs/{id}
    public function destroy($id)
    {
        $log = PmtLog::find($id);
        if (!$log) {
            return response()->json(['status' => 'error', 'message' => 'Log PMT tidak ditemukan.'], 404);
        }

        $log->delete();

        return response()->json([
            'status' => 'success',
            'message' => 'Log PMT berhasil dihapus.'
        ], 200);
    }
}
