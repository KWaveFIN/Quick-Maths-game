package com.example.quickmaths.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.launch
import com.example.quickmaths.data.SettingsRepository
import com.example.quickmaths.data.HighScoreRepository
import com.example.quickmaths.data.Settings
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val highScoreRepository: HighScoreRepository
) : ViewModel() {

    val settings = settingsRepository.settingsFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = Settings()
        )

    private val _countdown = MutableStateFlow(3)
    val countdown: StateFlow<Int> = _countdown

    private val _gameStarted = MutableStateFlow(false)
    val gameStarted: StateFlow<Boolean> = _gameStarted

    private val _currentNumber = MutableStateFlow(0)
    val currentNumber: StateFlow<Int> = _currentNumber

    private val _numberToAdd = MutableStateFlow(0)
    val numberToAdd: StateFlow<Int> = _numberToAdd

    private val _userInput = MutableStateFlow(0)
    val userInput: StateFlow<Int> = _userInput

    private val _score = MutableStateFlow(0)
    val score: StateFlow<Int> = _score

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
        val currentSettings = settings.value

        when (currentSettings.gameMode) {
            "Addition" -> addition(currentSettings.difficultySetting)
            "Subtraction" -> subtraction(currentSettings.difficultySetting)
            "Multiplication" -> multiplication(currentSettings.difficultySetting)
            else -> {
                return
            }
        }

    }

    private fun addition(difficultySetting: String) {

        while (_gameStarted.value) {
            if (_currentNumber.value < 10) {
                _numberToAdd.value = (1..10).random()
            } else if (_currentNumber.value < 100) {
                _numberToAdd.value = (1..100).random()
            } else if (_currentNumber.value < 1000) {
                _numberToAdd.value = (1..1000).random()
            } else if (_currentNumber.value < 10000) {
                _numberToAdd.value = (1..10000).random()
            } else {
                _numberToAdd.value = (1..100000).random()
            }

            val correctNumber = _currentNumber.value + _numberToAdd.value

            while (_userInput.value != correctNumber) {
                // TODO: Tick down timer here by difficultySetting
            }
            _currentNumber.value = correctNumber

            _score.value++
        }
        onGameFinished(_score.value)
    }

    private fun subtraction(difficultySetting: String) {
        // TODO
    }

    private fun multiplication(difficultySetting: String) {

        while (_gameStarted.value) {
            if (_currentNumber.value < 10) {
                _numberToAdd.value = (1..10).random()
            } else if (_currentNumber.value < 100) {
                _numberToAdd.value = (1..100).random()
            } else if (_currentNumber.value < 1000) {
                _numberToAdd.value = (1..1000).random()
            } else if (_currentNumber.value < 10000) {
                _numberToAdd.value = (1..10000).random()
            } else {
                _numberToAdd.value = (1..100000).random()
            }

            val correctNumber = _currentNumber.value * _numberToAdd.value

            while (_userInput.value != correctNumber) {
                // TODO: Tick down timer here by difficultySetting
            }

            _currentNumber.value = correctNumber

            _score.value++
        }
        onGameFinished(_score.value)
    }

    fun onGameFinished(score: Int) {
        viewModelScope.launch {
            highScoreRepository.saveHighScore(score)
        }
    }

    fun stopGame() {
        _gameStarted.value = false
    }

    fun setUserInput(input: Int) {
        _userInput.value = input
    }
}