<?php

namespace App\Http\Controllers\Concerns;

use App\Models\Child;
use App\Models\Mother;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Laravel\Sanctum\PersonalAccessToken;

/**
 * Shared ownership scoping for controllers whose data belongs to a mother/child.
 * Every index/show/update/destroy must check ownership before touching a record —
 * without it, one authenticated user can read/edit/delete another user's data
 * just by knowing (or guessing) an id.
 */
trait ScopesToOwnedChildren
{
    protected function getUser(Request $request)
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

    protected function ownedMotherIds(Request $request)
    {
        $user = $this->getUser($request);
        if (!$user) {
            return collect();
        }
        return Mother::where('user_id', $user->id)->pluck('id');
    }

    protected function ownedChildIds(Request $request)
    {
        return Child::whereIn('mother_id', $this->ownedMotherIds($request))->pluck('id');
    }
}
