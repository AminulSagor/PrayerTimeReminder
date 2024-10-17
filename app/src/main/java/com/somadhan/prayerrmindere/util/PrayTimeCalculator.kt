package com.somadhan.prayerrmindere.util


import com.batoulapps.adhan2.CalculationMethod
import com.batoulapps.adhan2.Coordinates
import com.batoulapps.adhan2.PrayerTimes
import com.batoulapps.adhan2.data.DateComponents
import kotlinx.datetime.Clock
import javax.inject.Inject

class PrayTimeCalculator @Inject constructor() {
    fun getPrayerTimes(latitude: Double, longitude: Double): PrayerTimes {
        val coordinates = Coordinates(latitude, longitude)
        val params = CalculationMethod.MUSLIM_WORLD_LEAGUE.parameters
        val date = DateComponents.from(Clock.System.now())
        return PrayerTimes(coordinates, date, params)
    }
}
