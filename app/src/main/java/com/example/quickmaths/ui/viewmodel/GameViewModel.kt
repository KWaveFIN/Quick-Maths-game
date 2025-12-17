package com.example.quickmaths.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.quickmaths.data.SettingsRepository

class GameViewModel(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _countdown = MutableStateFlow(3)
    val countdown: StateFlow<Int> = _countdown

    private val _gameStarted = MutableStateFlow(false)
    val gameStarted: StateFlow<Boolean> = _gameStarted

    fun startTimer() {
        viewModelScope.launch {
            _gameStarted.value = false

            for (i in 3 downTo 1) {
                _countdown.value = i
                delay(1000)
            }

            _gameStarted.value = true
            game()
        }
    }

    private fun game() {
        
    }
}