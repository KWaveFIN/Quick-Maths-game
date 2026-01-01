package com.example.quickmaths.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.launch
import com.example.quickmaths.data.HighScoreRepository
import com.example.quickmaths.data.HighScores
import javax.inject.Inject

@HiltViewModel
class HighScoreViewModel @Inject constructor(
    private val highScoreRepository: HighScoreRepository
) : ViewModel() {

    val highScores = highScoreRepository.highScoreFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HighScores()
        )

    fun setGameMode(mode: String) {
        viewModelScope.launch {
            highScoreRepository.updateGameMode(mode)
        }
    }

    fun getHighScore(highScores: HighScores, mode: String, difficulty: String): Int {
        return when (mode) {
            "Addition" -> when (difficulty) {
                "Easy" -> highScores.additionEasy
                "Normal" -> highScores.additionNormal
                "Hard" -> highScores.additionHard
                else -> 0
            }

            "Multiplication" -> when (difficulty) {
                "Easy" -> highScores.multiplicationEasy
                "Normal" -> highScores.multiplicationNormal
                "Hard" -> highScores.multiplicationHard
                else -> 0
            }

            "Mixed" -> when (difficulty) {
                "Easy" -> highScores.mixEasy
                "Normal" -> highScores.mixNormal
                "Hard" -> highScores.mixHard
                else -> 0
            }

            else -> 0
        }
    }
}