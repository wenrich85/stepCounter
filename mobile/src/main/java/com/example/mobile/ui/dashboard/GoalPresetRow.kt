package com.example.mobile.ui.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun GoalPresetRow(modifier: Modifier = Modifier){
    Column(modifier = modifier) {
        Text(
            text = "Drag a preset onto the goal (long-press, then move)",
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)){
            GoalPresetChip(value = 8000)
            GoalPresetChip(value = 10000)
            GoalPresetChip(value = 12000)

        }
    }
}