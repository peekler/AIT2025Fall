package hu.bme.ait.tictactoe.ui.screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun TicTacToeScreen(
    modifier: Modifier = Modifier,
    ticTactToeViewModel: TicTactToeViewModel = viewModel()
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome in TicTacToe", fontSize = 30.sp)

        TicTacToeBoard()

        Button(onClick = {}) {
            Text("Restart")
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TicTacToeBoard(
) {
    var canvasSize by remember { mutableStateOf(Size.Zero) }

    //val cellSize = 100.dp
    Canvas(
        modifier = Modifier
            .fillMaxSize(0.5f)
            //.size(cellSize * 3)
            .pointerInput(key1 = Unit) {
                detectTapGestures {
                    val cellX = (it.x / (canvasSize.width/3)).toInt()
                    val cellY = (it.y / (canvasSize.height/3)).toInt()

                }
            }
    ) {
        canvasSize = this.size
        val canvasWidth = size.width.toInt()
        val canvasHeight = size.height.toInt()


        // Draw the grid
        val gridSize = size.minDimension
        val thirdSize = gridSize / 3

        for (i in 1..2) {
            drawLine(
                color = Color.Black,
                strokeWidth = 8f,
                pathEffect = PathEffect.cornerPathEffect(4f),
                start = androidx.compose.ui.geometry.Offset(thirdSize * i, 0f),
                end = androidx.compose.ui.geometry.Offset(thirdSize * i, gridSize)
            )
            drawLine(
                color = Color.Black,
                strokeWidth = 8f,

                start = androidx.compose.ui.geometry.Offset(0f, thirdSize * i),
                end = androidx.compose.ui.geometry.Offset(gridSize, thirdSize * i),
            )
        }

    }
}