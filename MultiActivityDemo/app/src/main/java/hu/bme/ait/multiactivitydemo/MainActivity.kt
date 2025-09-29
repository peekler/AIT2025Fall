package hu.bme.ait.multiactivitydemo

import android.content.Intent
import android.os.Bundle
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import hu.bme.ait.multiactivitydemo.ui.theme.MultiActivityDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MultiActivityDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Column(
        modifier = modifier
    ) {
        Button(
            onClick = {
                val intentDetails = Intent()
                intentDetails.setClass(context,
                    DetailsActivity::class.java)

                intentDetails.putExtra("KEY_DATA","DATA: 5")

                context.startActivity(intentDetails)
            }
        ) {
            Text("Launch Details Activity")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MultiActivityDemoTheme {
        Greeting("Android")
    }
}