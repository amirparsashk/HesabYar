package com.example.hesabyar.ui.stats

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hesabyar.ui.components.MainScaffold

@Composable
fun StatsScreen(
    viewModel: StatsViewModel,
    onDashboardClick: () -> Unit = {},
    onHistoryClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    onAddTransaction: () -> Unit = {}
) {
    val state by viewModel.state.collectAsState()

    MainScaffold(
        currentScreen = "stats",
        onDashboardClick = onDashboardClick,
        onHistoryClick = onHistoryClick,
        onStatsClick = { /* Already here */ },
        onProfileClick = onProfileClick,
        onAddTransactionClick = onAddTransaction
    ) { paddingValues ->
        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            StatsContent(state = state, paddingValues = paddingValues)
        }
    }
}

@Composable
fun StatsContent(state: StatsContract.State, paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Hero Stats Intro
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                "Financial Insights",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0D631B)
            )
            Text(
                "Here is a summary of your financial activity based on your transactions.",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Income / Spent Summary
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SummaryCard(
                title = "INCOME",
                amount = "$${state.totalIncome}",
                amountColor = Color(0xFF2E7D32),
                modifier = Modifier.weight(1f)
            )
            SummaryCard(
                title = "SPENT",
                amount = "$${state.totalExpense}",
                amountColor = Color(0xFFBA1A1A),
                modifier = Modifier.weight(1f)
            )
        }

        // Donut Chart
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentAlignment = Alignment.Center
        ) {
            DonutChart(
                modifier = Modifier.size(160.dp),
                progress = 1f, // Simplified
                centerText = "$${state.totalExpense}",
                label = "TOTAL SPENT"
            )
        }

        // Category Breakdown
        CategoryBreakdown(state.categoryBreakdown)
    }
}

@Composable
fun SummaryCard(
    title: String,
    amount: String,
    amountColor: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        color = Color(0xFFE6F6FF),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color(0xFFCFE6F2))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                title,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                letterSpacing = 1.sp
            )
            Text(
                amount,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = amountColor
            )
        }
    }
}

@Composable
fun DonutChart(
    modifier: Modifier = Modifier,
    progress: Float,
    centerText: String,
    label: String
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(
                color = Color(0xFFE7E2D8),
                style = Stroke(width = 40f)
            )
            drawArc(
                color = Color(0xFF0D631B),
                startAngle = -90f,
                sweepAngle = 360 * progress,
                useCenter = false,
                style = Stroke(width = 40f, cap = StrokeCap.Round)
            )
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                label,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                centerText,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun CategoryBreakdown(breakdown: List<StatsContract.CategoryStat>) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text("Category Breakdown", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        if (breakdown.isEmpty()) {
            Text("No expense data available.")
        } else {
            breakdown.forEach { item ->
                CategoryItem(
                    name = item.categoryName,
                    amount = "$${item.amount}",
                    progress = item.percentage,
                    limit = "$${item.limit}",
                    icon = Icons.Default.Category
                )
            }
        }
    }
}

@Composable
fun CategoryItem(name: String, amount: String, progress: Float, limit: String, icon: ImageVector) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color(0xFFE6F6FF),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color(0xFFCFE6F2))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(48.dp),
                color = Color(0xFFDBF1FE),
                shape = CircleShape
            ) {
                Icon(icon, contentDescription = null, modifier = Modifier.padding(12.dp), tint = Color(0xFF0D631B))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(name, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
                    Text(amount, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(8.dp))
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier.fillMaxWidth().height(6.dp).clip(CircleShape),
                    color = Color(0xFFABF4AC),
                    trackColor = Color(0xFFDBF1FE)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("${(progress * 100).toInt()}% of budget", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("Limit: $limit", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}
