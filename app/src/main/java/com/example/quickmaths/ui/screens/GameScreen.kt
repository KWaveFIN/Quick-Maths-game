package com.example.quickmaths.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Backspace
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.quickmaths.ui.viewmodel.GameViewModel

@Composable
fun GameScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: GameViewModel = hiltViewModel()
) {

    val countdown by viewModel.countdown.collectAsStateWithLifecycle()
    val gameStarted by viewModel.gameStarted.collectAsStateWithLifecycle()
    val calculation by viewModel.calculation.collectAsStateWithLifecycle()
    val userInput by viewModel.userInput.collectAsStateWithLifecycle()
    val score by viewModel.score.collectAsStateWithLifecycle()
    val timer by viewModel.timer.collectAsStateWithLifecycle()
    val maxTimer by viewModel.maxTimer.collectAsStateWithLifecycle()


    LaunchedEffect(Unit) {
        viewModel.startTimer()
    }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Back button in top corner
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .padding(16.dp)
                    .size(32.dp)
                    .align(Alignment.TopStart)
                    .clickable {
                        viewModel.stopGame()
                        navController.popBackStack()
                    }
            )

            if (countdown <= 1 && gameStarted) {

                Box(modifier = Modifier.fillMaxSize()) {

                    // Calculation Display (Top)
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {
                        // Timer Progress Bar
                        if (maxTimer > 0) {
                            LinearProgressIndicator(
                                progress = { timer / maxTimer },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 64.dp, start = 32.dp, end = 32.dp)
                                    .height(8.dp),
                                color = MaterialTheme.colorScheme.primary,
                                trackColor = MaterialTheme.colorScheme.surfaceVariant,
                            )
                        }

                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.padding(top = 120.dp)
                        ) {
                            Text(
                                color = MaterialTheme.colorScheme.onBackground,
                                style = MaterialTheme.typography.headlineLarge,
                                softWrap = true,
                                text = calculation
                            )
                        }
                    }

                    // NumPad (Bottom)
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // User Input Display
                        Text(
                            text = userInput.toString(),
                            style = MaterialTheme.typography.displayMedium,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.padding(bottom = 24.dp)
                        )

                        // Number Grid
                        val rows = listOf(
                            listOf(1, 2, 3),
                            listOf(4, 5, 6),
                            listOf(7, 8, 9)
                        )

                        rows.forEach { row ->
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(16.dp),
                                modifier = Modifier.padding(bottom = 16.dp)
                            ) {
                                row.forEach { number ->
                                    NumPadButton(text = number.toString()) {
                                        if (userInput.toString().length < 9) {
                                            viewModel.setUserInput(userInput * 10 + number)
                                        }
                                    }
                                }
                            }
                        }

                        // Bottom Row: 0 and Backspace
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Spacer(modifier = Modifier.size(80.dp))

                            NumPadButton(text = "0") {
                                if (userInput.toString().length < 9) {
                                    viewModel.setUserInput(userInput * 10)
                                }
                            }

                            Button(
                                onClick = { viewModel.setUserInput(userInput / 10) },
                                modifier = Modifier.size(80.dp),
                                shape = MaterialTheme.shapes.medium
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.Backspace,
                                    contentDescription = "Backspace"
                                )
                            }
                        }
                    }
                }
            }
            else if (!gameStarted && countdown < 1) {
                // Game Over Screen
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.headlineLarge,
                        fontSize = 40.sp,
                        text = "Game Over!"
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    Text(
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.headlineLarge,
                        fontSize = 20.sp,
                        text = "Score: $score"
                    )
                }
            }
            else {
                // Countdown Screen
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.headlineLarge,
                        fontSize = 40.sp,
                        text = "$countdown"
                    )
                }
            }
        }
    }
}

@Composable
fun NumPadButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.size(80.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Text(text = text, style = MaterialTheme.typography.titleLarge)
    }
}