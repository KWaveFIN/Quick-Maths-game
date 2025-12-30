package com.example.quickmaths

import com.example.quickmaths.ui.screens.*

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.example.quickmaths.ui.theme.QuickMathsTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuickMathsTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "main_menu") {
                    composable("main_menu") {
                        MainMenuScreen(navController = navController)
                    }
                    composable("game_screen") {
                        GameScreen(navController = navController)
                    }
                    composable("high_score_screen") {
                        HighScoresScreen(navController = navController)
                    }
                    composable("settings_screen") {
                        SettingsScreen(navController = navController)
                    }
                }
            }
        }
    }
}

@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun GreetingPreview() {
    QuickMathsTheme {
        MainMenuScreen(navController = rememberNavController())
    }
}