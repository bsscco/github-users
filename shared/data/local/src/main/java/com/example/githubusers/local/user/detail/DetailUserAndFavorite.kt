package com.example.githubusers.local.user.detail

import androidx.annotation.Keep
import androidx.room.Embedded
import androidx.room.Relation
import com.example.githubusers.data.user.favorite.LocalFavorite

@Keep
internal data class DetailUserAndFavorite(
    @Embedded val user: LocalDetailUser,
    @Relation(
        parentColumn = "userName",
        entityColumn = "userName",
    )
    val favorite: LocalFavorite?,
)