package com.psikonutrisense.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ChildCare
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.psikonutrisense.app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen(
    userName: String?,
    userEmail: String?,
    userRole: String?,
    onBackClick: () -> Unit,
    onNavigateToDataIbu: () -> Unit,
    onNavigateToDataAnak: () -> Unit,
    onLogoutClick: () -> Unit
) {
    var showLogoutConfirm by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Akun Saya", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PrimaryRose,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(BackgroundSoftPink)
                .padding(16.dp)
        ) {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .background(CardLightPink, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Person, contentDescription = null, tint = PrimaryRose, modifier = Modifier.size(28.dp))
                    }
                    Column {
                        Text(
                            text = userName?.takeIf { it.isNotBlank() } ?: "Pengguna",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextDarkTitle
                        )
                        Text(
                            text = userEmail?.takeIf { it.isNotBlank() } ?: "-",
                            fontSize = 13.sp,
                            color = TextMuted
                        )
                        if (!userRole.isNullOrBlank()) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Surface(shape = RoundedCornerShape(8.dp), color = BadgePinkBg) {
                                Text(
                                    text = userRole.replaceFirstChar { it.uppercase() },
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text("Kelola Data", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextDarkTitle)
            Spacer(modifier = Modifier.height(8.dp))

            AccountMenuItem(
                icon = Icons.Default.Person,
                label = "Edit Data Diri Ibu",
                onClick = onNavigateToDataIbu
            )
            Spacer(modifier = Modifier.height(8.dp))
            AccountMenuItem(
                icon = Icons.Default.ChildCare,
                label = "Edit Data Diri Anak",
                onClick = onNavigateToDataAnak
            )

            Spacer(modifier = Modifier.weight(1f))

            OutlinedButton(
                onClick = { showLogoutConfirm = true },
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Icon(Icons.Default.Logout, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Keluar (Logout)", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }

    if (showLogoutConfirm) {
        AlertDialog(
            onDismissRequest = { showLogoutConfirm = false },
            title = { Text("Keluar dari Akun?") },
            text = { Text("Anda perlu login kembali untuk mengakses data Anda.") },
            confirmButton = {
                TextButton(onClick = {
                    showLogoutConfirm = false
                    onLogoutClick()
                }) {
                    Text("Keluar", color = Color.Red, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutConfirm = false }) {
                    Text("Batal")
                }
            }
        )
    }
}

@Composable
private fun AccountMenuItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(icon, contentDescription = null, tint = PrimaryRose, modifier = Modifier.size(20.dp))
            Text(label, fontSize = 14.sp, fontWeight = FontWeight.Medium, color = TextDarkTitle)
        }
    }
}
