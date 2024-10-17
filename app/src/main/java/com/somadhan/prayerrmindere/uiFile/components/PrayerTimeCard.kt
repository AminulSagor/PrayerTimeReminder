package com.somadhan.prayerrmindere.uiFile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.somadhan.prayerrmindere.data.models.Prayer
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun PrayerTimeCard(prayer: Prayer, onPrayerResponse: (String, String) -> Unit) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Row for prayer name and time
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = prayer.prayerName,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "Start: ${convertMillisToTime(prayer.startTime)} - End: ${convertMillisToTime(prayer.endTime)}",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Add buttons for Yes, No, and I Will
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {

                Button(
                    onClick = { onPrayerResponse(prayer.prayerName, "No") },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red, // Background color
                        contentColor = MaterialTheme.colorScheme.onSecondary  // Text color
                    )
                ) {
                    Text(text = "No")
                }
                Button(
                    onClick = { onPrayerResponse(prayer.prayerName, "Yes") },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary, // Background color
                        contentColor = MaterialTheme.colorScheme.onPrimary  // Text color
                    )
                ) {
                    Text(text = "Yes")
                }
                Button(
                    onClick = { onPrayerResponse(prayer.prayerName, "I Will") },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary, // Background color
                        contentColor = MaterialTheme.colorScheme.onTertiary  // Text color
                    )
                ) {
                    Text(text = "I will")
                }
            }

        }
    }
}

private fun convertMillisToTime(millis: Long): String {
    val formatter = SimpleDateFormat("hh:mm a", Locale.getDefault())
    return formatter.format(Date(millis))
}
