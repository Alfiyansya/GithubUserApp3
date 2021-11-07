package com.alfian.githubuserapp3.adapter

import com.alfian.githubuserapp3.datasource.UserResponse

interface OnItemClickCallback {
    fun onItemClicked(user: UserResponse)
}