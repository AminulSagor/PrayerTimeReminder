package com.somadhan.prayerrmindere.data.repository

import androidx.lifecycle.LiveData
import com.somadhan.prayerrmindere.data.database.PrayerDao
import com.somadhan.prayerrmindere.data.database.UserSettingsDao
import com.somadhan.prayerrmindere.data.models.PrayerResponse
import com.somadhan.prayerrmindere.data.models.UserSettings
import javax.inject.Inject

class PrayerRepository @Inject constructor(
    private val userSettingsDao: UserSettingsDao,
    private val prayerDao: PrayerDao
) {

    fun getUserSettings(): LiveData<UserSettings> {
        return userSettingsDao.getUserSettings()
    }

    suspend fun getUserSettingsSync(): UserSettings? {
        return userSettingsDao.getUserSettingsSync()
    }

    suspend fun insertUserSettings(settings: UserSettings) {
        userSettingsDao.insertUserSettings(settings)
    }

    suspend fun updateUserSettings(settings: UserSettings) {
        userSettingsDao.updateUserSettings(settings)
    }

    suspend fun recordPrayerResponse(prayerResponse: PrayerResponse) {
        prayerDao.insertPrayerResponse(prayerResponse)
    }

    suspend fun getPrayerResponsesForMonth(startOfMonth: Long, endOfMonth: Long): List<PrayerResponse> {
        // Use the DAO to get the responses from the database
        return prayerDao.getPrayerResponsesForMonth(startOfMonth, endOfMonth)
    }
}
