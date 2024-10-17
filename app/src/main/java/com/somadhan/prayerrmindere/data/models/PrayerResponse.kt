package com.somadhan.prayerrmindere.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "prayer_response")
data class PrayerResponse(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val prayerName: String,
    val response: String, // Either "Yes", "No", or "I Will"
    val timestamp: Long = System.currentTimeMillis()
)
