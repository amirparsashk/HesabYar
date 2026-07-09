package com.example.hesabyar.ui.stats

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hesabyar.ui.components.MainScaffold
import com.example.hesabyar.ui.theme.HesabYarTheme

@Composable
fun StatsScreen(
    onDashboardClick: () -> Unit = {},
    onHistoryClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    onAddTransaction: () -> Unit = {}
) {
    MainScaffold(
        currentScreen = "stats",
        onDashboardClick = onDashboardClick,
        onHistoryClick = onHistoryClick,
        onStatsClick = { /* Already here */ },
        onProfileClick = onProfileClick,
        onAddTransactionClick = onAddTransaction
    ) { paddingValues ->
        StatsContent(paddingValues = paddingValues)
    }
}

@Composable
fun StatsContent(paddingValues: PaddingValues) {
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
                "Insights for May",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0D631B)
            )
            Text(
                "You've managed your budget exceptionally well this month. Your savings rate is up by 12% compared to April.",
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
                amount = "$4,820",
                amountColor = Color(0xFF2E7D32),
                modifier = Modifier.weight(1f)
            )
            SummaryCard(
                title = "SPENT",
                amount = "$1,450",
                amountColor = Color(0xFFBA1A1A),
                modifier = Modifier.weight(1f)
            )
        }

        // Donut Chart placeholder
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentAlignment = Alignment.Center
        ) {
            DonutChart(
                modifier = Modifier.size(160.dp),
                progress = 0.6f,
                centerText = "$1,450.20",
                label = "TOTAL SPENT"
            )
        }

        // Bento Grid: Monthly Comparison
        MonthlyComparisonCard()

        // Bento Grid: Savings Goal
        SavingsGoalCard(
            title = "Bali Trip",
            current = 2400f,
            target = 3000f
        )

        // Category Breakdown
        CategoryBreakdown()
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
            drawArc(
                color = Color(0xFFFFB300),
                startAngle = -90f + (360 * progress),
                sweepAngle = 40f,
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
fun MonthlyComparisonCard() {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color(0xFFE6F6FF),
        shape = RoundedCornerShape(24.dp),
        border = BorderStroke(1.dp, Color(0xFFCFE6F2))
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Monthly Comparison", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(12.dp).clip(CircleShape).background(Color(0xFF0D631B)))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Income", style = MaterialTheme.typography.labelSmall)
                    Spacer(modifier = Modifier.width(12.dp))
                    Box(modifier = Modifier.size(12.dp).clip(CircleShape).background(Color(0xFFABF4AC)))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Expense", style = MaterialTheme.typography.labelSmall)
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier.fillMaxWidth().height(150.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.Bottom
            ) {
                BarGroup("MAR", 0.6f, 0.4f)
                BarGroup("APR", 0.8f, 0.5f)
                BarGroup("MAY", 1.0f, 0.3f, isHighlighted = true)
                BarGroup("JUN", 0.7f, 0.2f, isAlpha = true)
            }
        }
    }
}

@Composable
fun BarGroup(label: String, incomeHeight: Float, expenseHeight: Float, isHighlighted: Boolean = false, isAlpha: Boolean = false) {
    val alpha = if (isAlpha) 0.3f else 1f
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            Box(
                modifier = Modifier
                    .width(12.dp)
                    .fillMaxHeight(incomeHeight)
                    .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                    .background(Color(0xFF0D631B).copy(alpha = alpha))
            )
            Box(
                modifier = Modifier
                    .width(12.dp)
                    .fillMaxHeight(expenseHeight)
                    .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                    .background(Color(0xFFABF4AC).copy(alpha = alpha))
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            label,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = if (isHighlighted) FontWeight.Bold else FontWeight.Normal,
            color = if (isHighlighted) Color(0xFF0D631B) else MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun SavingsGoalCard(title: String, current: Float, target: Float) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color(0xFF0D631B),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text(title, style = MaterialTheme.typography.headlineSmall, color = Color.White, fontWeight = FontWeight.Bold)
            Text("SAVINGS GOAL", style = MaterialTheme.typography.labelSmall, color = Color.White.copy(alpha = 0.7f))
            Spacer(modifier = Modifier.height(16.dp))
            Row(verticalAlignment = Alignment.Bottom) {
                Text("$${current.toInt()}", style = MaterialTheme.typography.headlineMedium, color = Color.White, fontWeight = FontWeight.Bold)
                Text(" / $${target.toInt()}", style = MaterialTheme.typography.bodyLarge, color = Color.White.copy(alpha = 0.8f))
            }
            Spacer(modifier = Modifier.height(16.dp))
            val progress = current / target
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier.fillMaxWidth().height(8.dp).clip(CircleShape),
                color = Color(0xFFABF4AC),
                trackColor = Color.White.copy(alpha = 0.2f),
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text("${(progress * 100).toInt()}% reached", style = MaterialTheme.typography.bodyMedium, color = Color.White, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun CategoryBreakdown() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text("Category Breakdown", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        CategoryItem("Food & Drinks", "$450.00", 0.84f, "$500", Icons.Default.Restaurant)
        CategoryItem("Shopping", "$280.45", 0.41f, "$700", Icons.Default.ShoppingBag)
        CategoryItem("Transport", "$120.00", 0.60f, "$200", Icons.Default.DirectionsCar)
        CategoryItem("Housing", "$950.00", 0.95f, "$1000", Icons.Default.Home)
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

@Preview(showBackground = true)
@Composable
fun StatsScreenPreview() {
    HesabYarTheme {
        StatsScreen()
    }
}
