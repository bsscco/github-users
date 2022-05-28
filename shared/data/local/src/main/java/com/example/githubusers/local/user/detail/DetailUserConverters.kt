package com.example.githubusers.local.user.detail

import com.example.githubusers.data.user.detail.DataDetailUser
import com.example.githubusers.data.user.favorite.DataFavorite

internal fun DetailUserAndFavorite.toData(): Pair<DataDetailUser, DataFavorite?> {
    val (user, favorite) = this
    val dataUser = DataDetailUser(
        userName = user.userName,
        avatarUrl = user.avatarUrl,
        createdAt = user.createdAt,
    )
    val dataFavorite = favorite?.let {
        DataFavorite(
            userName = it.userName,
            avatarUrl = it.avatarUrl,
        )
    }
    return dataUser to dataFavorite
}

internal fun DataDetailUser.toLocal() = LocalDetailUser(
    userName = userName,
    avatarUrl = avatarUrl,
    createdAt = createdAt,
)