package com.example.quickmaths

import android.os.Bundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.quickmaths.ui.theme.QuickMathsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuickMathsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainMenuScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun MainMenuScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Game title
        Text(
            text = "Quick Maths!",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Start Game Button
        Button(
            onClick = { /* TODO: navigate to game screen */ },
            modifier = Modifier.fillMaxWidth(0.7f)
        ) {
            Text("Start Game")
        }

        Spacer(modifier = Modifier.height(20.dp))

        // High Scores Button
        Button(
            onClick = { /* TODO: navigate to high scores screen */ },
            modifier = Modifier.fillMaxWidth(0.7f)
        ) {
            Text("High Scores")
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Settings Button
        Button(
            onClick = { /* TODO: navigate to settings */ },
            modifier = Modifier.fillMaxWidth(0.7f)
        ) {
            Text("Settings")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    QuickMathsTheme {
        MainMenuScreen()
    }
}