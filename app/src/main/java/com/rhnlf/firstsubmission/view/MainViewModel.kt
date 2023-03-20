package com.rhnlf.firstsubmission.view

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rhnlf.firstsubmission.api.ApiConfig
import com.rhnlf.firstsubmission.data.GithubResponse
import com.rhnlf.firstsubmission.data.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _listGithub = MutableLiveData<List<User>>()
    val listGithub: LiveData<List<User>> = _listGithub

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "MainViewModel"
    }

    fun getGithub(username: String) {
        showLoading(true)
        val client = ApiConfig.getApiService().getGithub(username)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    _listGithub.value = response.body()?.items
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun clearUserList() {
        _listGithub.value = emptyList()
    }

    fun showLoading(state: Boolean) {
        _isLoading.value = state
    }
}