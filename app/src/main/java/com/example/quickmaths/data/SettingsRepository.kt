package com.example.quickmaths.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "settings")

data class Settings(
    val gameMode: String = "Addition",
    val difficultySetting: String = "Easy"
)

class SettingsRepository(private val context: Context) {

    companion object {
        private val GAME_MODE = stringPreferencesKey("game_mode")
        private val DIFFICULTY = stringPreferencesKey("difficulty")
    }

    val settingsFlow: Flow<Settings> = context.dataStore.data.map { prefs ->
        Settings(
            gameMode = prefs[GAME_MODE] ?: "Addition",
            difficultySetting = prefs[DIFFICULTY] ?: "Easy"
        )
    }

    suspend fun updateGameMode(mode: String) {
        context.dataStore.edit { prefs ->
            prefs[GAME_MODE] = mode
        }
    }

    suspend fun updateDifficulty(level: String) {
        context.dataStore.edit { prefs ->
            prefs[DIFFICULTY] = level
        }
    }
}