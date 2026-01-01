package com.example.quickmaths.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.launch
import com.example.quickmaths.data.SettingsRepository
import com.example.quickmaths.data.Settings
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    val settings = settingsRepository.settingsFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = Settings()
        )

    fun setGameMode(mode: String) {
        viewModelScope.launch {
            settingsRepository.updateGameMode(mode)
        }
    }

    fun setDifficulty(difficulty: String) {
        viewModelScope.launch {
            settingsRepository.updateDifficulty(difficulty)
        }
    }
}