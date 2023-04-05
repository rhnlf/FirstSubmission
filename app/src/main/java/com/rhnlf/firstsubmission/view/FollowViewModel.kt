package com.rhnlf.firstsubmission.view

import androidx.lifecycle.ViewModel
import com.rhnlf.firstsubmission.data.Repository

class FollowViewModel(private val repository: Repository) : ViewModel() {
    fun getFollower(name: String) = repository.followerUser(name)

    fun getFollowing(name: String) = repository.followingUser(name)
}