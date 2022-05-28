package com.example.githubusers.data.user.search

import androidx.annotation.Keep
import androidx.room.Embedded
import androidx.room.Relation
import com.example.githubusers.data.user.favorite.LocalFavorite

@Keep
data class SearchUserAndFavorite(
    @Embedded val user: LocalSearchUser,
    @Relation(
        parentColumn = "userName",
        entityColumn = "userName",
    )
    val favorite: LocalFavorite?,
)