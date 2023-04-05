package com.rhnlf.firstsubmission.helper

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rhnlf.firstsubmission.data.Repository
import com.rhnlf.firstsubmission.di.Injection
import com.rhnlf.firstsubmission.view.DetailViewModel
import com.rhnlf.firstsubmission.view.FavoriteViewModel
import com.rhnlf.firstsubmission.view.FollowViewModel
import com.rhnlf.firstsubmission.view.HomeViewModel

class ViewModelFactory private constructor(private val repository: Repository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> return HomeViewModel(
                repository
            ) as T
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> return FavoriteViewModel(
                repository
            ) as T
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> return DetailViewModel(
                repository
            ) as T
            modelClass.isAssignableFrom(FollowViewModel::class.java) -> return FollowViewModel(
                repository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory = instance ?: synchronized(this) {
            instance ?: ViewModelFactory(Injection.provideRepository(context))
        }.also { instance = it }
    }
}