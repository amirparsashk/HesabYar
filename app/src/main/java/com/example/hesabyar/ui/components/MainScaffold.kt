package com.example.hesabyar.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ReceiptLong
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(
    currentScreen: String,
    onDashboardClick: () -> Unit,
    onHistoryClick: () -> Unit,
    onStatsClick: () -> Unit,
    onProfileClick: () -> Unit,
    onAddTransactionClick: () -> Unit,
    title: String = "HesabYar",
    showTopBar: Boolean = true,
    showBottomBar: Boolean = true,
    showFloatingActionButton: Boolean = true,
    navigationIcon: @Composable () -> Unit = {
        IconButton(onClick = { /* TODO */ }) {
            Icon(Icons.Default.Menu, contentDescription = "Menu")
        }
    },
    actions: @Composable RowScope.() -> Unit = {
        Box(
            modifier = Modifier
                .padding(end = 16.dp)
                .size(40.dp)
                .clip(CircleShape)
                .border(2.dp, Color(0xFF2E7D32), CircleShape)
        ) {
            Icon(
                Icons.Default.Person,
                contentDescription = "Profile",
                modifier = Modifier.fillMaxSize().padding(4.dp)
            )
        }
    },
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            if (showTopBar) {
                TopAppBar(
                    title = {
                        Text(
                            title,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF0D631B)
                        )
                    },
                    navigationIcon = navigationIcon,
                    actions = actions,
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFFF3FAFF)
                    )
                )
            }
        },
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(
                    containerColor = Color(0xFFDBF1FE),
                    tonalElevation = 8.dp
                ) {
                    NavigationBarItem(
                        selected = currentScreen == "dashboard",
                        onClick = onDashboardClick,
                        icon = { Icon(Icons.Default.Dashboard, contentDescription = null) },
                        label = { Text("Dashboard") },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(0xFF2E7238),
                            selectedTextColor = Color(0xFF2E7238),
                            indicatorColor = Color(0xFFABF4AC)
                        )
                    )
                    NavigationBarItem(
                        selected = currentScreen == "history",
                        onClick = onHistoryClick,
                        icon = { Icon(Icons.AutoMirrored.Filled.ReceiptLong, contentDescription = null) },
                        label = { Text("History") },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(0xFF2E7238),
                            selectedTextColor = Color(0xFF2E7238),
                            indicatorColor = Color(0xFFABF4AC)
                        )
                    )
                    NavigationBarItem(
                        selected = currentScreen == "stats",
                        onClick = onStatsClick,
                        icon = { Icon(Icons.Default.Analytics, contentDescription = null) },
                        label = { Text("Stats") },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(0xFF2E7238),
                            selectedTextColor = Color(0xFF2E7238),
                            indicatorColor = Color(0xFFABF4AC)
                        )
                    )
                    NavigationBarItem(
                        selected = currentScreen == "profile",
                        onClick = onProfileClick,
                        icon = { Icon(Icons.Default.Person, contentDescription = null) },
                        label = { Text("Profile") },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(0xFF2E7238),
                            selectedTextColor = Color(0xFF2E7238),
                            indicatorColor = Color(0xFFABF4AC)
                        )
                    )
                }
            }
        },
        floatingActionButton = {
            if (showFloatingActionButton) {
                FloatingActionButton(
                    onClick = onAddTransactionClick,
                    containerColor = Color(0xFF0D631B),
                    contentColor = Color.White,
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                }
            }
        },
        containerColor = Color(0xFFF3FAFF),
        content = content
    )
}
