package com.example.hesabyar.ui.transaction

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hesabyar.ui.theme.HesabYarTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionDetailScreen(
    onBack: () -> Unit,
    onEdit: () -> Unit = {},
    onDelete: () -> Unit = {}
) {
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
        TransactionDetailContent(
            paddingValues = paddingValues,
            onEdit = onEdit,
            onDelete = onDelete
        )
    }
}

@Composable
fun TransactionDetailContent(
    paddingValues: PaddingValues,
    onEdit: () -> Unit = {},
    onDelete: () -> Unit = {}
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
                    color = Color(0xFFABF4AC),
                    shape = CircleShape
                ) {
                    Icon(
                        Icons.Default.ShoppingBasket,
                        contentDescription = null,
                        modifier = Modifier.padding(20.dp),
                        tint = Color(0xFF0D631B)
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "Grocery Shopping",
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
                        text = "FOOD & DINING",
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0D631B)
                    )
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Text(
                    text = "$120.50",
                    style = MaterialTheme.typography.displayMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0D631B)
                )
                
                Text(
                    text = "Today • 14:32 PM",
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
                        label = "PAID FROM",
                        value = "Main Wallet",
                        icon = Icons.Default.AccountBalanceWallet,
                        modifier = Modifier.weight(1f)
                    )
                    DetailItem(
                        label = "MERCHANT",
                        value = "Whole Foods",
                        icon = Icons.Default.Storefront,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
        
        // Memo Section
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
                    "Weekly grocery shopping for the family. Focus on organic products and fresh fruits.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF071E27)
                )
            }
        }
        
        // Attached Receipt
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                "ATTACHED RECEIPT",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF40493D),
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, Color(0xFFDBF1FE)),
                color = Color(0xFFE6F6FF)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        Icons.Default.Image,
                        contentDescription = null,
                        modifier = Modifier.size(48.dp),
                        tint = Color(0xFF0D631B).copy(alpha = 0.3f)
                    )
                    Text(
                        "Tap to view receipt",
                        modifier = Modifier.padding(top = 64.dp),
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF0D631B).copy(alpha = 0.5f)
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

@Preview(showBackground = true)
@Composable
fun TransactionDetailScreenPreview() {
    HesabYarTheme {
        TransactionDetailScreen(onBack = {})
    }
}
