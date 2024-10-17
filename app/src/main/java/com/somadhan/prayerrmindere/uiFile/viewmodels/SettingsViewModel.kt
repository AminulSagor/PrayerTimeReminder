package com.somadhan.prayerrmindere.uiFile.viewmodels


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.somadhan.prayerrmindere.data.models.UserSettings
import com.somadhan.prayerrmindere.data.repository.PrayerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: PrayerRepository
) : ViewModel() {

    val userSettings: LiveData<UserSettings> = repository.getUserSettings()

    init {
        viewModelScope.launch {
            val currentSettings = repository.getUserSettingsSync()
            if (currentSettings == null) {
                // Insert default settings if none exist
                repository.insertUserSettings(UserSettings(
                    is24HourFormat = false,
                    reminderTime = 20,
                    notifyFajr = true,
                    notifyDhuhr = true,
                    notifyAsr = true,
                    notifyMaghrib = true,
                    notifyIsha = true
                ))
            }
        }
    }

    fun updateUserSettings(settings: UserSettings) {
        viewModelScope.launch {
            repository.updateUserSettings(settings)
        }
    }
}
