package com.somadhan.prayerrmindere.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.somadhan.prayerrmindere.data.models.Prayer
import com.somadhan.prayerrmindere.data.models.PrayerResponse

@Dao
interface PrayerDao {

    @Insert
    suspend fun insertPrayer(prayer: Prayer)

    @Query("SELECT * FROM prayer")
    suspend fun getAllPrayers(): List<Prayer>

    @Query("DELETE FROM prayer")
    suspend fun deleteAllPrayers()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPrayerResponse(prayerResponse: PrayerResponse)

    @Query("SELECT * FROM prayer_response WHERE prayerName = :prayerName")
    suspend fun getPrayerResponse(prayerName: String): PrayerResponse

    @Query("SELECT * FROM prayer_response WHERE timestamp BETWEEN :startOfMonth AND :endOfMonth")
    suspend fun getPrayerResponsesForMonth(startOfMonth: Long, endOfMonth: Long): List<PrayerResponse>
}