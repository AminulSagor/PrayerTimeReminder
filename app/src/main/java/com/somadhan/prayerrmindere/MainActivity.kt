package com.somadhan.prayerrmindere

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.somadhan.prayerrmindere.uiFile.screens.MainScreen
import com.somadhan.prayerrmindere.uiFile.screens.ReportScreen
import com.somadhan.prayerrmindere.uiFile.screens.SettingsScreen
import com.somadhan.prayerrmindere.uiFile.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialize ViewModel
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        // Check location permission when the activity is created
        checkLocationPermission()

        setContent {
            navController = rememberNavController()
            MainContent(navController = navController)
        }
    }

    // Register the permission launcher using the Activity Result API
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                // Permission is granted, navigate and refresh the main screen
                navController.navigate("main_screen") {
                    popUpTo("main_screen") { inclusive = true }
                }
            } else {
                // Handle the case where the permission is denied
            }
        }

    // Function to check and request location permissions
    private fun checkLocationPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                // Permission is already granted, reload the prayer times
                mainViewModel.loadPrayerTimes()
            }
            else -> {
                // Request the location permission
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }
}

@Composable
fun MainContent(navController: NavHostController) {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        NavHost(navController = navController, startDestination = "main_screen") {

            composable("main_screen") {
                MainScreen(navController = navController)
            }

            composable("settings_screen") {
                SettingsScreen(navController = navController)
            }

            composable("report_screen") {
                ReportScreen(navController = navController)
            }

        }
    }
}