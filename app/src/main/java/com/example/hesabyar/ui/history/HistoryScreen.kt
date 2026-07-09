package com.example.hesabyar.ui.history

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import com.example.hesabyar.ui.components.MainScaffold
import com.example.hesabyar.ui.theme.HesabYarTheme

@Composable
fun HistoryScreen(
    onDashboardClick: () -> Unit = {},
    onStatsClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    onAddTransaction: () -> Unit = {},
    onTransactionClick: () -> Unit = {}
) {
    MainScaffold(
        currentScreen = "history",
        onDashboardClick = onDashboardClick,
        onHistoryClick = { /* Already here */ },
        onStatsClick = onStatsClick,
        onProfileClick = onProfileClick,
        onAddTransactionClick = onAddTransaction
    ) { paddingValues ->
        HistoryContent(
            paddingValues = paddingValues,
            onTransactionClick = onTransactionClick
        )
    }
}

@Composable
fun HistoryContent(
    paddingValues: PaddingValues,
    onTransactionClick: () -> Unit = {}
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf("All") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            placeholder = { Text("Search transactions...", color = Color(0xFF707A6C)) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Color(0xFF707A6C)) },
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color(0xFFDBF1FE),
                focusedContainerColor = Color(0xFFDBF1FE),
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = Color(0xFF0D631B),
            ),
            singleLine = true
        )

        // Filter Chips
        Spacer(modifier = Modifier.height(16.dp))
        val filters = listOf("All", "Food", "Shopping", "Transport", "Bills", "Entertainment")
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(bottom = 4.dp)
        ) {
            items(filters) { filter ->
                val isSelected = selectedFilter == filter
                FilterChip(
                    selected = isSelected,
                    onClick = { selectedFilter = filter },
                    label = { Text(filter) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = Color(0xFFABF4AC),
                        selectedLabelColor = Color(0xFF07521D),
                        containerColor = Color(0xFFD5ECF8),
                        labelColor = Color(0xFF40493D)
                    ),
                    border = null,
                    shape = RoundedCornerShape(24.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Transactions List
        TransactionHistoryGroup(
            date = "Today, Oct 24",
            items = listOf(
                TransactionHistoryData("Whole Foods Market", "Groceries • 12:45 PM", "-$84.20", Icons.Default.Restaurant, isExpense = true, iconBg = Color(0xFFABF4AC)),
                TransactionHistoryData("Uber Trip", "Transport • 09:15 AM", "-$12.50", Icons.Default.DirectionsCar, isExpense = true, iconBg = Color(0xFFABF4AC))
            ),
            onTransactionClick = onTransactionClick
        )

        Spacer(modifier = Modifier.height(24.dp))

        TransactionHistoryGroup(
            date = "Yesterday, Oct 23",
            items = listOf(
                TransactionHistoryData("Monthly Salary", "Income • 10:00 AM", "+$3,450.00", Icons.Default.Payments, isExpense = false, iconBg = Color(0xFFABF4AC)),
                TransactionHistoryData("Apple Store", "Gadgets • 04:30 PM", "-$199.00", Icons.Default.ShoppingBag, isExpense = true, iconBg = Color(0xFFABF4AC))
            ),
            onTransactionClick = onTransactionClick
        )

        Spacer(modifier = Modifier.height(24.dp))

        TransactionHistoryGroup(
            date = "Oct 22",
            items = listOf(
                TransactionHistoryData("Rent Payment", "Bills • 08:00 AM", "-$1,200.00", Icons.Default.Home, isExpense = true, iconBg = Color(0xFFABF4AC))
            ),
            onTransactionClick = onTransactionClick
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

data class TransactionHistoryData(
    val title: String,
    val subtitle: String,
    val amount: String,
    val icon: ImageVector,
    val isExpense: Boolean,
    val iconBg: Color
)

@Composable
fun TransactionHistoryGroup(
    date: String,
    items: List<TransactionHistoryData>,
    onTransactionClick: () -> Unit = {}
) {
    Column {
        Text(
            text = date.uppercase(),
            style = MaterialTheme.typography.labelLarge,
            color = Color(0xFF707A6C),
            modifier = Modifier.padding(start = 8.dp, bottom = 8.dp),
            letterSpacing = 1.sp
        )
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items.forEach { item ->
                TransactionHistoryListItem(item, onClick = onTransactionClick)
            }
        }
    }
}

@Composable
fun TransactionHistoryListItem(
    item: TransactionHistoryData,
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
                color = item.iconBg,
                shape = CircleShape
            ) {
                Icon(
                    item.icon,
                    contentDescription = null,
                    modifier = Modifier.padding(12.dp),
                    tint = Color(0xFF071E27)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    item.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF071E27)
                )
                Text(
                    item.subtitle,
                    style = MaterialTheme.typography.labelMedium,
                    color = Color(0xFF40493D)
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    item.amount,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = if (item.isExpense) Color(0xFFBA1A1A) else Color(0xFF286B33)
                )
                IconButton(onClick = { /* TODO: Open options */ }, modifier = Modifier.size(24.dp)) {
                    Icon(Icons.Default.MoreVert, contentDescription = "More", tint = Color(0xFF707A6C))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HistoryScreenPreview() {
    HesabYarTheme {
        HistoryScreen()
    }
}
