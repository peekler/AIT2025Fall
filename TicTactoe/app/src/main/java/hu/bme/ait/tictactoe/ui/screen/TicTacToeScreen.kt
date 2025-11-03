package hu.bme.ait.tictactoe.ui.screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.wajahatkarim.flippable.Flippable
import com.wajahatkarim.flippable.rememberFlipController
import hu.bme.ait.tictactoe.R
import net.engawapg.lib.zoomable.rememberZoomState
import net.engawapg.lib.zoomable.zoomable

@Composable
fun TicTacToeScreen(
    modifier: Modifier = Modifier,
    ticTactToeViewModel: TicTactToeViewModel = viewModel()
) {
    val context = LocalContext.current
    val zoomState = rememberZoomState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .zoomable(zoomState),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        /*Flippable(
            frontSide = {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 8.dp,
                        pressedElevation = 2.dp,
                        focusedElevation = 4.dp
                    ),
                    colors = CardDefaults.cardColors(Color(0xFFFFF9C4)),
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(), // Fill the Card
                        contentAlignment = Alignment.Center // Center the content
                    ) {
                        Text(
                            text = "Welcome in the Game",
                            style = MaterialTheme.typography.titleSmall // Or any other style you prefer
                        )
                    }
                }
            },
            backSide = {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 8.dp,
                        pressedElevation = 2.dp,
                        focusedElevation = 4.dp
                    ),
                    colors = CardDefaults.cardColors(Color(0xFF549DDC)),
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(), // Fill the Card
                        contentAlignment = Alignment.Center // Center the content
                    ) {
                        Text(
                            text = "BACKSIDE",
                            style = MaterialTheme.typography.titleSmall // Or any other style you prefer
                        )
                    }
                }
            },
            flipController = rememberFlipController()
        )*/




        Text(
            context.getString(
                R.string.next_player_text,
                ticTactToeViewModel.currentPlayer
            ),
            fontSize = 28.sp,
        )

        /*TicTacToeBoard(ticTactToeViewModel.board,
            {
                ticTactToeViewModel.onCellClicked(it)
            }
        )*/

        TicTacToeBoard(ticTactToeViewModel.board) {
            ticTactToeViewModel.onCellClicked(it)
        }


        Button(onClick = {
            ticTactToeViewModel.setNewBoard(3)
        }) {
            Text(context.getString(R.string.btn_reset))
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TicTacToeBoard(
    board: Array<Array<Player?>>,
    onCellClicked: (BoardCell) -> Unit
) {
    val textMeasurer = rememberTextMeasurer()

    val monkeyImage: ImageBitmap =
        ImageBitmap.imageResource(R.drawable.monkey)

    var canvasSize by remember { mutableStateOf(Size.Zero) }

    Canvas(
        modifier = Modifier
            .fillMaxSize(0.5f)
            .aspectRatio(1.0f)
            .pointerInput(key1 = Unit) {
                detectTapGestures {
                    val cellX = (it.x / (canvasSize.width / 3)).toInt()
                    val cellY = (it.y / (canvasSize.height / 3)).toInt()

                    onCellClicked(
                        BoardCell(cellY, cellX)
                    )
                }
            }
    ) {
        canvasSize = this.size
        val canvasWidth = size.width.toInt()
        val canvasHeight = size.height.toInt()


        // Draw the grid
        val gridSize = size.minDimension
        val thirdSize = gridSize / 3

        drawImage(
            monkeyImage,
            srcOffset = IntOffset(0, 0),
            srcSize = IntSize(monkeyImage.width, monkeyImage.height),
            dstOffset = IntOffset(2 * thirdSize.toInt(), thirdSize.toInt()),
            dstSize = IntSize(thirdSize.toInt(), thirdSize.toInt())
        )

        val textLayoutResult: TextLayoutResult =
            textMeasurer.measure(
                text = "3",
                style = TextStyle(
                    fontSize = thirdSize.toSp(),
                    fontWeight = FontWeight.Bold
                )
            )
        val textSize = textLayoutResult.size

        drawText(
            textLayoutResult = textLayoutResult,
            topLeft = Offset(
                x = 3 * (thirdSize / 2) - textSize.width / 2,
                y = 3 * (thirdSize / 2) - textSize.height / 2
            ),
        )



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

        // Draw players.. X and O
        for (row in 0..2) {
            for (col in 0..2) {
                val player = board[row][col]
                if (player != null) {
                    val centerX = col * thirdSize + thirdSize / 2
                    val centerY = row * thirdSize + thirdSize / 2
                    if (player == Player.X) {
                        drawLine(
                            color = Color.Black,
                            strokeWidth = 8f,
                            pathEffect = PathEffect.cornerPathEffect(4f),
                            start = androidx.compose.ui.geometry.Offset(
                                centerX - thirdSize / 4,
                                centerY - thirdSize / 4
                            ),
                            end = androidx.compose.ui.geometry.Offset(
                                centerX + thirdSize / 4,
                                centerY + thirdSize / 4
                            ),
                        )
                        drawLine(
                            color = Color.Black,
                            strokeWidth = 8f,
                            pathEffect = PathEffect.cornerPathEffect(4f),
                            start = androidx.compose.ui.geometry.Offset(
                                centerX + thirdSize / 4,
                                centerY - thirdSize / 4
                            ),
                            end = androidx.compose.ui.geometry.Offset(
                                centerX - thirdSize / 4,
                                centerY + thirdSize / 4
                            ),
                        )
                    } else {
                        drawCircle(
                            color = Color.Black,
                            style = Stroke(width = 8f),
                            center = androidx.compose.ui.geometry.Offset(centerX, centerY),
                            radius = thirdSize / 4,
                        )
                    }
                }
            }
        }
    }
}