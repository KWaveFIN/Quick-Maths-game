package com.example.quickmaths.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import com.example.quickmaths.data.SettingsRepository
import com.example.quickmaths.data.HighScoreRepository
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val highScoreRepository: HighScoreRepository
) : ViewModel() {

    private val _countdown = MutableStateFlow(3)
    val countdown: StateFlow<Int> = _countdown

    private val _timer = MutableStateFlow(10f)
    val timer: StateFlow<Float> = _timer

    private val _maxTimer = MutableStateFlow(10f)
    val maxTimer: StateFlow<Float> = _maxTimer

    private val _gameStarted = MutableStateFlow(false)
    val gameStarted: StateFlow<Boolean> = _gameStarted

    private val _currentNumber = MutableStateFlow(0)
    val currentNumber: StateFlow<Int> = _currentNumber

    private val _calculation = MutableStateFlow("")
    val calculation: StateFlow<String> = _calculation

    private val _numberToAdd = MutableStateFlow(0)
    val numberToAdd: StateFlow<Int> = _numberToAdd

    private val _userInput = MutableStateFlow(0)
    val userInput: StateFlow<Int> = _userInput

    private val _score = MutableStateFlow(0)
    val score: StateFlow<Int> = _score

    fun startTimer() {
        viewModelScope.launch {
            _gameStarted.value = false
            _score.value = 0
            _currentNumber.value = 0
            _userInput.value = 0
            _calculation.value = ""

            for (i in 3 downTo 1) {
                _countdown.value = i
                delay(1000)
            }
            _countdown.value = 0
            _gameStarted.value = true
            game()
        }
    }

    private suspend fun game() {
        val currentSettings = settingsRepository.settingsFlow.first()

        when (currentSettings.gameMode) {
            "Addition" -> addition(currentSettings.difficultySetting)
            "Multiplication" -> multiplication(currentSettings.difficultySetting)
            "Mixed" -> mixed(currentSettings.difficultySetting)
            else -> {
                return
            }
        }
    }

    private suspend fun addition(difficultySetting: String) {

        _currentNumber.value = 0

        while (_gameStarted.value) {
            if (_currentNumber.value < 100) {
                _numberToAdd.value = (1..10).random()
            } else if (_currentNumber.value < 1000) {
                _numberToAdd.value = (1..100).random()
            } else if (_currentNumber.value < 10000) {
                _numberToAdd.value = (1..1000).random()
            } else if (_currentNumber.value < 100000) {
                _numberToAdd.value = (1..10000).random()
            } else {
                _numberToAdd.value = (1..100000).random()
            }

            _calculation.value = "${_currentNumber.value} + ${_numberToAdd.value}"


            val correctNumber = _currentNumber.value + _numberToAdd.value

            val initialTimerValue = when (difficultySetting) {
                "Easy" -> 20
                "Normal" -> 10
                "Hard" -> 5
                else -> 10
            }
            _timer.value = initialTimerValue.toFloat()
            _maxTimer.value = initialTimerValue.toFloat()

            _userInput.value = 0

            // Wait loop: checks for answer every 10ms
            val startTime = System.currentTimeMillis()
            val initialDurationMillis = initialTimerValue * 1000L
            
            while (_userInput.value != correctNumber && _gameStarted.value) {
                delay(10)
                
                val elapsedMillis = System.currentTimeMillis() - startTime
                val remainingMillis = initialDurationMillis - elapsedMillis
                
                _timer.value = (remainingMillis / 1000f).coerceAtLeast(0f)
                
                if (remainingMillis <= 0) {
                    _gameStarted.value = false
                    _timer.value = 0f
                }
            }

            if (!_gameStarted.value) break

            _currentNumber.value = correctNumber
            _score.value++
        }
        onGameFinished(_score.value)
    }

    private suspend fun multiplication(difficultySetting: String) {

        _currentNumber.value = 1

        while (_gameStarted.value) {
            if (_currentNumber.value < 1000) {
                _numberToAdd.value = (1..10).random()
            } else if (_currentNumber.value < 10000) {
                _numberToAdd.value = (1..100).random()
            } else if (_currentNumber.value < 100000) {
                _numberToAdd.value = (1..1000).random()
            } else if (_currentNumber.value < 1000000) {
                _numberToAdd.value = (1..10000).random()
            } else {
                _numberToAdd.value = (1..1000000).random()
            }

            _calculation.value = "${_currentNumber.value} * ${_numberToAdd.value}"

            val correctNumber = _currentNumber.value * _numberToAdd.value

            val initialTimerValue = when (difficultySetting) {
                "Easy" -> 20
                "Normal" -> 10
                "Hard" -> 5
                else -> 10
            }
            _timer.value = initialTimerValue.toFloat()
            _maxTimer.value = initialTimerValue.toFloat()

            _userInput.value = 0

             // Wait loop: checks for answer every 10ms
            val startTime = System.currentTimeMillis()
            val initialDurationMillis = initialTimerValue * 1000L

            while (_userInput.value != correctNumber && _gameStarted.value) {
                delay(10)

                val elapsedMillis = System.currentTimeMillis() - startTime
                val remainingMillis = initialDurationMillis - elapsedMillis

                _timer.value = (remainingMillis / 1000f).coerceAtLeast(0f)

                if (remainingMillis <= 0) {
                    _gameStarted.value = false
                    _timer.value = 0f
                }
            }
             
             if (!_gameStarted.value) break

            _currentNumber.value = correctNumber
            _score.value++
        }
        onGameFinished(_score.value)
    }

    private fun mixed(difficultySetting: String) {
        // TODO
        stopGame()
    }

    fun onGameFinished(score: Int) {
        viewModelScope.launch {
            val currentSettings = settingsRepository.settingsFlow.first()
            highScoreRepository.saveHighScore(score, currentSettings.gameMode, currentSettings.difficultySetting)
        }
    }

    fun stopGame() {
        _gameStarted.value = false
    }

    fun setUserInput(input: Int) {
        if (_gameStarted.value) {
            _userInput.value = input
        }
    }
}