package com.example.githubusers.data.user.favorite

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(tableName = "favorites")
data class LocalFavorite(
    @PrimaryKey val userName: String,
    val avatarUrl: String,
)