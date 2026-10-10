package com.example.mobile.ui.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SyncActions(
    sendStatus: String,
    onSendToWatch: () -> Unit,
    onSaveToFirebase: () -> Unit,
    modifier: Modifier = Modifier,
    showStatus: Boolean = true
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = onSendToWatch,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Send to Watch")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = onSaveToFirebase,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save to Firebase")
        }

        if (showStatus) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Status: $sendStatus",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
