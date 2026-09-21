package com.psikonutrisense.app.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "psikonutrisense_prefs")

class SessionManager(private val context: Context) {

    companion object {
        val TOKEN_KEY = stringPreferencesKey("auth_token")
        val USER_NAME_KEY = stringPreferencesKey("user_name")
        val USER_EMAIL_KEY = stringPreferencesKey("user_email")
        val USER_ROLE_KEY = stringPreferencesKey("user_role")
        val MOTHER_ID_KEY = intPreferencesKey("mother_id")
        val CHILD_ID_KEY = intPreferencesKey("child_id")
        val REMEMBER_ME_KEY = booleanPreferencesKey("remember_me")
    }

    val token: Flow<String?> = context.dataStore.data.map { it[TOKEN_KEY] }
    val userName: Flow<String?> = context.dataStore.data.map { it[USER_NAME_KEY] }
    val userEmail: Flow<String?> = context.dataStore.data.map { it[USER_EMAIL_KEY] }
    val userRole: Flow<String?> = context.dataStore.data.map { it[USER_ROLE_KEY] }
    val motherId: Flow<Int?> = context.dataStore.data.map { it[MOTHER_ID_KEY] }
    val childId: Flow<Int?> = context.dataStore.data.map { it[CHILD_ID_KEY] }
    val rememberMe: Flow<Boolean> = context.dataStore.data.map { it[REMEMBER_ME_KEY] ?: true }

    suspend fun saveSession(token: String, name: String, email: String, role: String, rememberMe: Boolean = true) {
        context.dataStore.edit { prefs ->
            prefs[TOKEN_KEY] = token
            prefs[USER_NAME_KEY] = name
            prefs[USER_EMAIL_KEY] = email
            prefs[USER_ROLE_KEY] = role
            prefs[REMEMBER_ME_KEY] = rememberMe
        }
    }

    suspend fun saveMotherChildIds(motherId: Int, childId: Int) {
        context.dataStore.edit { prefs ->
            prefs[MOTHER_ID_KEY] = motherId
            prefs[CHILD_ID_KEY] = childId
        }
    }

    suspend fun clearSession() {
        context.dataStore.edit { it.clear() }
    }

    fun bearerToken(token: String) = "Bearer $token"
}
