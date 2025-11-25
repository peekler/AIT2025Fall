package hu.bme.ait.mapsdemo.ui.screen

import android.location.Location
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.MarkerState.Companion.invoke
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.ktx.model.cameraPosition
import kotlin.collections.emptyList

@Composable
fun MyMapScreen(modifier: Modifier = Modifier,
    viewModel: MyMapViewModel = viewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    var cameraState = rememberCameraPositionState {
        CameraPosition.fromLatLngZoom(
            LatLng(47.0, 19.0), 10f
        )
    }
    var uiSettings by remember {
        mutableStateOf(
            MapUiSettings(
                zoomControlsEnabled = true,
                zoomGesturesEnabled = true
            )
        )
    }
    var mapProperties by remember {
        mutableStateOf(
            MapProperties(
                mapType = MapType.SATELLITE,
                isTrafficEnabled = true
            )
        )
    }



    Column(
        modifier = modifier
    ) {
        var isSatellite by remember { mutableStateOf(true) }
        Switch(
            checked = isSatellite,
            onCheckedChange = {
                isSatellite = it
                mapProperties = mapProperties.copy(
                    mapType = if (it) MapType.SATELLITE else MapType.NORMAL
                )
            }
        )



        GoogleMap(
            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.5f),
            cameraPositionState = cameraState,
            uiSettings = uiSettings,
            properties = mapProperties,
            onMapClick = {
                coordinate ->
                    viewModel.addMarkerPosition(coordinate)
            }
        ) {
            var markerState by remember { mutableStateOf(
                MarkerState(position = LatLng(47.0, 19.0))) }

            Marker(
                state = markerState,
                title = "Marker AIT",
                snippet = "Marker long text, lorem ipsum...",
                draggable = true,
                alpha = 0.5f
            )

            for (markerPosition in viewModel.getMarkersList()) {
                var markerStateTmp by remember { mutableStateOf(
                    MarkerState(position = markerPosition)) }

                Marker(
                    state = markerStateTmp,
                    title = "Marker",
                    snippet = "Coord: " +
                            "${markerPosition.latitude}," +
                            "${markerPosition.longitude}"
                )
            }

        }
    }
}