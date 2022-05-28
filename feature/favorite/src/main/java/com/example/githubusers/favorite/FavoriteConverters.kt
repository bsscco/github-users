package com.example.githubusers.favorite

import com.example.githubusers.domain.user.favorite.Favorite

internal fun List<Favorite>.toFavoriteListState() = map { favorite ->
    FavoriteListContract.State.Favorite(
        userName = favorite.userName,
        avatarUrl = favorite.avatarUrl,
    )
}