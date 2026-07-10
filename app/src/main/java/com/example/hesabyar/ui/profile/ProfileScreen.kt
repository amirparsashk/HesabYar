package com.example.hesabyar.ui.profile

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.hesabyar.ui.components.MainScaffold

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    onDashboardClick: () -> Unit = {},
    onHistoryClick: () -> Unit = {},
    onStatsClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {},
    onLogoutClick: () -> Unit = {}
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is ProfileContract.Effect.NavigateToLogin -> onLogoutClick()
            }
        }
    }

    MainScaffold(
        currentScreen = "profile",
        onDashboardClick = onDashboardClick,
        onHistoryClick = onHistoryClick,
        onStatsClick = onStatsClick,
        onProfileClick = { /* Already here */ },
        onAddTransactionClick = { /* TODO */ }
    ) { paddingValues ->
        ProfileContent(
            state = state,
            onIntent = { viewModel.handleIntent(it) },
            paddingValues = paddingValues,
            onSettingsClick = onSettingsClick
        )
    }
}

@Composable
fun ProfileContent(
    state: ProfileContract.State,
    onIntent: (ProfileContract.Intent) -> Unit,
    paddingValues: PaddingValues,
    onSettingsClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        // Profile Header
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(contentAlignment = Alignment.BottomEnd) {
                Surface(
                    modifier = Modifier.size(96.dp),
                    shape = CircleShape,
                    color = Color(0xFFABF4AC).copy(alpha = 0.5f),
                    border = BorderStroke(4.dp, Color(0xFFABF4AC))
                ) {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier.padding(16.dp),
                        tint = Color(0xFF0D631B)
                    )
                }
                SmallFloatingActionButton(
                    onClick = { /* TODO */ },
                    containerColor = Color(0xFF0D631B),
                    contentColor = Color.White,
                    shape = CircleShape,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit Profile", modifier = Modifier.size(16.dp))
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                state.userName,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF071E27)
            )
            Text(
                state.userEmail,
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF40493D)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Stats Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ProfileStatCard(
                label = "ACCOUNT TIER",
                value = state.accountTier,
                modifier = Modifier.weight(1f)
            )
            ProfileStatCard(
                label = "MEMBER SINCE",
                value = state.memberSince,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Settings List
        Text(
            "PREFERENCES",
            style = MaterialTheme.typography.labelLarge,
            color = Color(0xFF40493D),
            modifier = Modifier.padding(start = 8.dp, bottom = 8.dp)
        )

        SettingsItem(
            icon = Icons.Default.ManageAccounts,
            title = "Account Settings",
            subtitle = "Personal info & preferences",
            onClick = onSettingsClick
        )
        SettingsItem(
            icon = Icons.Default.Notifications,
            title = "Notifications",
            subtitle = "Alerts & weekly reports",
            badgeCount = 2
        )
        SettingsItem(
            icon = Icons.Default.Lock,
            title = "Privacy",
            subtitle = "Security & data exports"
        )
        SettingsItem(
            icon = Icons.Default.Payments,
            title = "Currency",
            subtitle = "Current: IRR (Toman)"
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Logout Button
        OutlinedButton(
            onClick = { onIntent(ProfileContract.Intent.Logout) },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(2.dp, Color(0xFFBA1A1A).copy(alpha = 0.2f)),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFBA1A1A))
        ) {
            Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Logout", fontWeight = FontWeight.Bold)
        }

        Text(
            "HesabYar v2.4.1",
            style = MaterialTheme.typography.labelSmall,
            color = Color(0xFF40493D),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
    }
}

@Composable
fun ProfileStatCard(label: String, value: String, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        color = Color(0xFFDBF1FE),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                label,
                style = MaterialTheme.typography.labelSmall,
                color = Color(0xFF40493D),
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                value,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0D631B)
            )
        }
    }
}

@Composable
fun SettingsItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    badgeCount: Int? = null,
    onClick: () -> Unit = {}
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        color = Color(0xFFE6F6FF),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(40.dp),
                color = Color(0xFFABF4AC),
                shape = CircleShape
            ) {
                Icon(
                    icon,
                    contentDescription = null,
                    modifier = Modifier.padding(10.dp),
                    tint = Color(0xFF2E7238)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text(subtitle, style = MaterialTheme.typography.labelSmall, color = Color(0xFF40493D))
            }
            if (badgeCount != null) {
                Surface(
                    color = Color(0xFF0D631B),
                    shape = CircleShape,
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    Text(
                        badgeCount.toString(),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                        color = Color.White,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Icon(
                Icons.Default.ChevronRight,
                contentDescription = null,
                tint = Color(0xFF40493D)
            )
        }
    }
}
