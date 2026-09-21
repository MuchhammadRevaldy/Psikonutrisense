package com.psikonutrisense.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.psikonutrisense.app.data.model.GrowthRecord
import com.psikonutrisense.app.data.model.LocalRecipe
import com.psikonutrisense.app.ui.MainViewModel
import com.psikonutrisense.app.ui.UiState
import com.psikonutrisense.app.ui.screens.*
import com.psikonutrisense.app.ui.theme.PsikonutrisenseTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PsikonutrisenseTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PsikonutrisenseNavGraph()
                }
            }
        }
    }
}

@Composable
fun PsikonutrisenseNavGraph(
    viewModel: MainViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val autoLoginState by viewModel.autoLoginState.collectAsState()
    val userNameState by viewModel.userNameState.collectAsState()
    val userEmailState by viewModel.userEmailState.collectAsState()
    val userRoleState by viewModel.userRoleState.collectAsState()
    val loginState by viewModel.loginState.collectAsState()
    val registerState by viewModel.registerState.collectAsState()
    val mothersState by viewModel.mothersState.collectAsState()
    val childrenState by viewModel.childrenState.collectAsState()
    val growthState by viewModel.growthState.collectAsState()
    val recipesState by viewModel.recipesState.collectAsState()
    val immunizationState by viewModel.immunizationState.collectAsState()
    val actionState by viewModel.actionState.collectAsState()
    val profileComplete by viewModel.profileComplete.collectAsState()
    val onboardingMotherId by viewModel.onboardingMotherId.collectAsState()
    val onboardingSaveState by viewModel.onboardingSaveState.collectAsState()

    var selectedRecipe by remember { mutableStateOf<LocalRecipe?>(null) }
    var selectedGrowthRecord by remember { mutableStateOf<GrowthRecord?>(null) }

    val currentMother = (mothersState as? UiState.Success)?.data?.firstOrNull()
    val currentChild = (childrenState as? UiState.Success)?.data?.firstOrNull()

    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {
        composable("splash") {
            SplashScreen(
                canAutoLogin = autoLoginState,
                onNavigateNext = { isLoggedIn ->
                    if (isLoggedIn) {
                        // Check if profile is complete before going to home
                        viewModel.checkProfileComplete()
                        navController.navigate("profile_check") {
                            popUpTo("splash") { inclusive = true }
                        }
                    } else {
                        navController.navigate("login") {
                            popUpTo("splash") { inclusive = true }
                        }
                    }
                }
            )
        }

        // Intermediate route: waits for profile check result
        composable("profile_check") {
            LaunchedEffect(profileComplete) {
                when (profileComplete) {
                    true -> {
                        navController.navigate("home") {
                            popUpTo("profile_check") { inclusive = true }
                        }
                    }
                    false -> {
                        // Check if mother exists but child doesn't
                        if (onboardingMotherId != null) {
                            navController.navigate("onboarding_anak") {
                                popUpTo("profile_check") { inclusive = true }
                            }
                        } else {
                            navController.navigate("onboarding_ibu") {
                                popUpTo("profile_check") { inclusive = true }
                            }
                        }
                    }
                    null -> { /* Still loading, show splash-like screen */ }
                }
            }
            // Show a loading screen while checking
            SplashScreen(
                canAutoLogin = true,
                onNavigateNext = {} // No-op, navigation handled by LaunchedEffect above
            )
        }

        composable("login") {
            LoginScreen(
                loginState = loginState,
                onLoginClick = { email, pass, rememberMe -> viewModel.login(email, pass, rememberMe) },
                onNavigateRegister = { navController.navigate("register") },
                onLoginSuccess = {
                    viewModel.checkProfileComplete()
                    navController.navigate("profile_check") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        composable("register") {
            RegisterScreen(
                registerState = registerState,
                onRegisterClick = { name, email, pass, role -> viewModel.register(name, email, pass, role) },
                onNavigateLogin = { navController.navigate("login") },
                onRegisterSuccess = {
                    // New user → always go to onboarding
                    navController.navigate("onboarding_ibu") {
                        popUpTo("register") { inclusive = true }
                    }
                }
            )
        }

        // ===== ONBOARDING FLOW =====
        composable("onboarding_ibu") {
            DataDiriIbuScreen(
                actionState = onboardingSaveState,
                onBackClick = { /* No back during onboarding */ },
                onSaveClick = { mother -> viewModel.saveMotherOnboarding(mother) },
                isOnboarding = true,
                onOnboardingComplete = {
                    viewModel.resetOnboardingSaveState()
                    navController.navigate("onboarding_anak") {
                        popUpTo("onboarding_ibu") { inclusive = true }
                    }
                }
            )
        }

        composable("onboarding_anak") {
            DataDiriAnakScreen(
                actionState = onboardingSaveState,
                onBackClick = { /* No back during onboarding */ },
                onSaveClick = { child -> viewModel.saveChildOnboarding(child) },
                isOnboarding = true,
                onOnboardingComplete = {
                    viewModel.resetOnboardingSaveState()
                    navController.navigate("home") {
                        popUpTo("onboarding_anak") { inclusive = true }
                    }
                }
            )
        }

        // ===== MAIN APP =====
        composable("home") {
            LaunchedEffect(Unit) {
                viewModel.loadMothers()
                viewModel.loadChildren()
                viewModel.loadGrowthRecords()
                viewModel.loadRecipes()
                viewModel.loadImmunizations()
            }
            HomeScreen(
                mothersState = mothersState,
                childrenState = childrenState,
                growthState = growthState,
                immunizationState = immunizationState,
                onNavigateToGrowthChart = { navController.navigate("growth_chart") },
                onNavigateToResep = { navController.navigate("resep_list") },
                onNavigateToImunisasi = { navController.navigate("riwayat_imunisasi") },
                onNavigateToPsikososial = { navController.navigate("kondisi_psikososial") },
                onNavigateToEdukasi = { navController.navigate("edukasi") },
                onNavigateToDataIbu = { navController.navigate("data_ibu") },
                onNavigateToDataAnak = { navController.navigate("data_anak") },
                onNavigateToAntropometri = {
                    selectedGrowthRecord = null
                    navController.navigate("antropometri")
                },
                onNavigateToIntervensi = { navController.navigate("intervensi") },
                onNavigateToAccount = { navController.navigate("account") }
            )
        }

        composable("account") {
            AccountScreen(
                userName = userNameState,
                userEmail = userEmailState,
                userRole = userRoleState,
                onBackClick = { navController.popBackStack() },
                onNavigateToDataIbu = { navController.navigate("data_ibu") },
                onNavigateToDataAnak = { navController.navigate("data_anak") },
                onLogoutClick = {
                    viewModel.logout()
                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable("growth_chart") {
            GrowthChartScreen(
                growthState = growthState,
                onBackClick = { navController.popBackStack() },
                child = currentChild,
                onAddRecordClick = {
                    selectedGrowthRecord = null
                    navController.navigate("antropometri")
                },
                onEditRecordClick = { record ->
                    selectedGrowthRecord = record
                    navController.navigate("antropometri")
                },
                onDeleteRecordClick = { record ->
                    viewModel.deleteGrowthRecord(record.id, record.childId)
                }
            )
        }

        composable("intervensi") {
            IntervensiScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable("resep_list") {
            ResepListScreen(
                recipesState = recipesState,
                onBackClick = { navController.popBackStack() },
                onRecipeClick = { recipe ->
                    selectedRecipe = recipe
                    navController.navigate("resep_detail")
                }
            )
        }

        composable("resep_detail") {
            ResepDetailScreen(
                recipe = selectedRecipe,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable("edukasi") {
            EdukasiScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable("data_ibu") {
            LaunchedEffect(Unit) { viewModel.resetActionState() }
            DataDiriIbuScreen(
                actionState = actionState,
                onBackClick = { navController.popBackStack() },
                onSaveClick = { mother -> viewModel.saveMother(mother) },
                existing = currentMother,
                onDeleteClick = { currentMother?.let { viewModel.deleteMother(it.id) } }
            )
        }

        composable("data_anak") {
            LaunchedEffect(Unit) { viewModel.resetActionState() }
            DataDiriAnakScreen(
                actionState = actionState,
                onBackClick = { navController.popBackStack() },
                onSaveClick = { child -> viewModel.saveChild(child) },
                existing = currentChild,
                onDeleteClick = { currentChild?.let { viewModel.deleteChild(it.id) } }
            )
        }

        composable("antropometri") {
            LaunchedEffect(Unit) { viewModel.resetActionState() }
            AntropometriScreen(
                actionState = actionState,
                onBackClick = { navController.popBackStack() },
                onSaveClick = { record -> viewModel.saveGrowthRecord(record) },
                childId = currentChild?.id ?: 0,
                existing = selectedGrowthRecord
            )
        }

        composable("kondisi_kesehatan") {
            KondisiKesehatanScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable("riwayat_menyusui") {
            RiwayatMenyusuiScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable("asupan_pangan") {
            AsupanPanganScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable("pangan_favorit") {
            PanganFavoritScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable("riwayat_imunisasi") {
            RiwayatImunisasiScreen(
                immunizationState = immunizationState,
                onBackClick = { navController.popBackStack() },
                childId = currentChild?.id ?: 0,
                onMarkDone = { record ->
                    if (record.id != 0) {
                        viewModel.updateImmunization(record.id, record)
                    } else {
                        viewModel.saveImmunization(record)
                    }
                },
                onDelete = { record -> viewModel.deleteImmunization(record.id, record.childId) }
            )
        }

        composable("kondisi_psikososial") {
            KondisiPsikososialScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable("tujuan_utama") {
            TujuanUtamaScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
