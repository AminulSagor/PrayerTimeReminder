package com.somadhan.prayerrmindere.uiFile.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.batoulapps.adhan2.SunnahTimes
import com.somadhan.prayerrmindere.data.models.Prayer
import com.somadhan.prayerrmindere.data.models.PrayerResponse
import com.somadhan.prayerrmindere.data.repository.PrayerRepository
import com.somadhan.prayerrmindere.util.LocationProvider
import com.somadhan.prayerrmindere.util.PrayTimeCalculator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val locationProvider: LocationProvider,
    private val prayerTimeCalculator: PrayTimeCalculator,
    private val prayerRepository: PrayerRepository
) : ViewModel() {

    private val _prayerTimes = MutableLiveData<List<Prayer>>()
    val prayerTimes: LiveData<List<Prayer>> = _prayerTimes

    fun loadPrayerTimes() {
        locationProvider.getCurrentLocation(
            onLocationRetrieved = { location ->
                val latitude = location.latitude
                val longitude = location.longitude


                val prayerTimes = prayerTimeCalculator.getPrayerTimes(latitude, longitude)
                Log.d("MainViewModel", prayerTimes.toString())

                val prayers = listOf(
                    Prayer(prayerName = "Fajr", startTime = convertTimeToMillis(prayerTimes.fajr), endTime = convertTimeToMillis(prayerTimes.sunrise)),
                    Prayer(prayerName = "Dhuhr", startTime = convertTimeToMillis(prayerTimes.dhuhr), endTime = convertTimeToMillis(prayerTimes.asr)),
                    Prayer(prayerName = "Asr", startTime = convertTimeToMillis(prayerTimes.asr), endTime = convertTimeToMillis(prayerTimes.maghrib)),
                    Prayer(prayerName = "Maghrib", startTime = convertTimeToMillis(prayerTimes.maghrib), endTime = convertTimeToMillis(prayerTimes.isha)),
                    Prayer(prayerName = "Isha", startTime = convertTimeToMillis(prayerTimes.isha), endTime = convertTimeToMillis(SunnahTimes(prayerTimes).middleOfTheNight))
                )

                _prayerTimes.value = prayers
            },
            onFailure = {
                Log.d("MainViewModel", "Failed to retrieve location")
            }
        )
    }

    fun recordPrayerResponse(prayerName: String, response: String) {
        // Create a PrayerResponse object
        val prayerResponse = PrayerResponse(
            prayerName = prayerName,
            response = response,
            // Include any other fields needed for the PrayerResponse object
            timestamp = System.currentTimeMillis() // Example: storing the time of the response
        )

        // Insert response into your repository (Room Database)
        viewModelScope.launch {
            prayerRepository.recordPrayerResponse(prayerResponse)
        }
    }

}

private fun convertTimeToMillis(time: Instant?): Long {
        return time?.toEpochMilliseconds() ?: 0L
    }
