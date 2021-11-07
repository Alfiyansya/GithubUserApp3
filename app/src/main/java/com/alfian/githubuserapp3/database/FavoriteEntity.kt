package com.alfian.githubuserapp3.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(
    tableName = "favorite"
)
@Parcelize
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int? = 0,
    @ColumnInfo(name = "username")
    var login: String? = null,
    @ColumnInfo(name = "photo")
    var avatar_url: String? = null
) : Parcelable