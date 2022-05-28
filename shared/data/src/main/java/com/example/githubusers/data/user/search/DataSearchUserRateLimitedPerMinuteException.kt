package com.example.githubusers.data.user.search

class DataSearchUserRateLimitedPerMinuteException(
    override val message: String,
) : Exception(message)