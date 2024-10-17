package com.somadhan.prayerrmindere.uiFile.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.somadhan.prayerrmindere.data.repository.PrayerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(private val repository: PrayerRepository) : ViewModel() {

    fun getPrayerResponsesForMonth(startOfMonth: Long, endOfMonth: Long) = liveData {
        val data = repository.getPrayerResponsesForMonth(startOfMonth, endOfMonth)
        emit(data)
    }
}