package com.example.myassigementlokalapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.passwordlessauth.ui.LoginScreen
import com.example.passwordlessauth.ui.OtpScreen
import com.example.passwordlessauth.ui.SessionScreen
import com.example.passwordlessauth.ui.theme.PasswordlessAuthTheme
import com.example.passwordlessauth.viewmodel.AuthState
import com.example.passwordlessauth.viewmodel.AuthViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PasswordlessAuthTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PasswordlessAuthApp()
                }
            }
        }
    }
}

@Preview(showBackground = true)

@Composable
fun PasswordlessAuthApp() {

    val viewModel: AuthViewModel = viewModel()
    val authState by viewModel.authState.collectAsState()

    when (val state = authState) {

        is AuthState.EmailEntry -> {
            LoginScreen(
                onSendOtp = { email ->
                    viewModel.sendOtp(email)
                }
            )
        }

        is AuthState.OtpEntry -> {
            OtpScreen(
                email = state.email,
                errorMessage = state.errorMessage,
                otpData = viewModel.getOtpData(state.email), // ✅ MOST IMPORTANT FIX
                onValidateOtp = { email, otp ->
                    viewModel.validateOtp(email, otp)
                },
                onResendOtp = { email ->
                    viewModel.sendOtp(email)
                },
                onBack = {
                    viewModel.backToEmailEntry()
                }
            )
        }

        is AuthState.Authenticated -> {
            SessionScreen(
                email = state.email,
                sessionStartTime = state.sessionStartTime,
                onLogout = {
                    viewModel.logout()
                }
            )
        }
    }
}
