package com.example.quickmaths.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.quickmaths.ui.viewmodel.GameViewModel

@Composable
fun GameScreen(navController: NavHostController, modifier: Modifier = Modifier, viewModel: GameViewModel = viewModel()) {

    val countdown by viewModel.countdown.collectAsState()
    val gameStarted by viewModel.gameStarted.collectAsState()
    val currentNumber by viewModel.currentNumber.collectAsState()
    val numberToAdd by viewModel.numberToAdd.collectAsState()
    val userInput by viewModel.userInput.collectAsState()
    val score by viewModel.score.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.startTimer()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
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
                    viewModel.stopGame()
                    navController.popBackStack()
                }
        )


    }
    /* TODO: Implemet the game screen. It has a screen at the top that
        displays calculations to solve and buttons at the bottom for numbers 0-9 and a backspace.
        There is also a small text box on top of the buttons that reflects user input. Above the
        calculation there is also a bar that ticks down like a timer for each calculation.
        Game should start after a 3 second countdown shown with large numbers in the middle of
        the screen.
     */
}