package com.example.githubusers.data.user.detail

import com.example.githubusers.data.user.favorite.DataFavorite
import com.example.githubusers.domain.user.detail.DetailUser

internal fun Pair<DataDetailUser, DataFavorite?>.toDomain(): DetailUser {
    val (user, favorite) = this
    return DetailUser(
        userName = user.userName,
        avatarUrl = user.avatarUrl,
        createdAt = user.createdAt,
        favorite = favorite != null,
    )
}