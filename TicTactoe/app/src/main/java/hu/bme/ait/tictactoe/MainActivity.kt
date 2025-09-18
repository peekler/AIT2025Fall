package hu.bme.ait.tictactoe

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import hu.bme.ait.tictactoe.ui.theme.TicTactoeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TicTactoeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TicTacToeScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun TicTacToeScreen(modifier: Modifier) {
    var circleX by rememberSaveable { mutableStateOf(0f) }
    var circleY by rememberSaveable { mutableStateOf(0f) }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Canvas(modifier = Modifier.fillMaxSize()
            .pointerInput(key1 = Unit) {
                detectTapGestures {
                    offset ->
                        Log.d("TAG_CLICK_POS", "${offset.x} ${offset.y}")
                        circleX = offset.x
                        circleY = offset.y
                }
            }
        )
        {
            val canvasWidth = size.width
            val canvasHeight = size.height

            drawRect(
                color = Color.Red,
                size = size / 2f
            )

            drawCircle(
                color = Color.Blue,
                radius = 100f,
                style = Fill,
                center = Offset(circleX,
                    if (circleY > canvasHeight) canvasHeight else circleY)
            )

        }
    }
}