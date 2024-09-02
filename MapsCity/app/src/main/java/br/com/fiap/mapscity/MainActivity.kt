package br.com.fiap.mapscity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.fiap.mapscity.screen.FormScreen
import br.com.fiap.mapscity.screen.LoginPage
import br.com.fiap.mapscity.screen.ReportStatus
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = "loginPage") {

                composable("reportStatus") {
                    ReportStatus(navController)
                }
                composable("loginPage") {
                    LoginPage(navController)
                }
                composable("mapScreen") {
                    MapScreen(navController)
                }
                composable("formScreen") {
                    FormScreen(navController)
                }
            }
        }
    }
}

@Composable
fun MapScreen(navController: NavHostController) {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(LocalContext.current)
    val defaultLocation = LatLng(-23.5505, -46.6333) // São Paulo
    var userLocation by remember { mutableStateOf(defaultLocation) }
    val cameraPositionState = rememberCameraPositionState {
        position = com.google.android.gms.maps.model.CameraPosition.fromLatLngZoom(defaultLocation, 10f)
    }
    val mapUiSettings = remember { MapUiSettings(zoomControlsEnabled = false) }


    val context = LocalContext.current
    val locationPermissionGranted = remember { mutableStateOf(false) }


    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                locationPermissionGranted.value = true
                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    location?.let {
                        userLocation = LatLng(it.latitude, it.longitude)
                        cameraPositionState.position = com.google.android.gms.maps.model.CameraPosition.fromLatLngZoom(userLocation, 15f)
                    }
                }
            }
        }
    )


    if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
        locationPermissionGranted.value = true
    } else {
        LaunchedEffect(Unit) {
            launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }


    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = MapProperties(isMyLocationEnabled = locationPermissionGranted.value),
            uiSettings = mapUiSettings
        ) {
            Marker(state = rememberMarkerState(position = userLocation))
        }

        FloatingActionButton(
            onClick = {
                navController.navigate("formScreen")
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            content = {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Adicionar Denúncia")
            }
        )

        FloatingActionButton(
            onClick = {
                navController.navigate("reportStatus")
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(0.dp, 0.dp, 16.dp, 85.dp),
            content = {
                Icon(imageVector = Icons.Default.Info, contentDescription = "Adicionar Denúncia")
            }
        )




    }
}
