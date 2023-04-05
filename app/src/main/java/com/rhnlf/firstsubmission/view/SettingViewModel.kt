package com.rhnlf.firstsubmission.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.rhnlf.firstsubmission.helper.SettingPreferences
import kotlinx.coroutines.launch

class SettingViewModel(private val preferences: SettingPreferences) : ViewModel() {
    fun getThemeSetting(): LiveData<Boolean> = preferences.getThemeSetting().asLiveData()

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            preferences.saveThemeSetting(isDarkModeActive)
        }
    }
}