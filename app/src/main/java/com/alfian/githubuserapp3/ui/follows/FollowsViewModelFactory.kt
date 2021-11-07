package com.alfian.githubuserapp3.ui.follows

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FollowsViewModelFactory(private val username: String) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FollowsViewModel::class.java)) {
            return FollowsViewModel(username) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}