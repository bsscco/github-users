package com.example.githubusers.data.user.detail

import org.threeten.bp.ZonedDateTime

data class DataDetailUser(
    val userName: String,
    val avatarUrl: String,
    val createdAt: ZonedDateTime,
)