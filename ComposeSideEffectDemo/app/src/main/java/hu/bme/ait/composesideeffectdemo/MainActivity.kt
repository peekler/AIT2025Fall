package hu.bme.ait.composesideeffectdemo

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import hu.bme.ait.composesideeffectdemo.ui.theme.ComposeSideEffectDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeSideEffectDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    /*Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )*/
                    DisposableDemo(modifier = Modifier.padding(innerPadding) )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    var counter by remember { mutableStateOf(0) }

    SideEffect {
        Log.d("TAG_SIDE","SideEffect called")
    }

    LaunchedEffect(key1= Unit) {
        Log.d("TAG_SIDE","LaunchedEffect called")
    }

    Column(
        modifier = modifier
    ) {
        Button(onClick = {
            counter++
        }) {
            Text("Update")
        }

        Text(
            text = "Hello $counter!"
        )
    }
}

@Composable
fun DisposableDemo(modifier: Modifier) {
    var isVisible by remember { mutableStateOf(true) }

    Column(
        modifier = modifier
    ) {
        Button(onClick = {
            isVisible = !isVisible
        }) {
            Text(text = "Show")
        }

        if (isVisible) {
            DisposableScreen()
        }
    }
}


@Composable
fun DisposableScreen() {
    val context = LocalContext.current

    DisposableEffect(key1 = Unit) {
        Toast.makeText(context, "Enter to composition",
            Toast.LENGTH_LONG).show()

        onDispose {
            Toast.makeText(context, "Leave composition",
                Toast.LENGTH_LONG).show()
        }
    }

    Column() {
        Text(text = "Content...")
    }
}



