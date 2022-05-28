package com.example.githubusers.domain.user.detail

import org.threeten.bp.ZonedDateTime

data class DetailUser(
    val userName: String,
    val avatarUrl: String,
    val createdAt: ZonedDateTime,
    val favorite: Boolean,
)