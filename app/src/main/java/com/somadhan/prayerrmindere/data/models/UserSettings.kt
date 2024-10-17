package com.somadhan.prayerrmindere.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_settings")
data class UserSettings(
    @PrimaryKey val id: Int = 1, // Single entry for user settings
    val is24HourFormat: Boolean = false,
    val reminderTime: Int = 20, // Default reminder time
    val notifyFajr: Boolean = true,
    val notifyDhuhr: Boolean = true,
    val notifyAsr: Boolean = true,
    val notifyMaghrib: Boolean = true,
    val notifyIsha: Boolean = true
)
