package com.psikonutrisense.app.data.repository

import com.psikonutrisense.app.data.local.SessionManager
import com.psikonutrisense.app.data.model.*
import com.psikonutrisense.app.data.remote.PsikonutrisenseApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PsikonutrisenseRepository @Inject constructor(
    private val api: PsikonutrisenseApi,
    private val sessionManager: SessionManager
) {
    fun getToken(): Flow<String?> = sessionManager.token
    fun getUserName(): Flow<String?> = sessionManager.userName
    fun getUserEmail(): Flow<String?> = sessionManager.userEmail
    fun getUserRole(): Flow<String?> = sessionManager.userRole
    fun getRememberMe(): Flow<Boolean> = sessionManager.rememberMe

    private suspend fun getAuthHeader(): String {
        val token = sessionManager.token.firstOrNull() ?: ""
        return "Bearer $token"
    }

    private fun extractErrorMessage(response: Response<*>): String {
        return try {
            val errorJson = response.errorBody()?.string()
            if (!errorJson.isNullOrEmpty()) {
                val json = JSONObject(errorJson)
                if (json.has("message")) {
                    val msg = json.getString("message")
                    if (json.has("errors")) {
                        val errors = json.getJSONObject("errors")
                        val keys = errors.keys()
                        if (keys.hasNext()) {
                            val firstKey = keys.next()
                            val arr = errors.getJSONArray(firstKey)
                            if (arr.length() > 0) {
                                return "$msg (${arr.getString(0)})"
                            }
                        }
                    }
                    return msg
                }
            }
            response.message().ifBlank { "Error HTTP ${response.code()}" }
        } catch (e: Exception) {
            response.message().ifBlank { "Error HTTP ${response.code()}" }
        }
    }

    suspend fun login(email: String, pass: String, rememberMe: Boolean = true): Result<AuthResponse> {
        return runCatching {
            val response = api.login(LoginRequest(email, pass))
            if (response.isSuccessful && response.body() != null) {
                val authRes = response.body()!!
                authRes.token?.let { token ->
                    sessionManager.saveSession(token, authRes.user?.name ?: "", authRes.user?.email ?: "", authRes.user?.role ?: "", rememberMe)
                }
                authRes
            } else {
                throw Exception("Login gagal: ${extractErrorMessage(response)}")
            }
        }
    }

    suspend fun register(name: String, email: String, pass: String, role: String): Result<AuthResponse> {
        return runCatching {
            val response = api.register(RegisterRequest(name, email, pass, pass, role))
            if (response.isSuccessful && response.body() != null) {
                val authRes = response.body()!!
                authRes.token?.let { token ->
                    sessionManager.saveSession(token, authRes.user?.name ?: "", authRes.user?.email ?: "", authRes.user?.role ?: "", true)
                }
                authRes
            } else {
                throw Exception("Registrasi gagal: ${extractErrorMessage(response)}")
            }
        }
    }

    suspend fun logout() {
        runCatching { api.logout(getAuthHeader()) }
        sessionManager.clearSession()
    }

    suspend fun getMothers(): Result<List<Mother>> {
        return runCatching {
            val response = api.getMothers(getAuthHeader())
            if (response.isSuccessful) {
                response.body()?.data ?: emptyList()
            } else {
                throw Exception(extractErrorMessage(response))
            }
        }
    }

    suspend fun createMother(mother: Mother): Result<Mother> {
        return runCatching {
            val response = api.createMother(getAuthHeader(), mother)
            if (response.isSuccessful && response.body()?.data != null) {
                response.body()!!.data!!
            } else {
                throw Exception(extractErrorMessage(response))
            }
        }
    }

    suspend fun updateMother(id: Int, mother: Mother): Result<Mother> {
        return runCatching {
            val response = api.updateMother(getAuthHeader(), id, mother)
            if (response.isSuccessful && response.body()?.data != null) {
                response.body()!!.data!!
            } else {
                throw Exception(extractErrorMessage(response))
            }
        }
    }

    suspend fun deleteMother(id: Int): Result<Unit> {
        return runCatching {
            val response = api.deleteMother(getAuthHeader(), id)
            if (!response.isSuccessful) {
                throw Exception(extractErrorMessage(response))
            }
        }
    }

    suspend fun getChildren(): Result<List<Child>> {
        return runCatching {
            val response = api.getChildren(getAuthHeader())
            if (response.isSuccessful) {
                response.body()?.data ?: emptyList()
            } else {
                throw Exception(extractErrorMessage(response))
            }
        }
    }

    suspend fun createChild(child: Child): Result<Child> {
        return runCatching {
            val response = api.createChild(getAuthHeader(), child)
            if (response.isSuccessful && response.body()?.data != null) {
                response.body()!!.data!!
            } else {
                throw Exception(extractErrorMessage(response))
            }
        }
    }

    suspend fun updateChild(id: Int, child: Child): Result<Child> {
        return runCatching {
            val response = api.updateChild(getAuthHeader(), id, child)
            if (response.isSuccessful && response.body()?.data != null) {
                response.body()!!.data!!
            } else {
                throw Exception(extractErrorMessage(response))
            }
        }
    }

    suspend fun deleteChild(id: Int): Result<Unit> {
        return runCatching {
            val response = api.deleteChild(getAuthHeader(), id)
            if (!response.isSuccessful) {
                throw Exception(extractErrorMessage(response))
            }
        }
    }

    suspend fun getGrowthRecords(childId: Int? = null): Result<List<GrowthRecord>> {
        return runCatching {
            val response = api.getGrowthRecords(getAuthHeader(), childId)
            if (response.isSuccessful) {
                response.body()?.data ?: emptyList()
            } else {
                throw Exception(extractErrorMessage(response))
            }
        }
    }

    suspend fun createGrowthRecord(record: GrowthRecord): Result<GrowthRecord> {
        return runCatching {
            val response = api.createGrowthRecord(getAuthHeader(), record)
            if (response.isSuccessful && response.body()?.data != null) {
                response.body()!!.data!!
            } else {
                throw Exception(extractErrorMessage(response))
            }
        }
    }

    suspend fun updateGrowthRecord(id: Int, record: GrowthRecord): Result<GrowthRecord> {
        return runCatching {
            val response = api.updateGrowthRecord(getAuthHeader(), id, record)
            if (response.isSuccessful && response.body()?.data != null) {
                response.body()!!.data!!
            } else {
                throw Exception(extractErrorMessage(response))
            }
        }
    }

    suspend fun deleteGrowthRecord(id: Int): Result<Unit> {
        return runCatching {
            val response = api.deleteGrowthRecord(getAuthHeader(), id)
            if (!response.isSuccessful) {
                throw Exception(extractErrorMessage(response))
            }
        }
    }

    suspend fun getRecipes(): Result<List<LocalRecipe>> {
        return runCatching {
            val response = api.getRecipes(getAuthHeader())
            if (response.isSuccessful) {
                response.body()?.data ?: emptyList()
            } else {
                throw Exception(extractErrorMessage(response))
            }
        }
    }

    suspend fun getImmunizations(childId: Int? = null): Result<List<VaccinationRecord>> {
        return runCatching {
            val response = api.getVaccinations(getAuthHeader(), childId)
            if (response.isSuccessful) {
                response.body()?.data ?: emptyList()
            } else {
                throw Exception(extractErrorMessage(response))
            }
        }
    }

    suspend fun createImmunization(record: VaccinationRecord): Result<VaccinationRecord> {
        return runCatching {
            val response = api.createVaccination(getAuthHeader(), record)
            if (response.isSuccessful && response.body()?.data != null) {
                response.body()!!.data!!
            } else {
                throw Exception(extractErrorMessage(response))
            }
        }
    }

    suspend fun updateImmunization(id: Int, record: VaccinationRecord): Result<VaccinationRecord> {
        return runCatching {
            val response = api.updateVaccination(getAuthHeader(), id, record)
            if (response.isSuccessful && response.body()?.data != null) {
                response.body()!!.data!!
            } else {
                throw Exception(extractErrorMessage(response))
            }
        }
    }

    suspend fun deleteImmunization(id: Int): Result<Unit> {
        return runCatching {
            val response = api.deleteVaccination(getAuthHeader(), id)
            if (!response.isSuccessful) {
                throw Exception(extractErrorMessage(response))
            }
        }
    }

    suspend fun getNutritionRecords(childId: Int? = null): Result<List<NutritionRecord>> {
        return runCatching {
            val response = api.getNutritionRecords(getAuthHeader(), childId)
            if (response.isSuccessful) response.body()?.data ?: emptyList() else throw Exception(extractErrorMessage(response))
        }
    }

    suspend fun createNutritionRecord(record: NutritionRecord): Result<NutritionRecord> {
        return runCatching {
            val response = api.createNutritionRecord(getAuthHeader(), record)
            if (response.isSuccessful && response.body()?.data != null) response.body()!!.data!! else throw Exception(extractErrorMessage(response))
        }
    }

    suspend fun updateNutritionRecord(id: Int, record: NutritionRecord): Result<NutritionRecord> {
        return runCatching {
            val response = api.updateNutritionRecord(getAuthHeader(), id, record)
            if (response.isSuccessful && response.body()?.data != null) response.body()!!.data!! else throw Exception(extractErrorMessage(response))
        }
    }

    suspend fun getWellbeingScreenings(motherId: Int? = null): Result<List<WellbeingScreening>> {
        return runCatching {
            val response = api.getWellbeingScreenings(getAuthHeader(), motherId)
            if (response.isSuccessful) response.body()?.data ?: emptyList() else throw Exception(extractErrorMessage(response))
        }
    }

    suspend fun createWellbeingScreening(screening: WellbeingScreening): Result<WellbeingScreening> {
        return runCatching {
            val response = api.createWellbeingScreening(getAuthHeader(), screening)
            if (response.isSuccessful && response.body()?.data != null) response.body()!!.data!! else throw Exception(extractErrorMessage(response))
        }
    }

    suspend fun updateWellbeingScreening(id: Int, screening: WellbeingScreening): Result<WellbeingScreening> {
        return runCatching {
            val response = api.updateWellbeingScreening(getAuthHeader(), id, screening)
            if (response.isSuccessful && response.body()?.data != null) response.body()!!.data!! else throw Exception(extractErrorMessage(response))
        }
    }

    suspend fun getMainGoals(motherId: Int? = null): Result<List<MainGoal>> {
        return runCatching {
            val response = api.getMainGoals(getAuthHeader(), motherId)
            if (response.isSuccessful) response.body()?.data ?: emptyList() else throw Exception(extractErrorMessage(response))
        }
    }

    suspend fun createMainGoal(goal: MainGoal): Result<MainGoal> {
        return runCatching {
            val response = api.createMainGoal(getAuthHeader(), goal)
            if (response.isSuccessful && response.body()?.data != null) response.body()!!.data!! else throw Exception(extractErrorMessage(response))
        }
    }

    suspend fun updateMainGoal(id: Int, goal: MainGoal): Result<MainGoal> {
        return runCatching {
            val response = api.updateMainGoal(getAuthHeader(), id, goal)
            if (response.isSuccessful && response.body()?.data != null) response.body()!!.data!! else throw Exception(extractErrorMessage(response))
        }
    }

    suspend fun getHealthConditions(childId: Int? = null): Result<List<HealthCondition>> {
        return runCatching {
            val response = api.getHealthConditions(getAuthHeader(), childId)
            if (response.isSuccessful) response.body()?.data ?: emptyList() else throw Exception(extractErrorMessage(response))
        }
    }

    suspend fun createHealthCondition(condition: HealthCondition): Result<HealthCondition> {
        return runCatching {
            val response = api.createHealthCondition(getAuthHeader(), condition)
            if (response.isSuccessful && response.body()?.data != null) response.body()!!.data!! else throw Exception(extractErrorMessage(response))
        }
    }

    suspend fun updateHealthCondition(id: Int, condition: HealthCondition): Result<HealthCondition> {
        return runCatching {
            val response = api.updateHealthCondition(getAuthHeader(), id, condition)
            if (response.isSuccessful && response.body()?.data != null) response.body()!!.data!! else throw Exception(extractErrorMessage(response))
        }
    }

    suspend fun getFoodGroupFrequencies(childId: Int? = null): Result<List<FoodGroupFrequency>> {
        return runCatching {
            val response = api.getFoodGroupFrequencies(getAuthHeader(), childId)
            if (response.isSuccessful) response.body()?.data ?: emptyList() else throw Exception(extractErrorMessage(response))
        }
    }

    suspend fun createFoodGroupFrequency(record: FoodGroupFrequency): Result<FoodGroupFrequency> {
        return runCatching {
            val response = api.createFoodGroupFrequency(getAuthHeader(), record)
            if (response.isSuccessful && response.body()?.data != null) response.body()!!.data!! else throw Exception(extractErrorMessage(response))
        }
    }

    suspend fun updateFoodGroupFrequency(id: Int, record: FoodGroupFrequency): Result<FoodGroupFrequency> {
        return runCatching {
            val response = api.updateFoodGroupFrequency(getAuthHeader(), id, record)
            if (response.isSuccessful && response.body()?.data != null) response.body()!!.data!! else throw Exception(extractErrorMessage(response))
        }
    }

    suspend fun getFavoriteLocalFoods(childId: Int? = null): Result<List<FavoriteLocalFood>> {
        return runCatching {
            val response = api.getFavoriteLocalFoods(getAuthHeader(), childId)
            if (response.isSuccessful) response.body()?.data ?: emptyList() else throw Exception(extractErrorMessage(response))
        }
    }

    suspend fun createFavoriteLocalFood(record: FavoriteLocalFood): Result<FavoriteLocalFood> {
        return runCatching {
            val response = api.createFavoriteLocalFood(getAuthHeader(), record)
            if (response.isSuccessful && response.body()?.data != null) response.body()!!.data!! else throw Exception(extractErrorMessage(response))
        }
    }

    suspend fun updateFavoriteLocalFood(id: Int, record: FavoriteLocalFood): Result<FavoriteLocalFood> {
        return runCatching {
            val response = api.updateFavoriteLocalFood(getAuthHeader(), id, record)
            if (response.isSuccessful && response.body()?.data != null) response.body()!!.data!! else throw Exception(extractErrorMessage(response))
        }
    }
}
