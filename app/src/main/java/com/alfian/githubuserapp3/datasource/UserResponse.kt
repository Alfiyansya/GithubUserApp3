package com.alfian.githubuserapp3.datasource

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserResponse(
    val id: Int = 0,
    val name: String?,
    val login: String?,
    @field:SerializedName("avatar_url")
    val avatarUrl: String?,
    val bio: String?,
    val company: String?,
    val location: String?,
    val blog: String?,
    @field:SerializedName("public_repos")
    val publicRepo: String?,
    val followers: String?,
    val following: String?,
    @field:SerializedName("followers_url")
    val followersUrl: String?,
    @field:SerializedName("following_url")
    val followingUrl: String?
) : Parcelable