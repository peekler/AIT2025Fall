package hu.bme.ait.httpdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.scene.rememberSceneSetupNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import hu.bme.ait.httpdemo.ui.navigation.MainScreenRoute
import hu.bme.ait.httpdemo.ui.navigation.MoneyScreenRoute
import hu.bme.ait.httpdemo.ui.navigation.NewsScreenRoute
import hu.bme.ait.httpdemo.ui.screen.mainscreen.MainScreen
import hu.bme.ait.httpdemo.ui.theme.HTTPDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HTTPDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavGraph(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun NavGraph(modifier: Modifier) {
    val backStack = rememberNavBackStack(MainScreenRoute)

    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        onBack = {backStack.removeLastOrNull()},
        entryDecorators = listOf(
            rememberSceneSetupNavEntryDecorator(),
            rememberSavedStateNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider  = entryProvider {
            entry<MainScreenRoute> {
                MainScreen(
                    onMoneyAPISelected = {backStack.add(MoneyScreenRoute)},
                    onNewsAPISelected = {backStack.add(NewsScreenRoute)}
                )
            }
            entry<MoneyScreenRoute> {
                //MoneyScreen()
            }
            entry<NewsScreenRoute> {
                //NewsScreen()
            }
        }
    )
}

