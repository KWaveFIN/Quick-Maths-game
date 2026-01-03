# Quick-Maths-game
A simple math game made with Kotlin using Jetpack Compose.

# Functionality

- 3 game modes: addition, multiplication, mixed
- 3 difficulty settings: easy, normal and hard. This changes the time to input the correct answer in the game.
- High score tracking for each game mode and difficulty visible on the high score page
- Settings menu to select game mode and difficulty.

# Rules

- The game begins from 0 or 1 depending on game mode.
- The screen displays a calculation and the user has to input the correct answer within the time limit shown above the calculation.
- A correct answer is worth one point.
- The answer to the previous calculation is always the base for the next calculation.
- The other number for the calculation is randomly generated and will increase in size as the game goes on.
- The game will go on as long as the user can keep providing the correct answer within the time limit or when the user interrupts the game by pressing the back arrow in the top left corner.
- In case the score of the game is a new high score, it will be saved when the game ends or is interrupted.
