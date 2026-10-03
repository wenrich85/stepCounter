package com.example.stepcounter.shared.presentation

import com.example.stepcounter.shared.model.FitnessData

data class FitnessUiState(
    val fitnessData: FitnessData = FitnessData(),
    val isLoading: Boolean = true,
    val errorMessage: String? = null
)
