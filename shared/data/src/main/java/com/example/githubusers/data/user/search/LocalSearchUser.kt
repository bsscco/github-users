package com.example.githubusers.data.user.search

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(tableName = "searchUsers")
data class LocalSearchUser(
    @PrimaryKey val userName: String,
    val avatarUrl: String,
)