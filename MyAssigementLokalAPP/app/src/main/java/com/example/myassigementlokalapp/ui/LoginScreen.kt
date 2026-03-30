package com.example.passwordlessauth.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

/**
 * Login screen where user enters their email address.
 *
 * Compose Concepts Demonstrated:
 * - rememberSaveable: Survives process death and configuration changes
 * - State hoisting: email state is hoisted to this composable
 * - Event handling: onSendOtp callback for one-way data flow
 */


@Composable
fun LoginScreen(
    onSendOtp: (String) -> Unit
) {
    // Use rememberSaveable to survive configuration changes (screen rotation)
    var email by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(""))
    }

    // Validation state
    var isEmailValid by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Passwordless Login",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                // Reset validation error when user types
                isEmailValid = true
            },
            label = { Text("Email Address") },
            placeholder = { Text("example@email.com") },
            singleLine = true,
            isError = !isEmailValid,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        if (!isEmailValid) {
            Text(
                text = "Please enter a valid email address",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(bottom = 16.dp)
            )
        }

        Button(
            onClick = {
                val emailText = email.text.trim()
                // Basic email validation
                if (emailText.isNotEmpty() && emailText.contains("@")) {
                    onSendOtp(emailText)
                } else {
                    isEmailValid = false
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Send OTP")
        }

        Text(
            text = "You'll receive a 6-digit code valid for 60 seconds",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}