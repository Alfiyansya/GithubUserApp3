package com.alfian.githubuserapp3.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alfian.githubuserapp3.database.FavoriteEntity
import com.alfian.githubuserapp3.datasource.UserResponse
import com.alfian.githubuserapp3.networking.ApiConfig
import com.alfian.githubuserapp3.repository.FavoriteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DetailViewModel(username: String, app: Application) : ViewModel() {
    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(app)
    private val _userDetail = MutableLiveData<UserResponse?>()
    val detailUser: LiveData<UserResponse?> = _userDetail
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _isDataFailed = MutableLiveData<Boolean>()
    val isDataFailed: LiveData<Boolean> = _isDataFailed
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    init {
        viewModelScope.launch { getDetailUser(username) }
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
    private suspend fun getDetailUser(username: String) {
        coroutineScope.launch {
            _isLoading.value = true
            val getUserDetailDeferred = ApiConfig.getApiService().getDetailUserAsync(username)
            try {
                _isLoading.value = false
                _isDataFailed.value = false
                _userDetail.postValue(getUserDetailDeferred)
            } catch (e: Exception) {
                _isLoading.value = false
                _isDataFailed.value = true
                Log.e(TAG, "onFailure: ${e.message.toString()}")
            }
        }
    }

    companion object {
        private const val TAG = "DetailViewModel"
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}