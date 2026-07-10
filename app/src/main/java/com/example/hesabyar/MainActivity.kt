package com.example.hesabyar

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hesabyar.ui.auth.LoginScreen
import com.example.hesabyar.ui.auth.LoginViewModel
import com.example.hesabyar.ui.auth.SignUpScreen
import com.example.hesabyar.ui.auth.SignUpViewModel
import com.example.hesabyar.ui.dashboard.DashboardScreen
import com.example.hesabyar.ui.dashboard.DashboardViewModel
import com.example.hesabyar.ui.history.HistoryScreen
import com.example.hesabyar.ui.history.HistoryViewModel
import com.example.hesabyar.ui.profile.ProfileScreen
import com.example.hesabyar.ui.profile.ProfileViewModel
import com.example.hesabyar.ui.settings.SettingsScreen
import com.example.hesabyar.ui.settings.SettingsViewModel
import com.example.hesabyar.ui.splash.SplashContract
import com.example.hesabyar.ui.splash.SplashScreen
import com.example.hesabyar.ui.splash.SplashViewModel
import com.example.hesabyar.ui.stats.StatsScreen
import com.example.hesabyar.ui.stats.StatsViewModel
import com.example.hesabyar.ui.theme.HesabYarTheme
import com.example.hesabyar.ui.transaction.AddTransactionScreen
import com.example.hesabyar.ui.transaction.AddTransactionViewModel
import com.example.hesabyar.ui.transaction.TransactionDetailScreen
import com.example.hesabyar.ui.transaction.TransactionDetailViewModel

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
                    var selectedTransactionId by remember { mutableLongStateOf(-1L) }
                    
                    val context = LocalContext.current
                    val app = context.applicationContext as HesabYarApplication
                    
                    val splashViewModel: SplashViewModel = viewModel()
                    val loginViewModel: LoginViewModel = viewModel(factory = object : ViewModelProvider.Factory {
                        override fun <T : ViewModel> create(modelClass: Class<T>): T = LoginViewModel(app.userRepository) as T
                    })
                    val signUpViewModel: SignUpViewModel = viewModel(factory = object : ViewModelProvider.Factory {
                        override fun <T : ViewModel> create(modelClass: Class<T>): T = SignUpViewModel(app.userRepository) as T
                    })
                    val dashboardViewModel: DashboardViewModel = viewModel(factory = object : ViewModelProvider.Factory {
                        override fun <T : ViewModel> create(modelClass: Class<T>): T = DashboardViewModel(app.financeRepository) as T
                    })
                    val historyViewModel: HistoryViewModel = viewModel(factory = object : ViewModelProvider.Factory {
                        override fun <T : ViewModel> create(modelClass: Class<T>): T = HistoryViewModel(app.financeRepository) as T
                    })
                    val statsViewModel: StatsViewModel = viewModel(factory = object : ViewModelProvider.Factory {
                        override fun <T : ViewModel> create(modelClass: Class<T>): T = StatsViewModel(app.financeRepository) as T
                    })
                    val profileViewModel: ProfileViewModel = viewModel(factory = object : ViewModelProvider.Factory {
                        override fun <T : ViewModel> create(modelClass: Class<T>): T = ProfileViewModel(app.userRepository) as T
                    })
                    val settingsViewModel: SettingsViewModel = viewModel(factory = object : ViewModelProvider.Factory {
                        override fun <T : ViewModel> create(modelClass: Class<T>): T = SettingsViewModel(app.userRepository) as T
                    })
                    val addTransactionViewModel: AddTransactionViewModel = viewModel(factory = object : ViewModelProvider.Factory {
                        override fun <T : ViewModel> create(modelClass: Class<T>): T = AddTransactionViewModel(app.financeRepository) as T
                    })
                    val transactionDetailViewModel: TransactionDetailViewModel = viewModel(factory = object : ViewModelProvider.Factory {
                        override fun <T : ViewModel> create(modelClass: Class<T>): T = TransactionDetailViewModel(app.financeRepository) as T
                    })

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

                    when (currentScreen) {
                        "splash" -> SplashScreen(viewModel = splashViewModel)
                        "login" -> LoginScreen(
                            viewModel = loginViewModel,
                            onSignUpClick = { currentScreen = "signup" },
                            onLoginSuccess = { currentScreen = "dashboard" }
                        )
                        "signup" -> SignUpScreen(
                            viewModel = signUpViewModel,
                            onLoginClick = { currentScreen = "login" },
                            onSignUpSuccess = { currentScreen = "dashboard" }
                        )
                        "dashboard" -> DashboardScreen(
                            viewModel = dashboardViewModel,
                            onAddTransaction = { currentScreen = "add_transaction" },
                            onNavigateToStats = { currentScreen = "stats" },
                            onNavigateToHistory = { currentScreen = "history" },
                            onNavigateToProfile = { currentScreen = "profile" },
                            onTransactionClick = { id ->
                                selectedTransactionId = id
                                currentScreen = "transaction_detail"
                            }
                        )
                        "history" -> HistoryScreen(
                            viewModel = historyViewModel,
                            onDashboardClick = { currentScreen = "dashboard" },
                            onStatsClick = { currentScreen = "stats" },
                            onProfileClick = { currentScreen = "profile" },
                            onAddTransaction = { currentScreen = "add_transaction" },
                            onTransactionClick = { id ->
                                selectedTransactionId = id
                                currentScreen = "transaction_detail"
                            }
                        )
                        "stats" -> StatsScreen(
                            viewModel = statsViewModel,
                            onDashboardClick = { currentScreen = "dashboard" },
                            onHistoryClick = { currentScreen = "history" },
                            onProfileClick = { currentScreen = "profile" },
                            onAddTransaction = { currentScreen = "add_transaction" }
                        )
                        "profile" -> ProfileScreen(
                            viewModel = profileViewModel,
                            onDashboardClick = { currentScreen = "dashboard" },
                            onHistoryClick = { currentScreen = "history" },
                            onStatsClick = { currentScreen = "stats" },
                            onSettingsClick = { currentScreen = "settings" },
                            onLogoutClick = { currentScreen = "login" }
                        )
                        "add_transaction" -> AddTransactionScreen(
                            viewModel = addTransactionViewModel,
                            onBack = { currentScreen = "dashboard" }
                        )
                        "settings" -> SettingsScreen(
                            viewModel = settingsViewModel,
                            onBack = { currentScreen = "profile" },
                            onLogoutClick = { currentScreen = "login" }
                        )
                        "transaction_detail" -> TransactionDetailScreen(
                            transactionId = selectedTransactionId,
                            viewModel = transactionDetailViewModel,
                            onBack = { currentScreen = "history" },
                            onEdit = { /* TODO */ }
                        )
                    }
                }
            }
        }
    }
}
