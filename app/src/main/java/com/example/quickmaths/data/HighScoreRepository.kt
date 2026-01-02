package com.example.quickmaths.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.highScoreDataStore by preferencesDataStore(
    name = "high_scores"
)

data class HighScores(
    val additionEasy: Int = 0,
    val additionNormal: Int = 0,
    val additionHard: Int = 0,
    val multiplicationEasy: Int = 0,
    val multiplicationNormal: Int = 0,
    val multiplicationHard: Int = 0,
    val mixEasy: Int = 0,
    val mixNormal: Int = 0,
    val mixHard: Int = 0,
    val selectedMode: String = "Addition"
)

class HighScoreRepository(private val context: Context) {

    companion object {
        private val ADDITION_EASY = intPreferencesKey("addition_easy")
        private val ADDITION_NORMAL = intPreferencesKey("addition_normal")
        private val ADDITION_HARD = intPreferencesKey("addition_hard")
        private val MULTIPLICATION_EASY = intPreferencesKey("multiplication_easy")
        private val MULTIPLICATION_NORMAL = intPreferencesKey("multiplication_normal")
        private val MULTIPLICATION_HARD = intPreferencesKey("multiplication_hard")
        private val MIX_EASY = intPreferencesKey("mixed_easy")
        private val MIX_NORMAL = intPreferencesKey("mixed_normal")
        private val MIX_HARD = intPreferencesKey("mixed_hard")
        private val SELECTED_MODE = stringPreferencesKey("selected_mode")
    }

    val highScoreFlow: Flow<HighScores> =
        context.highScoreDataStore.data.map { prefs ->
            HighScores(
                additionEasy = prefs[ADDITION_EASY] ?: 0,
                additionNormal = prefs[ADDITION_NORMAL] ?: 0,
                additionHard = prefs[ADDITION_HARD] ?: 0,
                multiplicationEasy = prefs[MULTIPLICATION_EASY] ?: 0,
                multiplicationNormal = prefs[MULTIPLICATION_NORMAL] ?: 0,
                multiplicationHard = prefs[MULTIPLICATION_HARD] ?:0,
                mixEasy = prefs[MIX_EASY] ?: 0,
                mixNormal = prefs[MIX_NORMAL] ?: 0,
                mixHard = prefs[MIX_HARD] ?: 0,
                selectedMode = prefs[SELECTED_MODE] ?: "Addition"
            )
        }

    suspend fun saveHighScore(score: Int, mode: String, difficulty: String) {
        context.highScoreDataStore.edit { prefs ->
            val realMode = mode.lowercase()
            val realDifficulty = difficulty.lowercase()
            val keyString = "${realMode}_${realDifficulty}"
            val key = intPreferencesKey(keyString)
            
            val currentHighScore = prefs[key] ?: 0
            if (score > currentHighScore) {
                prefs[key] = score
            }
        }
    }

    suspend fun updateGameMode(mode: String) {
        context.highScoreDataStore.edit { prefs ->
            prefs[SELECTED_MODE] = mode
        }
    }
}