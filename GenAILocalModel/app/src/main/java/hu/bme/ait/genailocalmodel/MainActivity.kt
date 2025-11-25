package hu.bme.ait.genailocalmodel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier

import androidx.activity.viewModels
import androidx.compose.material3.*
import hu.bme.ait.genailocalmodel.ui.screen.ChatScreen
import hu.bme.ait.genailocalmodel.ui.screen.ChatViewModel

class MainActivity : ComponentActivity() {

    // Simple ViewModel instantiation
    private val viewModel: ChatViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Trigger model loading on startup
        viewModel.initializeModel()

        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Scaffold { innerPadding ->
                        ChatScreen(Modifier.padding(innerPadding),viewModel)
                    }

                }
            }
        }
    }
}
