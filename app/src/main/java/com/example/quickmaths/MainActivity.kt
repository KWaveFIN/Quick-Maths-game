package com.example.quickmaths

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.quickmaths.ui.screens.GameScreen
import com.example.quickmaths.ui.screens.HighScoresScreen
import com.example.quickmaths.ui.screens.MainMenuScreen
import com.example.quickmaths.ui.screens.SettingsScreen
import com.example.quickmaths.ui.theme.QuickMathsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuickMathsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "main_menu",
                        modifier = Modifier.safeDrawingPadding()
                    ) {
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