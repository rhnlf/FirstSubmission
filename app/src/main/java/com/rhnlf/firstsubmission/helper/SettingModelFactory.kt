package com.rhnlf.firstsubmission.helper

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rhnlf.firstsubmission.view.SettingViewModel

class SettingModelFactory(private val preferences: SettingPreferences) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingViewModel::class.java)) {
            return SettingViewModel(preferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}