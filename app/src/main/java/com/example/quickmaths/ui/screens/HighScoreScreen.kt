package com.example.quickmaths.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.quickmaths.R
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.quickmaths.ui.viewmodel.HighScoreViewModel

@Composable
fun HighScoresScreen(
    navController: NavHostController, 
    modifier: Modifier = Modifier,
    viewModel: HighScoreViewModel = hiltViewModel()
) {

    val isDropDownExpandedMode = remember {
        mutableStateOf(false)
    }

    val itemPosition = remember {
        mutableIntStateOf(0)
    }

    val gameModes = listOf("Addition", "Multiplication", "Mixed")

    val difficulties = listOf("Easy", "Normal", "Hard")
    
    val highScores by viewModel.highScores.collectAsStateWithLifecycle()

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
                        navController.popBackStack()
                    }
            )

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Box {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable {
                            isDropDownExpandedMode.value = true
                        }
                    ) {
                        Text("Game mode: ${highScores.selectedMode}")
                        Image(
                            painter = painterResource(id = R.drawable.drop_down_ic),
                            contentDescription = "DropDown Icon"
                        )
                    }
                    DropdownMenu(
                        expanded = isDropDownExpandedMode.value,
                        onDismissRequest = {
                            isDropDownExpandedMode.value = false
                        }) {
                        gameModes.forEachIndexed { index, mode ->
                            DropdownMenuItem(
                                text = {
                                    Text(text = mode)
                                },
                                onClick = {
                                    isDropDownExpandedMode.value = false
                                    itemPosition.intValue = index
                                    viewModel.setGameMode(mode)
                                })
                        }
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))

                // Display scores for the selected mode
                difficulties.forEach { difficulty ->
                    val score = viewModel.getHighScore(highScores, highScores.selectedMode, difficulty)
                    Text(
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.headlineLarge,
                        fontSize = 20.sp,
                        text = "$difficulty: $score"
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}