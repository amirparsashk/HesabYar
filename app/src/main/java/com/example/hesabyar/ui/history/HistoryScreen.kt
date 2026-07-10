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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hesabyar.ui.components.MainScaffold
import java.util.Date

@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel,
    onDashboardClick: () -> Unit = {},
    onStatsClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    onAddTransaction: () -> Unit = {},
    onTransactionClick: (Long) -> Unit = {}
) {
    val state by viewModel.state.collectAsState()

    MainScaffold(
        currentScreen = "history",
        onDashboardClick = onDashboardClick,
        onHistoryClick = { /* Already here */ },
        onStatsClick = onStatsClick,
        onProfileClick = onProfileClick,
        onAddTransactionClick = onAddTransaction
    ) { paddingValues ->
        HistoryContent(
            state = state,
            onSearchQueryChange = { viewModel.handleIntent(HistoryContract.Intent.SearchChanged(it)) },
            onCategorySelect = { viewModel.handleIntent(HistoryContract.Intent.FilterSelected(it)) },
            paddingValues = paddingValues,
            onTransactionClick = onTransactionClick
        )
    }
}

@Composable
fun HistoryContent(
    state: HistoryContract.State,
    onSearchQueryChange: (String) -> Unit,
    onCategorySelect: (Long?) -> Unit,
    paddingValues: PaddingValues,
    onTransactionClick: (Long) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp)
    ) {
        // Search Bar
        OutlinedTextField(
            value = state.searchQuery,
            onValueChange = onSearchQueryChange,
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
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(bottom = 4.dp)
        ) {
            item {
                FilterChip(
                    selected = state.selectedCategoryId == null,
                    onClick = { onCategorySelect(null) },
                    label = { Text("All") },
                    shape = RoundedCornerShape(24.dp)
                )
            }
            items(state.categories) { category ->
                FilterChip(
                    selected = state.selectedCategoryId == category.id,
                    onClick = { onCategorySelect(category.id) },
                    label = { Text(category.name) },
                    shape = RoundedCornerShape(24.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (state.transactions.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No transactions found.")
            }
        } else {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
            ) {
                state.transactions.forEach { transaction ->
                    TransactionHistoryListItem(
                        title = transaction.title,
                        subtitle = Date(transaction.date).toString(),
                        amount = (if (transaction.type == "Expense") "-" else "+") + "$${transaction.amount}",
                        isExpense = transaction.type == "Expense",
                        onClick = { onTransactionClick(transaction.id) }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun TransactionHistoryListItem(
    title: String,
    subtitle: String,
    amount: String,
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
                color = if (isExpense) Color(0xFFABF4AC) else Color(0xFFCCEACD),
                shape = CircleShape
            ) {
                Icon(
                    if (isExpense) Icons.Default.ShoppingCart else Icons.Default.Payments,
                    contentDescription = null,
                    modifier = Modifier.padding(12.dp),
                    tint = Color(0xFF071E27)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF071E27)
                )
                Text(
                    subtitle,
                    style = MaterialTheme.typography.labelMedium,
                    color = Color(0xFF40493D)
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    amount,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = if (isExpense) Color(0xFFBA1A1A) else Color(0xFF286B33)
                )
                IconButton(onClick = { /* TODO: Open options */ }, modifier = Modifier.size(24.dp)) {
                    Icon(Icons.Default.MoreVert, contentDescription = "More", tint = Color(0xFF707A6C))
                }
            }
        }
    }
}
