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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionScreen(
    viewModel: AddTransactionViewModel,
    onBack: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is AddTransactionContract.Effect.NavigateBack -> onBack()
                is AddTransactionContract.Effect.ShowError -> {
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
                        "New Entry",
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
        AddTransactionContent(
            state = state,
            onIntent = { viewModel.handleIntent(it) },
            paddingValues = paddingValues
        )
    }
}

@Composable
fun AddTransactionContent(
    state: AddTransactionContract.State,
    onIntent: (AddTransactionContract.Intent) -> Unit,
    paddingValues: PaddingValues
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Illustration Placeholder
        Box(
            modifier = Modifier
                .size(160.dp)
                .background(Color(0xFF0D631B).copy(alpha = 0.05f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.AccountBalanceWallet,
                contentDescription = null,
                modifier = Modifier.size(70.dp),
                tint = Color(0xFF0D631B)
            )
        }

        // Transaction Form
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = Color(0xFFE6F6FF),
            shape = RoundedCornerShape(24.dp),
            border = BorderStroke(1.dp, Color(0xFFDBF1FE)),
            shadowElevation = 0.dp
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // Type Toggle
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .clip(RoundedCornerShape(26.dp))
                        .background(Color(0xFFDBF1FE))
                        .padding(4.dp)
                ) {
                    val expenseSelected = state.type == "Expense"
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(22.dp))
                            .background(if (expenseSelected) Color(0xFFBA1A1A) else Color.Transparent)
                            .clickable { onIntent(AddTransactionContract.Intent.TypeChanged("Expense")) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "EXPENSE",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            color = if (expenseSelected) Color.White else Color(0xFF40493D)
                        )
                    }
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(22.dp))
                            .background(if (!expenseSelected) Color(0xFF0D631B) else Color.Transparent)
                            .clickable { onIntent(AddTransactionContract.Intent.TypeChanged("Income")) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "INCOME",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            color = if (!expenseSelected) Color.White else Color(0xFF40493D)
                        )
                    }
                }

                // Description Input
                InputWrapper(label = "DESCRIPTION") {
                    OutlinedTextField(
                        value = state.title,
                        onValueChange = { onIntent(AddTransactionContract.Intent.TitleChanged(it)) },
                        placeholder = {
                            Text(
                                "e.g. Starbucks, Rent, Salary",
                                color = Color(0xFF40493D).copy(alpha = 0.5f)
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = textFieldColors()
                    )
                }

                // Amount Input
                InputWrapper(label = "AMOUNT") {
                    OutlinedTextField(
                        value = state.amount,
                        onValueChange = { onIntent(AddTransactionContract.Intent.AmountChanged(it)) },
                        placeholder = {
                            Text(
                                "0.00",
                                color = Color(0xFF40493D).copy(alpha = 0.3f)
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        prefix = {
                            Text(
                                "$",
                                style = MaterialTheme.typography.headlineMedium,
                                color = Color(0xFF0D631B),
                                fontWeight = FontWeight.Bold
                            )
                        },
                        textStyle = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold, color = Color(0xFF0D631B)),
                        shape = RoundedCornerShape(16.dp),
                        colors = textFieldColors()
                    )
                }

                // Category and Date Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    InputWrapper(label = "CATEGORY", modifier = Modifier.weight(1f)) {
                        var expanded by remember { mutableStateOf(false) }
                        Box {
                            OutlinedTextField(
                                value = state.categories.find { it.id == state.categoryId }?.name ?: "",
                                onValueChange = { },
                                readOnly = true,
                                modifier = Modifier.fillMaxWidth().clickable { expanded = true },
                                enabled = false,
                                shape = RoundedCornerShape(16.dp),
                                trailingIcon = {
                                    Icon(
                                        Icons.Default.KeyboardArrowDown,
                                        contentDescription = null,
                                        tint = Color(0xFF0D631B)
                                    )
                                },
                                colors = textFieldColors()
                            )
                            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                                state.categories.forEach { category ->
                                    DropdownMenuItem(
                                        text = { Text(category.name) },
                                        onClick = {
                                            onIntent(AddTransactionContract.Intent.CategorySelected(category.id))
                                            expanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                    InputWrapper(label = "DATE", modifier = Modifier.weight(1f)) {
                        OutlinedTextField(
                            value = Date(state.date).toString(), // Should use proper formatter
                            onValueChange = { },
                            readOnly = true,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            trailingIcon = {
                                Icon(
                                    Icons.Default.CalendarToday,
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp),
                                    tint = Color(0xFF0D631B)
                                )
                            },
                            colors = textFieldColors()
                        )
                    }
                }

                // Notes Input
                InputWrapper(label = "NOTES (OPTIONAL)") {
                    OutlinedTextField(
                        value = state.note,
                        onValueChange = { onIntent(AddTransactionContract.Intent.NoteChanged(it)) },
                        placeholder = {
                            Text(
                                "Add some details...",
                                color = Color(0xFF40493D).copy(alpha = 0.5f)
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = textFieldColors()
                    )
                }

                // Save Button
                Button(
                    onClick = { onIntent(AddTransactionContract.Intent.SaveTransaction) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    enabled = !state.isLoading,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF0D631B),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(16.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
                ) {
                    if (state.isLoading) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                    } else {
                        Text("SAVE TRANSACTION", fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                    }
                }
            }
        }
    }
}

@Composable
private fun InputWrapper(
    label: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = Color(0xFF40493D),
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.5.sp
        )
        content()
    }
}

@Composable
private fun textFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedContainerColor = Color.White,
    unfocusedContainerColor = Color.White,
    focusedBorderColor = Color(0xFF0D631B),
    unfocusedBorderColor = Color(0xFFDBF1FE),
    focusedLabelColor = Color(0xFF0D631B),
    unfocusedLabelColor = Color(0xFF40493D),
    disabledBorderColor = Color(0xFFDBF1FE),
    disabledTextColor = Color.Black
)
