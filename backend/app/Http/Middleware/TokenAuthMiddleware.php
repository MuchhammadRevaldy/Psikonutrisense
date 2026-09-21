<?php

namespace App\Http\Middleware;

use Closure;
use Illuminate\Http\Request;
use Laravel\Sanctum\PersonalAccessToken;

class TokenAuthMiddleware
{
    public function handle(Request $request, Closure $next)
    {
        $token = $request->bearerToken();
        if (!$token) {
            return response()->json(['status' => 'error', 'message' => 'Unauthenticated.'], 401);
        }

        $accessToken = PersonalAccessToken::findToken($token);
        if (!$accessToken || !$accessToken->tokenable) {
            return response()->json(['status' => 'error', 'message' => 'Token tidak valid atau kadaluarsa.'], 401);
        }

        $user = $accessToken->tokenable;
        $request->setUserResolver(fn () => $user);

        return $next($request);
    }
}
