package com.example.mobile.ui.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.stepcounter.shared.model.FitnessData

@Composable
fun DashboardInfoPane(
    sendStatus: String,
    cloudData: FitnessData?,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Cloud document",
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = "fitnessData / demo-user",
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(16.dp))

            if (cloudData == null) {
                Text("No document yet. Tap Save to Firebase on a phone or tablet.")
            } else {
                Text("dailyGoal: ${cloudData.dailyGoal}")
                Spacer(modifier = Modifier.height(8.dp))

                Text("steps: ${cloudData.steps}")
                Spacer(modifier = Modifier.height(8.dp))

                Text("heartRate: ${cloudData.heartRate}")
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "steps and heartRate stay at defaults until the watch writes them.",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Pipes",
                style = MaterialTheme.typography.titleMedium
            )
            Text("Send to Watch = Data Layer / fitness-goals")
            Text("Save to Firebase = Firestore merge on dailyGoal")

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Status",
                style = MaterialTheme.typography.titleMedium
            )
            Text(text = sendStatus)
        }
    }
}
