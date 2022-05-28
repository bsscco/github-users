package com.example.githubusers.local.user.search

import com.example.githubusers.data.user.favorite.DataFavorite
import com.example.githubusers.data.user.search.DataSearchUser
import com.example.githubusers.data.user.search.LocalSearchUser
import com.example.githubusers.data.user.search.SearchUserAndFavorite

internal fun SearchUserAndFavorite.toData(): Pair<DataSearchUser, DataFavorite?> {
    val (user, favorite) = this
    val dataUser = DataSearchUser(
        userName = user.userName,
        avatarUrl = user.avatarUrl,
    )
    val dataFavorite = favorite?.let {
        DataFavorite(
            userName = it.userName,
            avatarUrl = it.avatarUrl,
        )
    }
    return dataUser to dataFavorite
}

internal fun DataSearchUser.toLocal() = LocalSearchUser(
    userName = userName,
    avatarUrl = avatarUrl,
)