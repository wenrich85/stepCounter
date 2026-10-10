package com.example.mobile.ui.dashboard

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.stepcounter.sendStepsGoalToWatch
import com.example.stepcounter.shared.data.FirebaseRepository
import com.example.stepcounter.shared.model.FitnessData

@Composable
fun PhoneCompanionApp(
    repository: FirebaseRepository,
    widthSizeClass: WindowWidthSizeClass
){
    val context = LocalContext.current
    var stepsGoal by remember { mutableIntStateOf(10000) }
    var sendStatus by remember { mutableStateOf("Not Sent") }
    var cloudData by remember { mutableStateOf<FitnessData?>(null) }

    DisposableEffect(repository) {
        val listenerRegistration = repository.listenToFitnessData(
            onDataChanged = { fitnessData ->
                cloudData = fitnessData
                stepsGoal = fitnessData.dailyGoal.toInt()
                sendStatus = "Goal received from Firebase: $stepsGoal"
            },
            onError = { exception ->
                sendStatus = "Firebase listener error: " +
                        (exception.message ?: "Unknown error")
            }

        )
        onDispose {  listenerRegistration.remove() }
    }

    val onDecrease = {
        if (stepsGoal > 500) stepsGoal -= 500
    }

    val onIncrease = {
        stepsGoal += 500
    }

    val onGoalDropped: (Int) -> Unit = { dropped ->
        stepsGoal = dropped.coerceAtLeast(500)
        sendStatus = "Dropped preset $dropped (not saved yet"
    }

    val onSendToWatch = {
        sendStatus = "Sending..."
        sendStepsGoalToWatch(
            context = context,
            stepsGoal = stepsGoal,
            onSuccess = { sendStatus = "Sent $stepsGoal to the watch."},
            onError = { errorMessage -> sendStatus = "Error: $errorMessage"}
        )
    }

    val onSaveToFirebase = {
        sendStatus = "Saving to Firebase"
        repository.updateDailyGoal(
            dailyGoal = stepsGoal.toLong(),
            onSuccess = { sendStatus = "Saved $stepsGoal in Firebase"},
            onError = { exception ->
                sendStatus = "Firebase error: " +
                        (exception.message ?:"UnKnown error")
            }
        )
    }

    val layoutLabel = widthSizeClass.label()

    if (widthSizeClass.isWide()){
       WideDashboard(
            stepsGoal= stepsGoal,
            sendStatus= sendStatus,
            layoutLabel= layoutLabel,
            cloudData= cloudData,
            onDecrease= onDecrease,
            onIncrease= onIncrease,
            onSendToWatch= onSendToWatch,
            onSaveToFirebase= onSaveToFirebase,
            extraEditorContent = { GoalPresetRow()},
            onGoalDropped = onGoalDropped
       )
    } else {
        CompactDashboard(
            stepsGoal= stepsGoal,
            sendStatus= sendStatus,
            layoutLabel= layoutLabel,
            onDecrease= onDecrease,
            onIncrease= onIncrease,
            onSendToWatch= onSendToWatch,
            onSaveToFirebase= onSaveToFirebase
        )
    }

}