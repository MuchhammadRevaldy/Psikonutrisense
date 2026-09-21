<?php

namespace App\Http\Controllers;

use App\Models\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\Hash;

class AuthController extends Controller
{
    // POST /api/auth/register
    public function register(Request $request)
    {
        $validated = $request->validate([
            'name'     => 'required|string|max:255',
            'email'    => 'required|email|unique:users,email',
            'password' => 'required|string|min:6|confirmed',
            'role'     => 'nullable|string|in:ibu,kader,bidan,admin',
        ]);

        $user = User::create([
            'name'     => $validated['name'],
            'email'    => $validated['email'],
            'password' => Hash::make($validated['password']),
            'role'     => $validated['role'] ?? 'ibu',
        ]);

        $token = $user->createToken('psikonutrisense_token')->plainTextToken;

        return response()->json([
            'status'  => 'success',
            'message' => 'Akun berhasil dibuat. Selamat datang di Psikonutrisense!',
            'user'    => $user,
            'token'   => $token,
        ], 201);
    }

    // POST /api/auth/login
    public function login(Request $request)
    {
        $validated = $request->validate([
            'email'    => 'required|email',
            'password' => 'required|string',
        ]);

        $user = User::where('email', $validated['email'])->first();

        if (!$user || !Hash::check($validated['password'], $user->password)) {
            return response()->json([
                'status'  => 'error',
                'message' => 'Email atau password tidak sesuai.',
            ], 401);
        }

        $user->tokens()->delete();
        $token = $user->createToken('psikonutrisense_token')->plainTextToken;

        return response()->json([
            'status'  => 'success',
            'message' => 'Login berhasil. Halo, Ibunda!',
            'user'    => $user,
            'token'   => $token,
        ], 200);
    }

    // POST /api/auth/logout (requires auth:sanctum)
    public function logout(Request $request)
    {
        $user = Auth::guard('sanctum')->user();
        if ($user && $user->currentAccessToken()) {
            $user->currentAccessToken()->delete();
        }

        return response()->json([
            'status'  => 'success',
            'message' => 'Logout berhasil.',
        ], 200);
    }

    // GET /api/auth/me (requires auth:sanctum)
    public function me(Request $request)
    {
        $user = Auth::guard('sanctum')->user();
        return response()->json([
            'status' => 'success',
            'data'   => $user,
        ], 200);
    }
}
