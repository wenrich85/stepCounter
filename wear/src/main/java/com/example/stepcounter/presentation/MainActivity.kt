package com.example.stepcounter.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import com.example.stepcounter.presentation.theme.StepCounterTheme

class MainActivity : ComponentActivity (){
    private var heartRate by mutableIntStateOf(72)
    private lateinit var heartRateSensorManager: HeartRateSensorManager

    private val heartRatePermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ){ isGranted ->
        if (isGranted) {
            heartRateSensorManager.startListening()
        }
    }

    override  fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        createNotificationChannel(this)

        heartRateSensorManager = HeartRateSensorManager(
            context = this,
            onHeartRateChanged = { newHeartRate -> heartRate = newHeartRate }
        )
        if(heartRateSensorManager.hasHeartRateSensor && !heartRateSensorManager.hasPermission()){
            heartRatePermissionLauncher.launch(heartRateSensorManager.requiredPermission)
        }
        setContent {
            StepCounterTheme {
                WearFitnessApp(
                    heartRateSensorValue = heartRate,
                    hasHeartRateSensor = heartRateSensorManager.hasHeartRateSensor
                )
            }
        }
    }
}