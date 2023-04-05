package com.rhnlf.firstsubmission.di

import android.content.Context
import com.rhnlf.firstsubmission.data.Repository
import com.rhnlf.firstsubmission.data.local.FavDB
import com.rhnlf.firstsubmission.data.remote.api.ApiConfig

object Injection {
    fun provideRepository(context: Context): Repository {
        val apiService = ApiConfig.getApiService()
        val database = FavDB.getDatabase(context)
        val dao = database.favDao()
        return Repository.getInstance(apiService, dao)
    }
}