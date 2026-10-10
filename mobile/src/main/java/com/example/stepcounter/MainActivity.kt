package com.example.stepcounter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import com.example.mobile.ui.dashboard.PhoneCompanionApp
import com.example.stepcounter.shared.data.FirebaseRepository

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repository = FirebaseRepository()

        setContent {
            val widthSizeClass = calculateWindowSizeClass(this).widthSizeClass
            MaterialTheme {
                PhoneCompanionApp(
                    repository = repository,
                    widthSizeClass = widthSizeClass
                )
            }
        }
    }
}
