package com.somadhan.prayerrmindere.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.somadhan.prayerrmindere.data.models.Prayer
import com.somadhan.prayerrmindere.data.models.PrayerResponse
import com.somadhan.prayerrmindere.data.models.UserSettings

@Database(entities = [Prayer::class, PrayerResponse::class, UserSettings::class], version = 2)
abstract class PrayerDatabase : RoomDatabase() {
    abstract fun prayerDao(): PrayerDao
    abstract fun userSettingsDao(): UserSettingsDao
}