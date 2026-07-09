package com.example.hesabyar.ui.dashboard

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hesabyar.ui.components.MainScaffold
import com.example.hesabyar.ui.theme.HesabYarTheme

@Composable
fun DashboardScreen(
    onAddTransaction: () -> Unit = {},
    onNavigateToStats: () -> Unit = {},
    onNavigateToHistory: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {},
    onTransactionClick: () -> Unit = {}
) {
    MainScaffold(
        currentScreen = "dashboard",
        onDashboardClick = { /* Already here */ },
        onHistoryClick = onNavigateToHistory,
        onStatsClick = onNavigateToStats,
        onProfileClick = onNavigateToProfile,
        onAddTransactionClick = onAddTransaction
    ) { paddingValues ->
        DashboardContent(
            paddingValues = paddingValues,
            onAddTransaction = onAddTransaction,
            onTransactionClick = onTransactionClick
        )
    }
}

@Composable
fun DashboardContent(
    paddingValues: PaddingValues,
    onAddTransaction: () -> Unit = {},
    onTransactionClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Balance Card
        BalanceCard()

        // Stats Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            StatItem(
                title = "Total Income",
                amount = "$12,450",
                icon = Icons.Default.ArrowDownward,
                iconColor = Color(0xFF286B33),
                modifier = Modifier.weight(1f)
            )
            StatItem(
                title = "Total Expenses",
                amount = "$8,120",
                icon = Icons.Default.ArrowUpward,
                iconColor = Color(0xFFBA1A1A),
                modifier = Modifier.weight(1f),
                borderColor = Color(0xFFBA1A1A).copy(alpha = 0.2f)
            )
        }

        // Quick Actions
        Column {
            Text(
                "Quick Actions",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                QuickActionButton(
                    text = "Add Income",
                    icon = Icons.Default.AddCircle,
                    containerColor = Color(0xFF0D631B),
                    contentColor = Color.White,
                    modifier = Modifier.weight(1f),
                    onClick = onAddTransaction
                )
                QuickActionButton(
                    text = "Add Expense",
                    icon = Icons.Default.RemoveCircle,
                    containerColor = Color(0xFF071E27),
                    contentColor = Color.White,
                    modifier = Modifier.weight(1f),
                    onClick = onAddTransaction
                )
            }
        }

        // Recent Transactions
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Recent Transactions",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    "See All",
                    style = MaterialTheme.typography.labelLarge,
                    color = Color(0xFF0D631B)
                )
            }

            TransactionItem(
                title = "Grocery Store",
                date = "Today, 2:45 PM",
                amount = "-$120.50",
                icon = Icons.Default.ShoppingCart,
                iconContainerColor = Color(0xFFABF4AC),
                isExpense = true,
                onClick = onTransactionClick
            )
            TransactionItem(
                title = "Freelance Salary",
                date = "Yesterday, 10:15 AM",
                amount = "+$2,400.00",
                icon = Icons.Default.Payments,
                iconContainerColor = Color(0xFFCCEACD),
                isExpense = false,
                onClick = onTransactionClick
            )
            TransactionItem(
                title = "Dinner Out",
                date = "Oct 24, 8:30 PM",
                amount = "-$85.00",
                icon = Icons.Default.Restaurant,
                iconContainerColor = Color(0xFFCFE6F2),
                isExpense = true,
                onClick = onTransactionClick
            )
            TransactionItem(
                title = "Electricity Bill",
                date = "Oct 22, 11:00 AM",
                amount = "-$142.20",
                icon = Icons.Default.Bolt,
                iconContainerColor = Color(0xFF88D982),
                isExpense = true,
                onClick = onTransactionClick
            )
        }
    }
}

@Composable
fun BalanceCard() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp),
        shape = RoundedCornerShape(24.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.linearGradient(
                        colors = listOf(Color(0xFF0D631B), Color(0xFF1B6D24))
                    )
                )
                .padding(24.dp)
        ) {
            Column {
                Text(
                    "CURRENT BALANCE",
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.White.copy(alpha = 0.8f),
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "$42,500.00",
                    style = MaterialTheme.typography.displayMedium,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        color = Color(0xFF2E7D32),
                        shape = CircleShape,
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.TrendingUp,
                            contentDescription = null,
                            tint = Color(0xFFCBFFC2),
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "+2.5% from last month",
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                }
            }
        }
    }
}

@Composable
fun StatItem(
    title: String,
    amount: String,
    icon: ImageVector,
    iconColor: Color,
    modifier: Modifier = Modifier,
    borderColor: Color = Color.Transparent
) {
    Surface(
        modifier = modifier,
        color = Color(0xFFDBF1FE),
        shape = RoundedCornerShape(16.dp),
        border = if (borderColor != Color.Transparent) BorderStroke(2.dp, borderColor) else null
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(title, style = MaterialTheme.typography.labelLarge, color = iconColor)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(amount, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun QuickActionButton(
    text: String,
    icon: ImageVector,
    containerColor: Color,
    contentColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(72.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(icon, contentDescription = null)
            Text(text, style = MaterialTheme.typography.labelLarge)
        }
    }
}

@Composable
fun TransactionItem(
    title: String,
    date: String,
    amount: String,
    icon: ImageVector,
    iconContainerColor: Color,
    isExpense: Boolean,
    onClick: () -> Unit = {}
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        color = Color(0xFFE6F6FF),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(48.dp),
                color = iconContainerColor,
                shape = CircleShape
            ) {
                Icon(
                    icon,
                    contentDescription = null,
                    modifier = Modifier.padding(12.dp),
                    tint = Color.Black.copy(alpha = 0.7f)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                Text(date, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Text(
                amount,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = if (isExpense) Color(0xFFBA1A1A) else Color(0xFF286B33)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    HesabYarTheme {
        DashboardScreen()
    }
}
