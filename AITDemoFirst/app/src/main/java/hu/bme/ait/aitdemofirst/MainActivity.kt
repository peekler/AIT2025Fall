package hu.bme.ait.aitdemofirst

import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import hu.bme.ait.aitdemofirst.ui.theme.AITDemoFirstTheme
import java.util.Date

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AITDemoFirstTheme {

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "AIT",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }

        }
    }

}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    var currentTime by rememberSaveable { mutableStateOf("") }

    Column(modifier = modifier) {
        Text(
            text = "Time: $currentTime!",
            fontSize = 30.sp
        )
        Button(
            onClick = {
                currentTime = Date(System.currentTimeMillis()).toString()
            }
        ) {
            Text("Press me")
        }
    }



}




@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AITDemoFirstTheme {
        Greeting("Android")
    }
}