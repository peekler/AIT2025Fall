package hu.bme.ait.hiltcomposedemo

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.ait.hiltcomposedemo.analytics.AnalyticsEngine
import hu.bme.ait.hiltcomposedemo.analytics.TestAnalytics
import hu.bme.ait.hiltcomposedemo.logging.MainLogger
import hu.bme.ait.hiltcomposedemo.toaster.DemoToaster
import hu.bme.ait.hiltcomposedemo.ui.theme.HiltComposeDemoTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var info: Info

    @Inject
    lateinit var vehicle: Vehicle

    @Inject
    lateinit var logger: MainLogger

    @TestAnalytics
    @Inject
    lateinit var analyitics: AnalyticsEngine

    @Inject
    lateinit var demoToaster: DemoToaster

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HiltComposeDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {

        var result by rememberSaveable { mutableStateOf("") }

        Column (
            modifier = modifier
        ) {
            Button(onClick = {
                result = info.text
                //result = vehicle.type
                //tvHello.text = analyitics.doTest()

                demoToaster.doToast()

                logger.doLogging()
            }) {
                Text("DI Demo")
            }
            Text(result)
        }
    }
}

