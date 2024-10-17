package com.somadhan.prayerrmindere.uiFile.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.somadhan.prayerrmindere.uiFile.viewmodels.SettingsViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavHostController, // Pass navController here
    viewModel: SettingsViewModel = hiltViewModel()
) {
    // Observe user settings and check for null
    val settings by viewModel.userSettings.observeAsState(null) // Initially set to null

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Settings") },
                navigationIcon = {
                    IconButton(onClick = {
                        // Navigate back to the main screen when the back button is clicked
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        // Check if settings are not null
        settings?.let { userSettings ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {
                Spacer(modifier = Modifier.height(50.dp))

                // Toggle for 24-hour time format
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Use 24-hour time format")
                    Switch(
                        checked = userSettings.is24HourFormat,
                        onCheckedChange = {
                            viewModel.updateUserSettings(userSettings.copy(is24HourFormat = it))
                        }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Reminder time slider
                Text("Set Reminder before Salah time end")
                Slider(
                    value = userSettings.reminderTime.toFloat(),
                    onValueChange = {
                        viewModel.updateUserSettings(userSettings.copy(reminderTime = it.toInt()))
                    },
                    valueRange = 5f..30f, // Reminders range from 5 to 30 minutes
                    steps = 5
                )
                Text("${userSettings.reminderTime} minutes before Salah")

                Spacer(modifier = Modifier.height(16.dp))

                // Notification settings for each prayer
                listOf(
                    "Fajr" to userSettings.notifyFajr,
                    "Dhuhr" to userSettings.notifyDhuhr,
                    "Asr" to userSettings.notifyAsr,
                    "Maghrib" to userSettings.notifyMaghrib,
                    "Isha" to userSettings.notifyIsha
                ).forEach { (prayerName, isEnabled) ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(prayerName)
                        Switch(
                            checked = isEnabled,
                            onCheckedChange = {
                                val updatedSettings = when (prayerName) {
                                    "Fajr" -> userSettings.copy(notifyFajr = it)
                                    "Dhuhr" -> userSettings.copy(notifyDhuhr = it)
                                    "Asr" -> userSettings.copy(notifyAsr = it)
                                    "Maghrib" -> userSettings.copy(notifyMaghrib = it)
                                    "Isha" -> userSettings.copy(notifyIsha = it)
                                    else -> userSettings
                                }
                                viewModel.updateUserSettings(updatedSettings)
                            }
                        )
                    }
                }
            }
        } ?: run {
            Text("Loading settings...", modifier = Modifier.padding(16.dp))
        }
    }
}