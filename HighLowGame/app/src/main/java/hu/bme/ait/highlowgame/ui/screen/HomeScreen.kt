package hu.bme.ait.highlowgame.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import hu.bme.ait.highlowgame.ui.navigation.GameScreenRoute

@Composable
fun HomeScreen(onStartClicked : () -> Unit ) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            modifier = Modifier.fillMaxWidth(0.5f),
            onClick = {
                onStartClicked()
            }) {
            Text("Start")
        }
        Button(
            modifier = Modifier.fillMaxWidth(0.5f),
            onClick = {

            }) {
            Text("Help")
        }
        Button(
            modifier = Modifier.fillMaxWidth(0.5f),
            onClick = {

            }) {
            Text("About")
        }
    }
}