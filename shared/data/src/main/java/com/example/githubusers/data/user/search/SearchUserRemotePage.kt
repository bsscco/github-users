package com.example.githubusers.data.user.search

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "searchUserRemotePage")
data class SearchUserRemotePage(
    @PrimaryKey
    val nextPage: Int,
)