package com.example.githubusers.local.user.favorite

import com.example.githubusers.data.user.favorite.DataFavorite
import com.example.githubusers.data.user.favorite.LocalFavorite

internal fun LocalFavorite.toData() = DataFavorite(
    userName = userName,
    avatarUrl = avatarUrl,
)