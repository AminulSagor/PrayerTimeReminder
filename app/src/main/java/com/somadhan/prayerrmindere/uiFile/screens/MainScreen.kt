package com.somadhan.prayerrmindere.uiFile.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.somadhan.prayerrmindere.uiFile.components.PrayerTimeCard
import com.somadhan.prayerrmindere.uiFile.viewmodels.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: MainViewModel = hiltViewModel(), navController: NavHostController) {
    LaunchedEffect(Unit) {
        viewModel.loadPrayerTimes()
    }

    val prayerTimes by viewModel.prayerTimes.observeAsState(emptyList())


    androidx.compose.material3.Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Today's Prayer Times") },
                actions = {
                    IconButton(onClick = {
                        navController.navigate("settings_screen")
                    }) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings",
                            tint = androidx.compose.ui.graphics.Color.Black, // Set to a contrasting color
                            modifier = Modifier.size(24.dp) // Adjust the size if necessary
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(8.dp)
        ) {
            // Iterate through the list of prayer times
            prayerTimes.forEach { prayer ->
                PrayerTimeCard(prayer = prayer) { prayerName, response ->
                    viewModel.recordPrayerResponse(prayerName, response)
                }
                Spacer(modifier = Modifier.height(1.dp))
            }
            Spacer(modifier = Modifier.weight(1f)) // Fills available space
            TextButton(
                onClick = {
                    navController.navigate("report_screen") // Navigating to the ReportScreen
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = "View Monthly Report")
            }
        }

    }
}

