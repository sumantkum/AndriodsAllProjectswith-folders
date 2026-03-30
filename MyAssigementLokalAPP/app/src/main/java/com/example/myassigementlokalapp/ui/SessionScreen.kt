package com.example.passwordlessauth.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

/**
 * Session screen displayed after successful authentication.
 * Shows session start time and live duration timer.
 *
 * Compose Concepts Demonstrated:
 * - LaunchedEffect: Manages timer lifecycle tied to composition
 * - remember: Maintains timer state across recompositions
 * - Proper cleanup: Timer stops when screen is removed from composition
 */

@Composable
fun SessionScreen(
    email: String,
    sessionStartTime: Long,
    onLogout: () -> Unit
) {
    // Track elapsed time - survives recomposition
    var elapsedTimeMs by remember { mutableStateOf(0L) }

    // Timer effect - updates every second
    // Key: sessionStartTime ensures timer resets if session changes
    LaunchedEffect(sessionStartTime) {
        while (true) {
            elapsedTimeMs = System.currentTimeMillis() - sessionStartTime
            delay(1000) // Update every second
        }
    }

    // Format session start time
    val dateFormat = SimpleDateFormat("MMM dd, yyyy HH:mm:ss", Locale.getDefault())
    val startTimeFormatted = dateFormat.format(Date(sessionStartTime))

    // Calculate duration in mm:ss format
    val totalSeconds = (elapsedTimeMs / 1000).toInt()
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    val durationFormatted = String.format("%02d:%02d", minutes, seconds)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Active Session",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Logged in as:",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                Text(
                    text = email,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                Divider(modifier = Modifier.padding(bottom = 16.dp))

                Text(
                    text = "Session Started:",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                Text(
                    text = startTimeFormatted,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                Text(
                    text = "Session Duration:",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                Text(
                    text = durationFormatted,
                    style = MaterialTheme.typography.displaySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        Button(
            onClick = onLogout,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error
            )
        ) {
            Text("Logout")
        }

        Text(
            text = "Your session is active and being tracked",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}