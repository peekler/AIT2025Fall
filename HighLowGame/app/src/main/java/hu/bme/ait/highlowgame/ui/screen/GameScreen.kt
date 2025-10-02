package hu.bme.ait.highlowgame.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import kotlin.random.Random
import kotlin.random.nextInt


@Composable
fun GameScreen(modifier: Modifier) {
    var userInput by remember { mutableStateOf("0") }
    var resultText by remember { mutableStateOf("-") }
    var generatedNum by remember { mutableStateOf(0) }

    var errorText by remember { mutableStateOf("-") }
    var inputErrorState by remember { mutableStateOf(false) }

    fun validateInput(input: String) {
        try {
            val myNum = input.toInt()
            inputErrorState = false
        } catch (e: Exception) {
            errorText = e.localizedMessage
            inputErrorState = true
        }
    }

    LaunchedEffect(Unit) {
        generatedNum = Random.nextInt(3) // 0..3
    }

    Column(modifier = modifier) {
        OutlinedTextField(
            label = {Text("Enter your guess here:")},
            modifier = Modifier.fillMaxWidth(),
            value = userInput,
            onValueChange = {
                userInput = it
                validateInput(userInput)
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal
            ),
            isError = inputErrorState
        )

        if (!inputErrorState) {
            Button(onClick = {

                val userNum = userInput.toInt()
                if (generatedNum == userNum) {
                    resultText = "You have won!"
                } else if (generatedNum < userNum) {
                    resultText = "The number is lower"
                } else if (generatedNum > userNum) {
                    resultText = "The number is higher"
                }

            }) {
                Text("Guess")
            }
        }

        Text("$resultText",
            fontSize = 30.sp)
    }
}