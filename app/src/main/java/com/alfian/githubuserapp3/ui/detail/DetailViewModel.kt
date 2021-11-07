package com.alfian.githubuserapp3.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alfian.githubuserapp3.database.FavoriteEntity
import com.alfian.githubuserapp3.datasource.UserResponse
import com.alfian.githubuserapp3.repository.FavoriteRepository
import com.alfian.githubuserapp3.repository.UserRepository
import kotlinx.coroutines.launch

class DetailViewModel(username: String, app: Application) : ViewModel() {
    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(app)
    val detailUser: LiveData<UserResponse?> = UserRepository.userDetail
    val isLoading: LiveData<Boolean> = UserRepository.isLoading
    val isNoInternet: LiveData<Boolean> = UserRepository.isNoInternet
    val isDataFailed: LiveData<Boolean> = UserRepository.isDataFailed

    init {
        viewModelScope.launch { UserRepository.getDetailUser(username) }
        Log.i(TAG, "DetailViewModel is Created")

    }

    fun insert(favEntity: FavoriteEntity) {
        mFavoriteRepository.insert(favEntity)
    }

    fun delete(favEntity: FavoriteEntity) {
        mFavoriteRepository.delete(favEntity)
    }

    fun getFavoriteById(id: Int): LiveData<List<FavoriteEntity>> {
        return mFavoriteRepository.getUserFavoriteById(id)
    }

    companion object {
        private const val TAG = "DetailViewModel"
    }

}