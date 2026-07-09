package com.example.hesabyar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import android.app.Activity
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import com.example.hesabyar.ui.auth.LoginScreen
import com.example.hesabyar.ui.auth.SignUpScreen
import com.example.hesabyar.ui.components.MainScaffold
import com.example.hesabyar.ui.dashboard.DashboardContent
import com.example.hesabyar.ui.history.HistoryContent
import com.example.hesabyar.ui.profile.ProfileContent
import com.example.hesabyar.ui.settings.SettingsScreen
import com.example.hesabyar.ui.stats.StatsContent
import com.example.hesabyar.ui.transaction.AddTransactionScreen
import com.example.hesabyar.ui.transaction.TransactionDetailScreen
import com.example.hesabyar.ui.splash.SplashContract
import com.example.hesabyar.ui.splash.SplashScreen
import com.example.hesabyar.ui.splash.SplashViewModel
import com.example.hesabyar.ui.theme.HesabYarTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HesabYarTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var currentScreen by remember { mutableStateOf("splash") }
                    var showExitDialog by remember { mutableStateOf(false) }
                    val context = LocalContext.current
                    val splashViewModel: SplashViewModel = viewModel()

                    BackHandler {
                        when (currentScreen) {
                            "dashboard" -> showExitDialog = true
                            "history", "stats", "profile" -> currentScreen = "dashboard"
                            "add_transaction" -> currentScreen = "dashboard"
                            "settings" -> currentScreen = "profile"
                            "transaction_detail" -> currentScreen = "history"
                            "signup" -> currentScreen = "login"
                            "login" -> showExitDialog = true
                            "splash" -> (context as? Activity)?.finish()
                            else -> currentScreen = "dashboard"
                        }
                    }

                    if (showExitDialog) {
                        AlertDialog(
                            onDismissRequest = { showExitDialog = false },
                            title = { Text("Exit App") },
                            text = { Text("Are you sure you want to exit HesabYar?") },
                            confirmButton = {
                                TextButton(onClick = { (context as? Activity)?.finish() }) {
                                    Text("Yes", color = Color(0xFF0D631B))
                                }
                            },
                            dismissButton = {
                                TextButton(onClick = { showExitDialog = false }) {
                                    Text("No", color = Color.Gray)
                                }
                            },
                            containerColor = Color(0xFFF3FAFF)
                        )
                    }

                    LaunchedEffect(splashViewModel) {
                        splashViewModel.effect.collect { effect ->
                            when (effect) {
                                is SplashContract.Effect.NavigateToMain -> {
                                    currentScreen = "login"
                                }
                            }
                        }
                    }

                    val mainScreens = listOf("dashboard", "history", "stats", "profile")
                    val subScreens = listOf("add_transaction", "settings", "transaction_detail")

                    if (currentScreen in mainScreens || currentScreen in subScreens) {
                        MainScaffold(
                            currentScreen = when (currentScreen) {
                                "settings" -> "profile"
                                "transaction_detail" -> "history"
                                else -> if (currentScreen in mainScreens) currentScreen else "dashboard"
                            },
                            onDashboardClick = { currentScreen = "dashboard" },
                            onHistoryClick = { currentScreen = "history" },
                            onStatsClick = { currentScreen = "stats" },
                            onProfileClick = { currentScreen = "profile" },
                            onAddTransactionClick = { currentScreen = "add_transaction" },
                            showBottomBar = currentScreen in mainScreens,
                            showFloatingActionButton = currentScreen == "dashboard" || currentScreen == "history",
                            title = when (currentScreen) {
                                "dashboard" -> "HesabYar"
                                "history" -> "History"
                                "stats" -> "Analytics"
                                "profile" -> "Profile"
                                "add_transaction" -> "New Entry"
                                "settings" -> "Settings"
                                "transaction_detail" -> "Transaction Details"
                                else -> "HesabYar"
                            },
                            navigationIcon = {
                                if (currentScreen in subScreens) {
                                    IconButton(onClick = {
                                        currentScreen = when (currentScreen) {
                                            "add_transaction" -> "dashboard"
                                            "settings" -> "profile"
                                            "transaction_detail" -> "history"
                                            else -> "dashboard"
                                        }
                                    }) {
                                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                                    }
                                } else {
                                    IconButton(onClick = { /* TODO */ }) {
                                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                                    }
                                }
                            }
                        ) { paddingValues ->
                            when (currentScreen) {
                                "dashboard" -> DashboardContent(
                                    paddingValues = paddingValues,
                                    onAddTransaction = { currentScreen = "add_transaction" },
                                    onTransactionClick = { currentScreen = "transaction_detail" }
                                )
                                "history" -> HistoryContent(
                                    paddingValues = paddingValues,
                                    onTransactionClick = { currentScreen = "transaction_detail" }
                                )
                                "stats" -> StatsContent(paddingValues = paddingValues)
                                "profile" -> ProfileContent(
                                    paddingValues = paddingValues,
                                    onSettingsClick = { currentScreen = "settings" },
                                    onLogoutClick = { currentScreen = "login" }
                                )
                                "add_transaction" -> com.example.hesabyar.ui.transaction.AddTransactionContent(
                                    paddingValues = paddingValues,
                                    onBack = { currentScreen = "dashboard" }
                                )
                                "settings" -> com.example.hesabyar.ui.settings.SettingsContent(
                                    paddingValues = paddingValues,
                                    onLogoutClick = { currentScreen = "login" }
                                )
                                "transaction_detail" -> com.example.hesabyar.ui.transaction.TransactionDetailContent(
                                    paddingValues = paddingValues,
                                    onEdit = { /* TODO */ },
                                    onDelete = { /* TODO */ }
                                )
                            }
                        }
                    } else {
                        when (currentScreen) {
                            "splash" -> SplashScreen(viewModel = splashViewModel)
                            "login" -> LoginScreen(
                                onSignUpClick = { currentScreen = "signup" },
                                onLoginSuccess = { currentScreen = "dashboard" }
                            )
                            "signup" -> SignUpScreen(
                                onLoginClick = { currentScreen = "login" },
                                onSignUpSuccess = { currentScreen = "dashboard" }
                            )
                        }
                    }
                }
            }
        }
    }
}
