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

            val childrenResult = repository.getChildren()
            val children = childrenResult.getOrDefault(emptyList())
            if (children.isEmpty()) {
                // Mother exists but no child — go to child onboarding
                _onboardingMotherId.value = mothers.first().id
                _profileComplete.value = false
                return@launch
            }

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
}
