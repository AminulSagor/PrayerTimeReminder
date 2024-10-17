package com.somadhan.prayerrmindere.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "prayer")
data class Prayer(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // Changed id to Int
    val prayerName: String,
    val startTime: Long, // Using Long to store time in milliseconds
    val endTime: Long    // Using Long to store time in milliseconds
)
