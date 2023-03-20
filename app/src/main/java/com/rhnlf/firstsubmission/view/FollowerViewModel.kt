package com.rhnlf.firstsubmission.view

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rhnlf.firstsubmission.api.ApiConfig
import com.rhnlf.firstsubmission.data.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowerViewModel : ViewModel() {

    companion object {
        private const val TAG = "FollowerViewModel"
    }

    private val _followerList = MutableLiveData<List<User>>()
    val followerList: LiveData<List<User>> = _followerList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getFollower(username: String?) {
        showLoading(true)
        val client = ApiConfig.getApiService().getFollower(username)
        client.enqueue(object : Callback<List<User>> {
            override fun onResponse(
                call: Call<List<User>>,
                response: Response<List<User>>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    _followerList.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun showLoading(state: Boolean) {
        _isLoading.value = state
    }
}