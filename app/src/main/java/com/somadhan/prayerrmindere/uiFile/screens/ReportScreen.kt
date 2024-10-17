package com.somadhan.prayerrmindere.uiFile.screens

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.somadhan.prayerrmindere.data.models.PrayerResponse
import com.somadhan.prayerrmindere.uiFile.viewmodels.ReportViewModel
import com.somadhan.prayerrmindere.util.PrayerTimeHelper

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportScreen(viewModel: ReportViewModel = hiltViewModel(), navController: NavHostController) {
    val prayerTimeHelper = PrayerTimeHelper() // Create instance of the helper

    val responses = viewModel.getPrayerResponsesForMonth(
        prayerTimeHelper.getStartOfMonth(),
        prayerTimeHelper.getEndOfMonth()
    ).observeAsState(initial = emptyList())

    // Prepare the data for the graph
    val entries = responses.value.mapIndexed { index: Int, response: PrayerResponse ->
        BarEntry(index.toFloat(), if (response.response == "Yes") 1f else 0f) // Example
    }

    // Prepare the dataset
    val dataSet = BarDataSet(entries, "Prayer Responses")
    val barData = BarData(dataSet)

    // Main UI with TopAppBar and content
    Column {
        // Add a TopAppBar with a back button
        TopAppBar(
            title = { Text("Prayer Responses") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) { // Navigate back
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        )

        // Display the graph
        if (entries.isNotEmpty()) {
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { context: Context ->
                    BarChart(context).apply {
                        data = barData
                        invalidate() // Refresh the chart with new data
                    }
                }
            )
        } else {
            Text("No data for this month")
        }
    }
}
