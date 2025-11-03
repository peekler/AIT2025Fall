package hu.bme.ait.highlowgame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.scene.rememberSceneSetupNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import hu.bme.ait.highlowgame.ui.navigation.GameScreenRoute
import hu.bme.ait.highlowgame.ui.navigation.HomeScreenRoute
import hu.bme.ait.highlowgame.ui.screen.GameScreen
import hu.bme.ait.highlowgame.ui.screen.GameViewModel
import hu.bme.ait.highlowgame.ui.screen.HomeScreen
import hu.bme.ait.highlowgame.ui.theme.HighLowGameTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HighLowGameTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    /*GameScreen(
                        modifier = Modifier.padding(
                            innerPadding)
                    )*/

                    NavGrap(modifier = Modifier.padding(innerPadding))
                }
            }
        }

    }

    @Composable
    fun NavGrap(modifier: Modifier) {
        val backStack = rememberNavBackStack(HomeScreenRoute)

        // NavDisplay can actually display the current screen from those listed in the entryProvider
        NavDisplay(
            modifier = modifier,
            backStack = backStack,
            onBack = {backStack.removeLastOrNull()},
            entryDecorators = listOf(
                rememberSceneSetupNavEntryDecorator(),
                rememberSavedStateNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator()
            ),
            entryProvider = entryProvider {
                entry<HomeScreenRoute> {
                    HomeScreen(onStartClicked = {
                        backStack.add(GameScreenRoute(5))
                    })
                }
                entry<GameScreenRoute> {
                    GameScreen(modifier,
                        upperBound=it.upperBound,
                        viewModel  = viewModel(factory = GameViewModel.Factory(it))
                        )
                }
            },
            transitionSpec = {
                slideInHorizontally(initialOffsetX = {it}) togetherWith
                        slideOutHorizontally(targetOffsetX = { -it })
            },
            popTransitionSpec = {
                // Slide in from left when navigating back
                slideInHorizontally(initialOffsetX = { -it }) togetherWith
                        slideOutHorizontally(targetOffsetX = { it })
            },
            predictivePopTransitionSpec = {
                // Slide in from left when navigating back
                slideInHorizontally(initialOffsetX = { -it }) togetherWith
                        slideOutHorizontally(targetOffsetX = { it })
            }
        )
    }


}
