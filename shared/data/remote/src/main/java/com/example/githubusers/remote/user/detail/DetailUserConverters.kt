package com.example.githubusers.remote.user.detail

import com.example.githubusers.data.user.detail.DataDetailUser
import org.threeten.bp.ZonedDateTime

internal fun GetUserResponse.toData() = DataDetailUser(
    userName = login!!,
    avatarUrl = avatarUrl!!,
    createdAt = ZonedDateTime.parse(createdAt!!),
)