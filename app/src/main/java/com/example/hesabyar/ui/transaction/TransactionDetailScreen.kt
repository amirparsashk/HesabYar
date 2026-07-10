package com.example.hesabyar.ui.transaction

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionDetailScreen(
    transactionId: Long,
    viewModel: TransactionDetailViewModel,
    onBack: () -> Unit,
    onEdit: (Long) -> Unit = {}
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(transactionId) {
        viewModel.handleIntent(TransactionDetailContract.Intent.LoadTransaction(transactionId))
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is TransactionDetailContract.Effect.NavigateBack -> onBack()
                is TransactionDetailContract.Effect.ShowError -> {
                    // Show error
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Transaction Details",
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
                actions = {
                    IconButton(onClick = { /* Share action */ }) {
                        Icon(
                            Icons.Default.Share,
                            contentDescription = "Share",
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
        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (state.transaction != null) {
            TransactionDetailContent(
                state = state,
                paddingValues = paddingValues,
                onEdit = { onEdit(transactionId) },
                onDelete = { viewModel.handleIntent(TransactionDetailContract.Intent.DeleteTransaction) }
            )
        }
    }
}

@Composable
fun TransactionDetailContent(
    state: TransactionDetailContract.State,
    paddingValues: PaddingValues,
    onEdit: () -> Unit = {},
    onDelete: () -> Unit = {}
) {
    val transaction = state.transaction!!
    val isExpense = transaction.type == "Expense"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Main Transaction Card
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = Color(0xFFE6F6FF),
            shape = RoundedCornerShape(24.dp),
            border = BorderStroke(1.dp, Color(0xFFDBF1FE)),
            shadowElevation = 0.dp
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Category Icon
                Surface(
                    modifier = Modifier.size(80.dp),
                    color = if (isExpense) Color(0xFFABF4AC) else Color(0xFFCCEACD),
                    shape = CircleShape
                ) {
                    Icon(
                        if (isExpense) Icons.Default.ShoppingBasket else Icons.Default.Payments,
                        contentDescription = null,
                        modifier = Modifier.padding(20.dp),
                        tint = Color(0xFF0D631B)
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = transaction.title,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF071E27)
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Surface(
                    color = Color(0xFF0D631B).copy(alpha = 0.1f),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, Color(0xFF0D631B).copy(alpha = 0.2f))
                ) {
                    Text(
                        text = state.category?.name ?: "UNCATEGORIZED",
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0D631B)
                    )
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Text(
                    text = (if (isExpense) "-" else "+") + "$${transaction.amount}",
                    style = MaterialTheme.typography.displayMedium,
                    fontWeight = FontWeight.Bold,
                    color = if (isExpense) Color(0xFFBA1A1A) else Color(0xFF0D631B)
                )
                
                Text(
                    text = Date(transaction.date).toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF40493D)
                )
                
                Spacer(modifier = Modifier.height(32.dp))
                HorizontalDivider(color = Color(0xFFDBF1FE))
                Spacer(modifier = Modifier.height(24.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    DetailItem(
                        label = "TYPE",
                        value = transaction.type,
                        icon = Icons.Default.Info,
                        modifier = Modifier.weight(1f)
                    )
                    DetailItem(
                        label = "ID",
                        value = "#${transaction.id}",
                        icon = Icons.Default.Fingerprint,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
        
        // Memo Section
        if (!transaction.note.isNullOrBlank()) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color(0xFFDBF1FE).copy(alpha = 0.5f),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, Color(0xFFDBF1FE))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "MEMO",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF40493D),
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        transaction.note,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF071E27)
                    )
                }
            }
        }
        
        // Action Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = onEdit,
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF0D631B),
                    contentColor = Color.White
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
            ) {
                Icon(Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("EDIT", fontWeight = FontWeight.Bold)
            }
            
            OutlinedButton(
                onClick = onDelete,
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color(0xFFBA1A1A)
                ),
                border = BorderStroke(1.dp, Color(0xFFBA1A1A))
            ) {
                Icon(Icons.Default.Delete, contentDescription = null, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("DELETE", fontWeight = FontWeight.Bold)
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun DetailItem(
    label: String,
    value: String,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Surface(
            modifier = Modifier.size(40.dp),
            color = Color(0xFFDBF1FE),
            shape = CircleShape
        ) {
            Icon(
                icon,
                contentDescription = null,
                modifier = Modifier.padding(10.dp),
                tint = Color(0xFF0D631B)
            )
        }
        Column {
            Text(
                label,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF40493D)
            )
            Text(
                value,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF071E27)
            )
        }
    }
}
