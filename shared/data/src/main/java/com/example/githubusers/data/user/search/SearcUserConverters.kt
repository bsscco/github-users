package com.example.githubusers.data.user.search

import com.example.githubusers.domain.user.search.SearchUser

internal fun SearchUserAndFavorite.toDomain(): SearchUser {
    val (user, favorite) = this
    return SearchUser(
        userName = user.userName,
        avatarUrl = user.avatarUrl,
        favorite = favorite != null,
    )
}