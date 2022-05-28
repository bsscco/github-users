package com.example.githubusers.domain.user.search

data class SearchUser(
    val userName: String,
    val avatarUrl: String,
    val favorite: Boolean,
)