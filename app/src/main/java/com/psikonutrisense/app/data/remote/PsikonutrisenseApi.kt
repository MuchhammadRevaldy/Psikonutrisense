package com.psikonutrisense.app.data.remote

import com.psikonutrisense.app.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface PsikonutrisenseApi {

    // ===== AUTH =====
    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<AuthResponse>

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>

    @POST("auth/logout")
    suspend fun logout(@Header("Authorization") token: String): Response<ApiResponse<Unit>>

    @GET("auth/me")
    suspend fun me(@Header("Authorization") token: String): Response<ApiResponse<User>>

    // ===== MOTHERS =====
    @GET("mothers")
    suspend fun getMothers(@Header("Authorization") token: String): Response<ApiListResponse<Mother>>

    @POST("mothers")
    suspend fun createMother(@Header("Authorization") token: String, @Body mother: Mother): Response<ApiResponse<Mother>>

    @GET("mothers/{id}")
    suspend fun getMother(@Header("Authorization") token: String, @Path("id") id: Int): Response<ApiResponse<Mother>>

    @PUT("mothers/{id}")
    suspend fun updateMother(@Header("Authorization") token: String, @Path("id") id: Int, @Body mother: Mother): Response<ApiResponse<Mother>>

    @DELETE("mothers/{id}")
    suspend fun deleteMother(@Header("Authorization") token: String, @Path("id") id: Int): Response<ApiResponse<Unit>>

    // ===== CHILDREN =====
    @GET("children")
    suspend fun getChildren(@Header("Authorization") token: String): Response<ApiListResponse<Child>>

    @POST("children")
    suspend fun createChild(@Header("Authorization") token: String, @Body child: Child): Response<ApiResponse<Child>>

    @GET("children/{id}")
    suspend fun getChild(@Header("Authorization") token: String, @Path("id") id: Int): Response<ApiResponse<Child>>

    @PUT("children/{id}")
    suspend fun updateChild(@Header("Authorization") token: String, @Path("id") id: Int, @Body child: Child): Response<ApiResponse<Child>>

    @DELETE("children/{id}")
    suspend fun deleteChild(@Header("Authorization") token: String, @Path("id") id: Int): Response<ApiResponse<Unit>>

    // ===== GROWTH RECORDS =====
    @GET("growth-records")
    suspend fun getGrowthRecords(@Header("Authorization") token: String, @Query("child_id") childId: Int? = null): Response<ApiListResponse<GrowthRecord>>

    @POST("growth-records")
    suspend fun createGrowthRecord(@Header("Authorization") token: String, @Body record: GrowthRecord): Response<ApiResponse<GrowthRecord>>

    @PUT("growth-records/{id}")
    suspend fun updateGrowthRecord(@Header("Authorization") token: String, @Path("id") id: Int, @Body record: GrowthRecord): Response<ApiResponse<GrowthRecord>>

    @DELETE("growth-records/{id}")
    suspend fun deleteGrowthRecord(@Header("Authorization") token: String, @Path("id") id: Int): Response<ApiResponse<Unit>>

    // ===== VACCINATION =====
    @GET("vaccination-records")
    suspend fun getVaccinations(@Header("Authorization") token: String, @Query("child_id") childId: Int? = null): Response<ApiListResponse<VaccinationRecord>>

    @POST("vaccination-records")
    suspend fun createVaccination(@Header("Authorization") token: String, @Body record: VaccinationRecord): Response<ApiResponse<VaccinationRecord>>

    @PUT("vaccination-records/{id}")
    suspend fun updateVaccination(@Header("Authorization") token: String, @Path("id") id: Int, @Body record: VaccinationRecord): Response<ApiResponse<VaccinationRecord>>

    @DELETE("vaccination-records/{id}")
    suspend fun deleteVaccination(@Header("Authorization") token: String, @Path("id") id: Int): Response<ApiResponse<Unit>>

    // ===== PMT LOGS =====
    @GET("pmt-logs")
    suspend fun getPmtLogs(@Header("Authorization") token: String, @Query("child_id") childId: Int? = null): Response<ApiListResponse<PmtLog>>

    @POST("pmt-logs")
    suspend fun createPmtLog(@Header("Authorization") token: String, @Body log: PmtLog): Response<ApiResponse<PmtLog>>

    @PUT("pmt-logs/{id}")
    suspend fun updatePmtLog(@Header("Authorization") token: String, @Path("id") id: Int, @Body log: PmtLog): Response<ApiResponse<PmtLog>>

    // ===== NUTRITION RECORDS =====
    @GET("nutrition-records")
    suspend fun getNutritionRecords(@Header("Authorization") token: String, @Query("child_id") childId: Int? = null): Response<ApiListResponse<NutritionRecord>>

    @POST("nutrition-records")
    suspend fun createNutritionRecord(@Header("Authorization") token: String, @Body record: NutritionRecord): Response<ApiResponse<NutritionRecord>>

    @PUT("nutrition-records/{id}")
    suspend fun updateNutritionRecord(@Header("Authorization") token: String, @Path("id") id: Int, @Body record: NutritionRecord): Response<ApiResponse<NutritionRecord>>

    // ===== WELLBEING =====
    @GET("wellbeing-screenings")
    suspend fun getWellbeingScreenings(@Header("Authorization") token: String, @Query("mother_id") motherId: Int? = null): Response<ApiListResponse<WellbeingScreening>>

    @POST("wellbeing-screenings")
    suspend fun createWellbeingScreening(@Header("Authorization") token: String, @Body screening: WellbeingScreening): Response<ApiResponse<WellbeingScreening>>

    @PUT("wellbeing-screenings/{id}")
    suspend fun updateWellbeingScreening(@Header("Authorization") token: String, @Path("id") id: Int, @Body screening: WellbeingScreening): Response<ApiResponse<WellbeingScreening>>

    // ===== LOCAL RECIPES =====
    @GET("local-recipes")
    suspend fun getRecipes(@Header("Authorization") token: String): Response<ApiListResponse<LocalRecipe>>

    @GET("local-recipes/{id}")
    suspend fun getRecipe(@Header("Authorization") token: String, @Path("id") id: Int): Response<ApiResponse<LocalRecipe>>

    @POST("local-recipes")
    suspend fun createRecipe(@Header("Authorization") token: String, @Body recipe: LocalRecipe): Response<ApiResponse<LocalRecipe>>

    @PUT("local-recipes/{id}")
    suspend fun updateRecipe(@Header("Authorization") token: String, @Path("id") id: Int, @Body recipe: LocalRecipe): Response<ApiResponse<LocalRecipe>>

    @DELETE("local-recipes/{id}")
    suspend fun deleteRecipe(@Header("Authorization") token: String, @Path("id") id: Int): Response<ApiResponse<Unit>>
}
