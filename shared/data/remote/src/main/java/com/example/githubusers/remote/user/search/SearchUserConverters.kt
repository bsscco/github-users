package com.example.githubusers.remote.user.search

import com.example.githubusers.data.user.search.DataSearchUser

internal fun SearchUsersResponse.toData(): List<DataSearchUser> {
    return items.map { item ->
        DataSearchUser(
            userName = item.login,
            avatarUrl = item.avatarUrl,
        )
    }
}