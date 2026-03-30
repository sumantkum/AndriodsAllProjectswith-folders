package com.example.passwordlessauth.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.passwordlessauth.data.OtpData
import com.example.passwordlessauth.viewmodel.AuthViewModel
import kotlinx.coroutines.delay

/**
 * OTP entry screen with countdown timer and attempt tracking.
 *
 * Compose Concepts Demonstrated:
 * - LaunchedEffect: For countdown timer that survives recomposition
 * - remember: For timer state
 * - State hoisting: OTP input is managed locally, events flow up
 * - Recomposition handling: Timer continues correctly during recompositions
 */

@Composable
fun OtpScreen(
    email: String,
    errorMessage: String?,
    otpData: OtpData?,
    onValidateOtp: (String, String) -> Unit,
    onResendOtp: (String) -> Unit,
    onBack: () -> Unit
) {
    var otpInput by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(""))
    }

    var remainingTimeMs by remember { mutableStateOf(60_000L) }
    var isExpired by remember { mutableStateOf(false) }

    // Countdown timer
    LaunchedEffect(email, otpData?.generatedAt) {
        while (true) {
            if (otpData != null) {
                remainingTimeMs = otpData.getRemainingTimeMs()
                isExpired = otpData.isExpired()
                if (remainingTimeMs <= 0) break
            }
            delay(100)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text("Enter OTP", style = MaterialTheme.typography.headlineMedium)
        Text("Sent to $email", color = MaterialTheme.colorScheme.onSurfaceVariant)

        Spacer(modifier = Modifier.height(16.dp))

        if (!isExpired) {
            LinearProgressIndicator(
                progress = remainingTimeMs / 60_000f,
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            Text("OTP Expired", color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(16.dp))

        otpData?.let {
            Text("Attempts remaining: ${it.attemptsRemaining}")
        }

        OutlinedTextField(
            value = otpInput,
            onValueChange = {
                if (it.text.length <= 6 && it.text.all(Char::isDigit)) {
                    otpInput = it
                }
            },
            label = { Text("6-Digit OTP") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = errorMessage != null,
            modifier = Modifier.fillMaxWidth()
        )

        if (errorMessage != null) {
            Text(errorMessage, color = MaterialTheme.colorScheme.error)
        }

        Button(
            onClick = { onValidateOtp(email, otpInput.text) },
            enabled = otpInput.text.length == 6 && !isExpired,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Verify OTP")
        }

        TextButton(onClick = { onResendOtp(email) }) {
            Text("Resend OTP")
        }

        TextButton(onClick = onBack) {
            Text("Back")
        }
    }
}
