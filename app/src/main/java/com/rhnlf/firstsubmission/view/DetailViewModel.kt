package com.rhnlf.firstsubmission.view

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rhnlf.firstsubmission.api.ApiConfig
import com.rhnlf.firstsubmission.data.DetailUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {

    companion object {
        private const val TAG = "DetailViewModel"
    }

    private val _detail = MutableLiveData<DetailUser>()
    val detail: LiveData<DetailUser> = _detail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getDetail(username: String) {
        showLoading(true)
        val client = ApiConfig.getApiService().getDetail(username)
        client.enqueue(object : Callback<DetailUser> {
            override fun onResponse(call: Call<DetailUser>, response: Response<DetailUser>) {
                showLoading(false)
                if (response.isSuccessful) {
                    _detail.value = response.body()
                }
            }

            override fun onFailure(call: Call<DetailUser>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun showLoading(state: Boolean) {
        _isLoading.value = state
    }
}





