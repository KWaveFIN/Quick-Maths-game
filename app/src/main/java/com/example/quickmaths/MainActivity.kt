package com.example.quickmaths

import android.content.res.Configuration
import android.os.Bundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHost
import com.example.quickmaths.ui.theme.QuickMathsTheme
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

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

@Composable
fun MainMenuScreen(navController: NavHostController, modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Close button in top-left
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Close App",
            tint = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .padding(16.dp)
                .size(32.dp)
                .clickable { (context as? android.app.Activity)?.finish() }
        )

        // Main content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Quick Maths!",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.headlineLarge
            )

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = { navController.navigate("game_screen") },
                modifier = Modifier.fillMaxWidth(0.7f)
            ) { Text("Start Game") }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { navController.navigate("high_score_screen") },
                modifier = Modifier.fillMaxWidth(0.7f)
            ) { Text("High Scores") }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { navController.navigate("settings_screen") },
                modifier = Modifier.fillMaxWidth(0.7f)
            ) { Text("Settings") }
        }
    }
}

@Composable
fun GameScreen(navController: NavHostController, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        // Back button in top corner
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back",
            tint = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .padding(16.dp)
                .size(32.dp)
                .clickable {
                    navController.popBackStack()
                }
        )

        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.headlineLarge,
                text = "Game Screen"
            )
        }
    }
    /* TODO: Implemet the game screen. It has a screen at the top that
        displays calculations to solve and buttons at the bottom for numbers 0-9 and a backspace.
        There is also a small text box on top of the buttons that reflects user input. Above the
        calculation there is also a bar that ticks down like a timer for each calculation.
        Game should start after a 3 second countdown shown with large numbers in the middle of
        the screen.
     */
}

@Composable
fun HighScoresScreen(navController: NavHostController, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        // Back button in top corner
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back",
            tint = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .padding(16.dp)
                .size(32.dp)
                .clickable {
                    navController.popBackStack()
                }
        )
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.headlineLarge,
                text = "High Score Screen"
            )
        }
    }
    /* TODO: Implement the high score screen. It has a list of high scores for each game type and
        difficulty rating.
     */
}

@Composable
fun SettingsScreen(navController: NavHostController, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        // Back button in top corner
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back",
            tint = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .padding(16.dp)
                .size(32.dp)
                .clickable {
                    navController.popBackStack()
                }
        )
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.headlineLarge,
                text = "Settings Screen"
            )
        }
    }
    /* TODO: Implement the settings screen. It has a game mode selector as well as a difficulty
        selector. Here the user can also change their username. Maybe also a language selector?
     */
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