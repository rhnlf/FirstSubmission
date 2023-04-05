package com.rhnlf.firstsubmission.view

import androidx.lifecycle.ViewModel
import com.rhnlf.firstsubmission.data.Repository

class HomeViewModel(private val repository: Repository) : ViewModel() {
    fun searchUser(githubName: String) = repository.findUser(githubName)
}