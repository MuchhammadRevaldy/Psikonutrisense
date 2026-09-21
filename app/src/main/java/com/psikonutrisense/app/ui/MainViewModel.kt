package com.psikonutrisense.app.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.psikonutrisense.app.data.model.*
import com.psikonutrisense.app.data.repository.PsikonutrisenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class UiState<out T> {
    object Idle : UiState<Nothing>()
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: PsikonutrisenseRepository
) : ViewModel() {

    // null while loading, then true only if a token exists AND "remember me" was checked at login
    private val _autoLoginState = MutableStateFlow<Boolean?>(null)
    val autoLoginState: StateFlow<Boolean?> = _autoLoginState.asStateFlow()

    private val _userEmailState = MutableStateFlow<String?>(null)
    val userEmailState: StateFlow<String?> = _userEmailState.asStateFlow()

    private val _userNameState = MutableStateFlow<String?>(null)
    val userNameState: StateFlow<String?> = _userNameState.asStateFlow()

    private val _userRoleState = MutableStateFlow<String?>(null)
    val userRoleState: StateFlow<String?> = _userRoleState.asStateFlow()

    private val _loginState = MutableStateFlow<UiState<AuthResponse>>(UiState.Idle)
    val loginState: StateFlow<UiState<AuthResponse>> = _loginState.asStateFlow()

    private val _registerState = MutableStateFlow<UiState<AuthResponse>>(UiState.Idle)
    val registerState: StateFlow<UiState<AuthResponse>> = _registerState.asStateFlow()

    private val _mothersState = MutableStateFlow<UiState<List<Mother>>>(UiState.Idle)
    val mothersState: StateFlow<UiState<List<Mother>>> = _mothersState.asStateFlow()

    private val _childrenState = MutableStateFlow<UiState<List<Child>>>(UiState.Idle)
    val childrenState: StateFlow<UiState<List<Child>>> = _childrenState.asStateFlow()

    private val _growthState = MutableStateFlow<UiState<List<GrowthRecord>>>(UiState.Idle)
    val growthState: StateFlow<UiState<List<GrowthRecord>>> = _growthState.asStateFlow()

    private val _recipesState = MutableStateFlow<UiState<List<LocalRecipe>>>(UiState.Idle)
    val recipesState: StateFlow<UiState<List<LocalRecipe>>> = _recipesState.asStateFlow()

    private val _immunizationState = MutableStateFlow<UiState<List<VaccinationRecord>>>(UiState.Idle)
    val immunizationState: StateFlow<UiState<List<VaccinationRecord>>> = _immunizationState.asStateFlow()

    private val _actionState = MutableStateFlow<UiState<String>>(UiState.Idle)
    val actionState: StateFlow<UiState<String>> = _actionState.asStateFlow()

    private val _healthConditionState = MutableStateFlow<UiState<HealthCondition?>>(UiState.Idle)
    val healthConditionState: StateFlow<UiState<HealthCondition?>> = _healthConditionState.asStateFlow()

    private val _nutritionState = MutableStateFlow<UiState<NutritionRecord?>>(UiState.Idle)
    val nutritionState: StateFlow<UiState<NutritionRecord?>> = _nutritionState.asStateFlow()

    private val _foodGroupState = MutableStateFlow<UiState<FoodGroupFrequency?>>(UiState.Idle)
    val foodGroupState: StateFlow<UiState<FoodGroupFrequency?>> = _foodGroupState.asStateFlow()

    private val _favoriteFoodState = MutableStateFlow<UiState<FavoriteLocalFood?>>(UiState.Idle)
    val favoriteFoodState: StateFlow<UiState<FavoriteLocalFood?>> = _favoriteFoodState.asStateFlow()

    private val _wellbeingState = MutableStateFlow<UiState<WellbeingScreening?>>(UiState.Idle)
    val wellbeingState: StateFlow<UiState<WellbeingScreening?>> = _wellbeingState.asStateFlow()

    private val _mainGoalState = MutableStateFlow<UiState<MainGoal?>>(UiState.Idle)
    val mainGoalState: StateFlow<UiState<MainGoal?>> = _mainGoalState.asStateFlow()

    // null while loading, then true only once the wizard's last step (Tujuan Utama) has been saved
    private val _wizardComplete = MutableStateFlow<Boolean?>(null)
    val wizardComplete: StateFlow<Boolean?> = _wizardComplete.asStateFlow()

    // Profile completeness check state
    // null = not checked yet, true = complete, false = needs onboarding
    private val _profileComplete = MutableStateFlow<Boolean?>(null)
    val profileComplete: StateFlow<Boolean?> = _profileComplete.asStateFlow()

    // Saved mother ID from onboarding flow (used to chain mother → child creation)
    private val _onboardingMotherId = MutableStateFlow<Int?>(null)
    val onboardingMotherId: StateFlow<Int?> = _onboardingMotherId.asStateFlow()

    // Onboarding save state — separate from actionState to avoid cross-screen side effects
    private val _onboardingSaveState = MutableStateFlow<UiState<String>>(UiState.Idle)
    val onboardingSaveState: StateFlow<UiState<String>> = _onboardingSaveState.asStateFlow()

    init {
        viewModelScope.launch {
            combine(repository.getToken(), repository.getRememberMe()) { token, rememberMe ->
                !token.isNullOrBlank() && rememberMe
            }.collect { canAutoLogin ->
                _autoLoginState.value = canAutoLogin
            }
        }
        viewModelScope.launch {
            repository.getUserEmail().collect { _userEmailState.value = it }
        }
        viewModelScope.launch {
            repository.getUserName().collect { _userNameState.value = it }
        }
        viewModelScope.launch {
            repository.getUserRole().collect { _userRoleState.value = it }
        }
    }

    fun login(email: String, pass: String, rememberMe: Boolean = true) {
        viewModelScope.launch {
            _loginState.value = UiState.Loading
            repository.login(email, pass, rememberMe)
                .onSuccess {
                    _loginState.value = UiState.Success(it)
                }
                .onFailure {
                    _loginState.value = UiState.Error(it.message ?: "Login gagal")
                }
        }
    }

    fun register(name: String, email: String, pass: String, role: String) {
        viewModelScope.launch {
            _registerState.value = UiState.Loading
            repository.register(name, email, pass, role)
                .onSuccess {
                    _registerState.value = UiState.Success(it)
                }
                .onFailure {
                    _registerState.value = UiState.Error(it.message ?: "Registrasi gagal")
                }
        }
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
            _profileComplete.value = null
            _mothersState.value = UiState.Idle
            _childrenState.value = UiState.Idle
            _growthState.value = UiState.Idle
            _immunizationState.value = UiState.Idle
        }
    }

    /**
     * Check if logged-in user already has Mother + Child profile data.
     * If both exist → profileComplete = true → navigate to home.
     * If missing → profileComplete = false → navigate to onboarding.
     */
    fun checkProfileComplete() {
        viewModelScope.launch {
            _profileComplete.value = null // reset
            val mothersResult = repository.getMothers()
            val mothers = mothersResult.getOrDefault(emptyList())
            if (mothers.isEmpty()) {
                _profileComplete.value = false
                return@launch
            }
            _mothersState.value = UiState.Success(mothers)

            val childrenResult = repository.getChildren()
            val children = childrenResult.getOrDefault(emptyList())
            if (children.isEmpty()) {
                // Mother exists but no child — go to child onboarding
                _onboardingMotherId.value = mothers.first().id
                _profileComplete.value = false
                return@launch
            }
            _childrenState.value = UiState.Success(children)

            // Both exist
            _profileComplete.value = true
        }
    }

    fun loadMothers() {
        viewModelScope.launch {
            _mothersState.value = UiState.Loading
            repository.getMothers()
                .onSuccess { _mothersState.value = UiState.Success(it) }
                .onFailure { _mothersState.value = UiState.Error(it.message ?: "Gagal memuat data ibu") }
        }
    }

    fun loadChildren() {
        viewModelScope.launch {
            _childrenState.value = UiState.Loading
            repository.getChildren()
                .onSuccess { _childrenState.value = UiState.Success(it) }
                .onFailure { _childrenState.value = UiState.Error(it.message ?: "Gagal memuat data anak") }
        }
    }

    fun loadGrowthRecords(childId: Int? = null) {
        viewModelScope.launch {
            _growthState.value = UiState.Loading
            repository.getGrowthRecords(childId)
                .onSuccess { _growthState.value = UiState.Success(it) }
                .onFailure { _growthState.value = UiState.Error(it.message ?: "Gagal memuat data pertumbuhan") }
        }
    }

    fun loadRecipes() {
        viewModelScope.launch {
            _recipesState.value = UiState.Loading
            repository.getRecipes()
                .onSuccess { _recipesState.value = UiState.Success(it) }
                .onFailure { _recipesState.value = UiState.Error(it.message ?: "Gagal memuat resep") }
        }
    }

    fun loadImmunizations(childId: Int? = null) {
        viewModelScope.launch {
            _immunizationState.value = UiState.Loading
            repository.getImmunizations(childId)
                .onSuccess { _immunizationState.value = UiState.Success(it) }
                .onFailure { _immunizationState.value = UiState.Error(it.message ?: "Gagal memuat imunisasi") }
        }
    }

    fun saveMother(mother: Mother) {
        viewModelScope.launch {
            _actionState.value = UiState.Loading
            val result = if (mother.id != 0) {
                repository.updateMother(mother.id, mother)
            } else {
                repository.createMother(mother)
            }
            result.onSuccess {
                _actionState.value = UiState.Success("Data Ibu berhasil disimpan")
                loadMothers()
            }.onFailure {
                _actionState.value = UiState.Error(it.message ?: "Gagal menyimpan data ibu")
            }
        }
    }

    /**
     * Save mother during onboarding flow — stores the created mother ID
     * so the child onboarding screen can use it.
     */
    fun saveMotherOnboarding(mother: Mother) {
        viewModelScope.launch {
            _onboardingSaveState.value = UiState.Loading
            repository.createMother(mother)
                .onSuccess {
                    _onboardingMotherId.value = it.id
                    _mothersState.value = UiState.Success(listOf(it))
                    _onboardingSaveState.value = UiState.Success("Data Ibu berhasil disimpan")
                }
                .onFailure {
                    _onboardingSaveState.value = UiState.Error(it.message ?: "Gagal menyimpan data ibu")
                }
        }
    }

    fun deleteMother(id: Int) {
        viewModelScope.launch {
            _actionState.value = UiState.Loading
            repository.deleteMother(id)
                .onSuccess {
                    _actionState.value = UiState.Success("Data Ibu berhasil dihapus")
                    loadMothers()
                }
                .onFailure {
                    _actionState.value = UiState.Error(it.message ?: "Gagal menghapus data ibu")
                }
        }
    }

    fun saveChild(child: Child) {
        viewModelScope.launch {
            _actionState.value = UiState.Loading
            val result = if (child.id != 0) {
                repository.updateChild(child.id, child)
            } else {
                repository.createChild(child)
            }
            result.onSuccess {
                _actionState.value = UiState.Success("Data Anak berhasil disimpan")
                loadChildren()
            }.onFailure {
                _actionState.value = UiState.Error(it.message ?: "Gagal menyimpan data anak")
            }
        }
    }

    /**
     * Save child during onboarding flow — uses the stored onboarding mother ID.
     */
    fun saveChildOnboarding(child: Child) {
        viewModelScope.launch {
            _onboardingSaveState.value = UiState.Loading
            val motherId = _onboardingMotherId.value ?: 0
            val childWithMotherId = child.copy(motherId = motherId)
            repository.createChild(childWithMotherId)
                .onSuccess {
                    _childrenState.value = UiState.Success(listOf(it))
                    _onboardingSaveState.value = UiState.Success("Data Anak berhasil disimpan")
                }
                .onFailure {
                    _onboardingSaveState.value = UiState.Error(it.message ?: "Gagal menyimpan data anak")
                }
        }
    }

    fun deleteChild(id: Int) {
        viewModelScope.launch {
            _actionState.value = UiState.Loading
            repository.deleteChild(id)
                .onSuccess {
                    _actionState.value = UiState.Success("Data Anak berhasil dihapus")
                    loadChildren()
                }
                .onFailure {
                    _actionState.value = UiState.Error(it.message ?: "Gagal menghapus data anak")
                }
        }
    }

    fun resetOnboardingSaveState() {
        _onboardingSaveState.value = UiState.Idle
    }

    fun saveGrowthRecord(record: GrowthRecord) {
        viewModelScope.launch {
            _actionState.value = UiState.Loading
            val result = if (record.id != 0) {
                repository.updateGrowthRecord(record.id, record)
            } else {
                repository.createGrowthRecord(record)
            }
            result.onSuccess {
                _actionState.value = UiState.Success("Data Antropometri berhasil disimpan")
                loadGrowthRecords(record.childId)
            }.onFailure {
                _actionState.value = UiState.Error(it.message ?: "Gagal menyimpan antropometri")
            }
        }
    }

    fun deleteGrowthRecord(id: Int, childId: Int) {
        viewModelScope.launch {
            _actionState.value = UiState.Loading
            repository.deleteGrowthRecord(id)
                .onSuccess {
                    _actionState.value = UiState.Success("Data Antropometri berhasil dihapus")
                    loadGrowthRecords(childId)
                }
                .onFailure {
                    _actionState.value = UiState.Error(it.message ?: "Gagal menghapus antropometri")
                }
        }
    }

    fun saveImmunization(record: VaccinationRecord) {
        viewModelScope.launch {
            _actionState.value = UiState.Loading
            repository.createImmunization(record)
                .onSuccess {
                    _actionState.value = UiState.Success("Riwayat Imunisasi berhasil disimpan")
                    loadImmunizations(record.childId)
                }
                .onFailure {
                    _actionState.value = UiState.Error(it.message ?: "Gagal menyimpan imunisasi")
                }
        }
    }

    fun updateImmunization(id: Int, record: VaccinationRecord) {
        viewModelScope.launch {
            _actionState.value = UiState.Loading
            repository.updateImmunization(id, record)
                .onSuccess {
                    _actionState.value = UiState.Success("Riwayat Imunisasi berhasil diperbarui")
                    loadImmunizations(record.childId)
                }
                .onFailure {
                    _actionState.value = UiState.Error(it.message ?: "Gagal memperbarui imunisasi")
                }
        }
    }

    fun deleteImmunization(id: Int, childId: Int) {
        viewModelScope.launch {
            _actionState.value = UiState.Loading
            repository.deleteImmunization(id)
                .onSuccess {
                    _actionState.value = UiState.Success("Riwayat Imunisasi berhasil dihapus")
                    loadImmunizations(childId)
                }
                .onFailure {
                    _actionState.value = UiState.Error(it.message ?: "Gagal menghapus imunisasi")
                }
        }
    }

    fun resetActionState() {
        _actionState.value = UiState.Idle
    }

    /**
     * Checks whether the mandatory one-time health-profile wizard was already
     * completed for this mother — used by a mother who already has a `main_goals`
     * row (the wizard's last step) so returning users aren't routed through it again.
     */
    fun checkWizardComplete(motherId: Int) {
        viewModelScope.launch {
            _wizardComplete.value = null
            val goals = repository.getMainGoals(motherId).getOrDefault(emptyList())
            _wizardComplete.value = goals.isNotEmpty()
        }
    }

    fun loadHealthCondition(childId: Int) {
        viewModelScope.launch {
            _healthConditionState.value = UiState.Loading
            repository.getHealthConditions(childId)
                .onSuccess { _healthConditionState.value = UiState.Success(it.firstOrNull()) }
                .onFailure { _healthConditionState.value = UiState.Error(it.message ?: "Gagal memuat kondisi kesehatan") }
        }
    }

    fun saveHealthCondition(condition: HealthCondition) {
        viewModelScope.launch {
            _actionState.value = UiState.Loading
            val result = if (condition.id != 0) repository.updateHealthCondition(condition.id, condition) else repository.createHealthCondition(condition)
            result.onSuccess {
                _actionState.value = UiState.Success("Kondisi Kesehatan berhasil disimpan")
            }.onFailure {
                _actionState.value = UiState.Error(it.message ?: "Gagal menyimpan kondisi kesehatan")
            }
        }
    }

    fun loadNutrition(childId: Int) {
        viewModelScope.launch {
            _nutritionState.value = UiState.Loading
            repository.getNutritionRecords(childId)
                .onSuccess { _nutritionState.value = UiState.Success(it.firstOrNull()) }
                .onFailure { _nutritionState.value = UiState.Error(it.message ?: "Gagal memuat data menyusui") }
        }
    }

    fun saveNutrition(record: NutritionRecord) {
        viewModelScope.launch {
            _actionState.value = UiState.Loading
            val result = if (record.id != 0) repository.updateNutritionRecord(record.id, record) else repository.createNutritionRecord(record)
            result.onSuccess {
                _actionState.value = UiState.Success("Data Menyusui & MPASI berhasil disimpan")
            }.onFailure {
                _actionState.value = UiState.Error(it.message ?: "Gagal menyimpan data menyusui")
            }
        }
    }

    fun loadFoodGroupFrequency(childId: Int) {
        viewModelScope.launch {
            _foodGroupState.value = UiState.Loading
            repository.getFoodGroupFrequencies(childId)
                .onSuccess { _foodGroupState.value = UiState.Success(it.firstOrNull()) }
                .onFailure { _foodGroupState.value = UiState.Error(it.message ?: "Gagal memuat asupan pangan") }
        }
    }

    fun saveFoodGroupFrequency(record: FoodGroupFrequency) {
        viewModelScope.launch {
            _actionState.value = UiState.Loading
            val result = if (record.id != 0) repository.updateFoodGroupFrequency(record.id, record) else repository.createFoodGroupFrequency(record)
            result.onSuccess {
                _actionState.value = UiState.Success("Data Asupan Pangan berhasil disimpan")
            }.onFailure {
                _actionState.value = UiState.Error(it.message ?: "Gagal menyimpan asupan pangan")
            }
        }
    }

    fun loadFavoriteLocalFood(childId: Int) {
        viewModelScope.launch {
            _favoriteFoodState.value = UiState.Loading
            repository.getFavoriteLocalFoods(childId)
                .onSuccess { _favoriteFoodState.value = UiState.Success(it.firstOrNull()) }
                .onFailure { _favoriteFoodState.value = UiState.Error(it.message ?: "Gagal memuat pangan favorit") }
        }
    }

    fun saveFavoriteLocalFood(record: FavoriteLocalFood) {
        viewModelScope.launch {
            _actionState.value = UiState.Loading
            val result = if (record.id != 0) repository.updateFavoriteLocalFood(record.id, record) else repository.createFavoriteLocalFood(record)
            result.onSuccess {
                _actionState.value = UiState.Success("Pangan Favorit berhasil disimpan")
            }.onFailure {
                _actionState.value = UiState.Error(it.message ?: "Gagal menyimpan pangan favorit")
            }
        }
    }

    fun loadWellbeing(motherId: Int) {
        viewModelScope.launch {
            _wellbeingState.value = UiState.Loading
            repository.getWellbeingScreenings(motherId)
                .onSuccess { _wellbeingState.value = UiState.Success(it.firstOrNull()) }
                .onFailure { _wellbeingState.value = UiState.Error(it.message ?: "Gagal memuat kondisi psikososial") }
        }
    }

    fun saveWellbeing(screening: WellbeingScreening) {
        viewModelScope.launch {
            _actionState.value = UiState.Loading
            val result = if (screening.id != 0) repository.updateWellbeingScreening(screening.id, screening) else repository.createWellbeingScreening(screening)
            result.onSuccess {
                _wellbeingState.value = UiState.Success(it)
                _actionState.value = UiState.Success("Kondisi Psikososial berhasil disimpan")
            }.onFailure {
                _actionState.value = UiState.Error(it.message ?: "Gagal menyimpan kondisi psikososial")
            }
        }
    }

    fun loadMainGoal(motherId: Int) {
        viewModelScope.launch {
            _mainGoalState.value = UiState.Loading
            repository.getMainGoals(motherId)
                .onSuccess { _mainGoalState.value = UiState.Success(it.firstOrNull()) }
                .onFailure { _mainGoalState.value = UiState.Error(it.message ?: "Gagal memuat tujuan utama") }
        }
    }

    fun saveMainGoal(goal: MainGoal) {
        viewModelScope.launch {
            _actionState.value = UiState.Loading
            val result = if (goal.id != 0) repository.updateMainGoal(goal.id, goal) else repository.createMainGoal(goal)
            result.onSuccess {
                _actionState.value = UiState.Success("Tujuan Utama berhasil disimpan")
                _wizardComplete.value = true
            }.onFailure {
                _actionState.value = UiState.Error(it.message ?: "Gagal menyimpan tujuan utama")
            }
        }
    }
}
