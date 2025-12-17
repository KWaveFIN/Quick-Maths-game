package com.example.quickmaths.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.highScoreDataStore by preferencesDataStore(
    name = "high_scores"
)

class HighScoreRepository(private val context: Context) {

    companion object {
        private val HIGH_SCORE = intPreferencesKey("high_score")
    }

    val highScoreFlow: Flow<Int> =
        context.highScoreDataStore.data.map { prefs ->
            prefs[HIGH_SCORE] ?: 0
        }

    suspend fun saveHighScore(score: Int) {
        context.highScoreDataStore.edit { prefs ->
            val currentHighScore = prefs[HIGH_SCORE] ?: 0
            if (score > currentHighScore) {
                prefs[HIGH_SCORE] = score
            }
        }
    }
}