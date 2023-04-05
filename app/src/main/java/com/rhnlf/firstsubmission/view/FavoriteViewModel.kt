package com.rhnlf.firstsubmission.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.rhnlf.firstsubmission.data.Repository
import com.rhnlf.firstsubmission.data.remote.response.UserResponse

class FavoriteViewModel(private val repository: Repository) : ViewModel() {
    fun getAllChanges(): LiveData<List<UserResponse>> = repository.getAllFavorite()
}