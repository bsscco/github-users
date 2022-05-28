package com.example.githubusers.data.user.favorite

import com.example.githubusers.domain.user.favorite.Favorite

internal fun DataFavorite.toDomain() = Favorite(
    userName = userName,
    avatarUrl = avatarUrl,
)
