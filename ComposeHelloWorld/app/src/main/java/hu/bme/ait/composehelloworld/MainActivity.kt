package hu.bme.ait.composehelloworld

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.bme.ait.composehelloworld.ui.theme.ComposeHelloWorldTheme
import java.util.Date

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ComposeHelloWorldTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }

        var myFun: (Int) -> Unit = {
            val demo = it
        }

        myTaskFun(
            8,
            task = {
                val b = it
                val c = b * b
                Log.d("Result", "$c")
            }
        ) { num1, num2 ->
            val c = num1 + num2
            Log.d("Result", "$c")
        }
    }

    fun myTaskFun(num: Int, task: (Int)->Unit,
                  task2: (Int, Int)->Unit) {
        //....
        task(3)
    }

}



@Composable
fun SimpleForm(modifier: Modifier = Modifier) {
    // State to hold the text from the TextField
    var textFieldValue by remember { mutableStateOf("") }
    // State to hold the text to be displayed below the button
    var displayText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), // Add some padding around the column
        horizontalAlignment = Alignment.CenterHorizontally, // Center children horizontally
        verticalArrangement = Arrangement.spacedBy(16.dp) // Add space between children
    ) {
        // TextField for user input
        TextField(
            value = textFieldValue,
            onValueChange = {
                theTextTypedByTheUser ->
                textFieldValue = theTextTypedByTheUser
                            },
            label = { Text("Enter some text") },
            modifier = Modifier.fillMaxWidth() // Make TextField take full width
        )


        Button(onClick = {}, content = {Text("Press")})

        Button(onClick = {}) {
            Text("Press")
        }

        // Button to trigger the action
        Button(onClick = {
            // When button is clicked, update the displayText with textFieldValue
            displayText = textFieldValue
        }) {
            Text("Show Text")
        }

        // Text to display the content from the TextField
        Text(
            text = displayText,
            style = MaterialTheme.typography.bodyLarge // Use a predefined text style
        )
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    var timeText by rememberSaveable { mutableStateOf("") }
    var inputText by rememberSaveable { mutableStateOf("") }

    Column(modifier = modifier
        .fillMaxSize()
        .border(width = 1.dp, color = Color.Blue),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
        ) {
        Text(
            text = "Current time: $timeText $inputText"
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(0.8f),
            label = {
                Text("Enter data:")
                    },
            value = inputText,
            onValueChange = {
                inputText = it
            }
        )


        Button(
            onClick = {
               timeText = Date(System.currentTimeMillis()).toString()
            }
        )
        {
            Text("Show")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeHelloWorldTheme {
        Column {
            Greeting("Android")
        }
    }
}

