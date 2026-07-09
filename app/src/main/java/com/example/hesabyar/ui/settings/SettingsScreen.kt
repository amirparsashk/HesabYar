package com.example.hesabyar.ui.settings

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hesabyar.ui.theme.HesabYarTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    onLogoutClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Settings",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF071E27)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color(0xFF071E27)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFF3FAFF)
                )
            )
        },
        containerColor = Color(0xFFF3FAFF)
    ) { paddingValues ->
        SettingsContent(
            paddingValues = paddingValues,
            onLogoutClick = onLogoutClick
        )
    }
}

@Composable
fun SettingsContent(
    paddingValues: PaddingValues,
    onLogoutClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Profile Header Section
        ProfileHeaderSection()

        // Main Settings sections
        AppearanceSection()
        SecuritySection()
        AboutSection(onLogoutClick = onLogoutClick)

        // Illustration/Footer
        FooterSection()
    }
}

@Composable
private fun ProfileHeaderSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Surface(
            modifier = Modifier
                .weight(2f)
                .height(100.dp),
            color = Color(0xFFE6F6FF),
            shape = RoundedCornerShape(24.dp),
            border = BorderStroke(1.dp, Color(0xFFDBF1FE))
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(contentAlignment = Alignment.BottomEnd) {
                    Surface(
                        modifier = Modifier.size(60.dp),
                        shape = CircleShape,
                        color = Color(0xFFABF4AC).copy(alpha = 0.5f)
                    ) {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = null,
                            modifier = Modifier.padding(12.dp),
                            tint = Color(0xFF0D631B)
                        )
                    }
                    Surface(
                        modifier = Modifier.size(20.dp),
                        shape = CircleShape,
                        color = Color(0xFF0D631B)
                    ) {
                        Icon(
                            Icons.Default.Edit,
                            contentDescription = null,
                            modifier = Modifier.padding(4.dp),
                            tint = Color.White
                        )
                    }
                }
                Column {
                    Text(
                        "Rachel Wong",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF071E27)
                    )
                    Text(
                        "rachelwong@gmail.com",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFF40493D)
                    )
                }
            }
        }

        Surface(
            modifier = Modifier
                .weight(1f)
                .height(100.dp),
            color = Color(0xFFABF4AC),
            shape = RoundedCornerShape(24.dp)
        ) {
            Column(
                modifier = Modifier.padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    Icons.Default.WorkspacePremium,
                    contentDescription = null,
                    tint = Color(0xFF0D631B),
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    "PREMIUM",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF0D631B)
                )
                Text(
                    "Manage",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color(0xFF0D631B),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun AppearanceSection() {
    var darkMode by remember { mutableStateOf(false) }
    
    SettingsCard(
        title = "Appearance",
        icon = Icons.Default.Palette
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text("Dark Mode", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
                Text("Switch to a darker interface", style = MaterialTheme.typography.labelSmall, color = Color(0xFF40493D))
            }
            Switch(
                checked = darkMode,
                onCheckedChange = { darkMode = it },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = Color(0xFF0D631B),
                    uncheckedThumbColor = Color(0xFF40493D),
                    uncheckedTrackColor = Color(0xFFDBF1FE)
                )
            )
        }
        
        HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = Color(0xFFDBF1FE))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text("Currency", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
                Text("Primary display currency", style = MaterialTheme.typography.labelSmall, color = Color(0xFF40493D))
            }
            Text(
                "USD ($)",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0D631B)
            )
        }
    }
}

@Composable
private fun SecuritySection() {
    var notifications by remember { mutableStateOf(true) }
    
    SettingsCard(
        title = "Security",
        icon = Icons.Default.Security
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text("Push Notifications", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
                Text("Alerts for low balance & limits", style = MaterialTheme.typography.labelSmall, color = Color(0xFF40493D))
            }
            Switch(
                checked = notifications,
                onCheckedChange = { notifications = it },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = Color(0xFF0D631B)
                )
            )
        }
        
        HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = Color(0xFFDBF1FE))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text("Change Password", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
                Text("Last changed 3 months ago", style = MaterialTheme.typography.labelSmall, color = Color(0xFF40493D))
            }
            Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Color(0xFF40493D))
        }
    }
}

@Composable
private fun AboutSection(onLogoutClick: () -> Unit) {
    SettingsCard(
        title = "About App",
        icon = Icons.Default.Info
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AboutSmallCard(label = "Version", value = "2.4.0-build", modifier = Modifier.weight(1f))
            AboutSmallButton(icon = Icons.Default.Help, label = "Help Center", modifier = Modifier.weight(1f))
            AboutSmallButton(icon = Icons.Default.Description, label = "Privacy", modifier = Modifier.weight(1f))
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Column {
                Text(
                    "HesabYar",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF071E27).copy(alpha = 0.4f)
                )
                Text(
                    "Human-centric finance management.",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color(0xFF40493D),
                    fontWeight = FontWeight.Light
                )
            }
            
            Button(
                onClick = onLogoutClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFBA1A1A).copy(alpha = 0.1f),
                    contentColor = Color(0xFFBA1A1A)
                ),
                shape = CircleShape,
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Log Out", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun FooterSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(Color(0xFFABF4AC).copy(alpha = 0.2f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.Savings,
                contentDescription = null,
                modifier = Modifier.size(50.dp),
                tint = Color(0xFF0D631B).copy(alpha = 0.6f)
            )
        }
        Text(
            "STRESS-FREE FINANCE",
            style = MaterialTheme.typography.labelSmall,
            color = Color(0xFF40493D),
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp
        )
    }
}

@Composable
private fun SettingsCard(
    title: String,
    icon: ImageVector,
    content: @Composable ColumnScope.() -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color.White,
        shape = RoundedCornerShape(24.dp),
        border = BorderStroke(1.dp, Color(0xFFDBF1FE))
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Icon(icon, contentDescription = null, tint = Color(0xFF0D631B), modifier = Modifier.size(20.dp))
                Text(
                    title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF071E27)
                )
            }
            content()
        }
    }
}

@Composable
private fun AboutSmallCard(label: String, value: String, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        color = Color(0xFFF3FAFF),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color(0xFFDBF1FE))
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(label, style = MaterialTheme.typography.labelSmall, color = Color(0xFF40493D))
            Text(value, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun AboutSmallButton(icon: ImageVector, label: String, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.clickable { },
        color = Color(0xFFF3FAFF),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color(0xFFDBF1FE))
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(icon, contentDescription = null, modifier = Modifier.size(16.dp), tint = Color(0xFF0D631B))
            Text(label, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    HesabYarTheme {
        SettingsScreen(onBack = {}, onLogoutClick = {})
    }
}
