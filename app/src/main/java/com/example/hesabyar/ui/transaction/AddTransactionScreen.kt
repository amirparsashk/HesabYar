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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hesabyar.ui.theme.HesabYarTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionScreen(
    onBack: () -> Unit
) {
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
        AddTransactionContent(paddingValues = paddingValues, onBack = onBack)
    }
}

@Composable
fun AddTransactionContent(
    paddingValues: PaddingValues,
    onBack: () -> Unit
) {
    var transactionType by remember { mutableStateOf("EXPENSE") }
    var description by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Shopping") }
    var date by remember { mutableStateOf("2024-05-26") }
    var notes by remember { mutableStateOf("") }

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
                    val expenseSelected = transactionType == "EXPENSE"
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(22.dp))
                            .background(if (expenseSelected) Color(0xFFBA1A1A) else Color.Transparent)
                            .clickable { transactionType = "EXPENSE" },
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
                            .clickable { transactionType = "INCOME" },
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
                        value = description,
                        onValueChange = { description = it },
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
                        value = amount,
                        onValueChange = { amount = it },
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
                        OutlinedTextField(
                            value = category,
                            onValueChange = { },
                            readOnly = true,
                            modifier = Modifier.fillMaxWidth(),
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
                    }
                    InputWrapper(label = "DATE", modifier = Modifier.weight(1f)) {
                        OutlinedTextField(
                            value = date,
                            onValueChange = { date = it },
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
                        value = notes,
                        onValueChange = { notes = it },
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
                    onClick = { onBack() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF0D631B),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(16.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
                ) {
                    Text("SAVE TRANSACTION", fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                }
            }
        }

        // Quick Summary Cards
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SummaryCard(
                title = "MONTHLY AVG",
                amount = "$1,240",
                icon = Icons.Default.Insights,
                iconColor = Color(0xFF0D631B),
                containerColor = Color(0xFFDBF1FE),
                modifier = Modifier.weight(1f)
            )
            SummaryCard(
                title = "PROJECTED",
                amount = "$4,120",
                icon = Icons.Default.Savings,
                iconColor = Color(0xFF2E7238),
                containerColor = Color(0xFFABF4AC).copy(alpha = 0.3f),
                modifier = Modifier.weight(1f)
            )
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
    unfocusedLabelColor = Color(0xFF40493D)
)

@Composable
fun SummaryCard(
    title: String,
    amount: String,
    icon: ImageVector,
    iconColor: Color,
    containerColor: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.aspectRatio(1.2f),
        color = containerColor,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color(0xFFDBF1FE).copy(alpha = 0.5f))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(24.dp))
            Column {
                Text(
                    title,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color(0xFF40493D),
                    fontWeight = FontWeight.Bold
                )
                Text(
                    amount,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF071E27)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddTransactionScreenPreview() {
    HesabYarTheme {
        AddTransactionScreen(onBack = {})
    }
}
