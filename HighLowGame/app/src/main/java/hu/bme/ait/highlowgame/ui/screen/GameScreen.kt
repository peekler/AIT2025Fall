package hu.bme.ait.highlowgame.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import hu.bme.ait.highlowgame.R
import kotlin.random.Random
import kotlin.random.nextInt


@Composable
fun GameScreen(modifier: Modifier,
               upperBound: Int = 3,
               viewModel: GameViewModel = viewModel()
               ) {
    var userInput by remember { mutableStateOf("0") }
    var resultText by remember { mutableStateOf("-") }

    var errorText by remember { mutableStateOf("-") }
    var inputErrorState by remember { mutableStateOf(false) }

    var showDialog by rememberSaveable { mutableStateOf(false) }

    fun validateInput(input: String) {
        try {
            val myNum = input.toInt()
            inputErrorState = false
        } catch (e: Exception) {
            errorText = e.localizedMessage
            inputErrorState = true
        }
    }

    /*LaunchedEffect(Unit) {
        generatedNum = Random.nextInt(3) // 0..2
    }*/

    Column(modifier = modifier) {
        OutlinedTextField(
            label = { Text("Enter your guess here (0..$upperBound):") },
            modifier = Modifier.fillMaxWidth(),
            value = userInput,
            onValueChange = {
                userInput = it
                validateInput(userInput)
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal
            ),
            trailingIcon = {
                if (inputErrorState)
                    Icon(Icons.Filled.Warning, "error", tint = MaterialTheme.colorScheme.error)
            },
            leadingIcon = {
               Icon(Icons.Filled.Info, "Number", tint = MaterialTheme.colorScheme.primaryContainer)
            },
            isError = inputErrorState
        )

        Button(
            enabled = !inputErrorState,
            onClick = {
                viewModel.increaseCounter()

                val userNum = userInput.toInt()
                if (viewModel.generatedNumber.value == userNum) {
                    showDialog = true
                    resultText = "You have won!"
                } else if (viewModel.generatedNumber.value < userNum) {
                    resultText = "The number is lower"
                } else if (viewModel.generatedNumber.value > userNum) {
                    resultText = "The number is higher"
                }

            }) {
            Text("Guess")
        }

        Text(
            "Steps: ${viewModel.counter.value}"
        )

        if (inputErrorState) {
            Text(
                errorText,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.error,
                fontStyle = FontStyle.Italic,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        Text(
            "$resultText",
            fontSize = 30.sp
        )

        if (showDialog)
        {
            WinDialog(
                onCancel = {showDialog = false},
                onConfirm = {
                    showDialog = false
                    viewModel.reset()
                    resultText = "-"
                    userInput = "0"
                }
            )
        }
    }
}

@Composable
fun WinDialog(
    onConfirm: () -> Unit,
    onCancel: () -> Unit
)
{
    val context = LocalContext.current // represents the Activity where the composable is running and Activity can access the resurces

    AlertDialog(
        onDismissRequest = onCancel,
        title = {Text(context.getString(R.string.text_congrat))},
        text = {Text(context.getString(R.string.text_win))},
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(context.getString(R.string.btn_ok))
            }
        },
        dismissButton = {
            TextButton(onClick = onCancel) {
                Text(context.getString(R.string.btn_cancel))
            }
        },
    )
}
