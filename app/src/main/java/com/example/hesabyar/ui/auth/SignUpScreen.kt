package com.example.hesabyar.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hesabyar.ui.theme.HesabYarTheme

@Composable
fun SignUpScreen(onLoginClick: () -> Unit, onSignUpSuccess: () -> Unit) {
    // Using MaterialTheme color scheme for consistency
    val primaryColor = MaterialTheme.colorScheme.primary
    val backgroundColor = MaterialTheme.colorScheme.background
    val inputFieldColor = Color.White
    val primaryContainerColor = MaterialTheme.colorScheme.primaryContainer
    val onPrimaryContainerColor = MaterialTheme.colorScheme.onPrimaryContainer
    val onSurfaceVariantColor = MaterialTheme.colorScheme.onSurfaceVariant

    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize().background(backgroundColor)) {
        // Floating Background Element (Subtle Decoration)
        Box(
            modifier = Modifier
                .size(384.dp)
                .offset(x = 100.dp, y = (-100).dp)
                .align(Alignment.TopEnd)
                .background(primaryContainerColor.copy(alpha = 0.2f), CircleShape)
                .blur(72.dp)
        )

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.Transparent
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp, vertical = 48.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Header
                Text(
                    text = "HesabYar",
                    style = MaterialTheme.typography.displayMedium,
                    color = primaryColor,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 20.dp)
                )

                // Illustration Placeholder (Coin + Character)
                Box(
                    modifier = Modifier
                        .size(240.dp)
                        .padding(bottom = 32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    // Background shape
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(primaryContainerColor.copy(alpha = 0.2f), CircleShape)
                    )
                    // Placeholder for the illustration
                    Icon(
                        imageVector = Icons.Default.AccountBalanceWallet,
                        contentDescription = null,
                        modifier = Modifier.size(120.dp),
                        tint = primaryColor
                    )
                }

                // Title and Subtitle
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Join HesabYar",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "Start your journey toward stress-free finance.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = onSurfaceVariantColor,
                        modifier = Modifier.padding(top = 4.dp, bottom = 32.dp)
                    )
                }

                // Form
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    // Full Name
                    SignUpInputField(
                        label = "FULL NAME",
                        value = fullName,
                        onValueChange = { fullName = it },
                        placeholder = "Sam Wilson",
                        backgroundColor = inputFieldColor
                    )

                    // Email
                    SignUpInputField(
                        label = "EMAIL ADDRESS",
                        value = email,
                        onValueChange = { email = it },
                        placeholder = "sam@example.com",
                        keyboardType = KeyboardType.Email,
                        backgroundColor = inputFieldColor
                    )

                    // Password Fields
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        SignUpInputField(
                            label = "PASSWORD",
                            value = password,
                            onValueChange = { password = it },
                            placeholder = "••••••••",
                            modifier = Modifier.weight(1f),
                            visualTransformation = PasswordVisualTransformation(),
                            keyboardType = KeyboardType.Password,
                            backgroundColor = inputFieldColor
                        )
                        SignUpInputField(
                            label = "CONFIRM",
                            value = confirmPassword,
                            onValueChange = { confirmPassword = it },
                            placeholder = "••••••••",
                            modifier = Modifier.weight(1f),
                            visualTransformation = PasswordVisualTransformation(),
                            keyboardType = KeyboardType.Password,
                            backgroundColor = inputFieldColor
                        )
                    }

                    // Create Account Button
                    Button(
                        onClick = { onSignUpSuccess() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp)
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = primaryContainerColor,
                            contentColor = onPrimaryContainerColor
                        ),
                        shape = RoundedCornerShape(28.dp)
                    ) {
                        Text(
                            text = "CREATE ACCOUNT",
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.25.sp
                        )
                    }
                }

                // Footer
                Row(
                    modifier = Modifier
                        .padding(top = 32.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Already have an account? ",
                        style = MaterialTheme.typography.bodyMedium,
                        color = onSurfaceVariantColor
                    )
                    TextButton(onClick = onLoginClick) {
                        Text(
                            text = "Login",
                            style = MaterialTheme.typography.bodyMedium,
                            color = primaryColor,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpInputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: androidx.compose.ui.text.input.VisualTransformation = androidx.compose.ui.text.input.VisualTransformation.None,
    backgroundColor: Color
) {
    Column(modifier = modifier) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.6.sp,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder, color = Color.Gray) },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = backgroundColor,
                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.outlineVariant,
                cursorColor = MaterialTheme.colorScheme.primary
            ),
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            visualTransformation = visualTransformation,
            singleLine = true
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    HesabYarTheme {
        SignUpScreen(onLoginClick = {}, onSignUpSuccess = {})
    }
}
