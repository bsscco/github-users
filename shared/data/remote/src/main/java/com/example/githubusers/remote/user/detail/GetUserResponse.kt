package com.example.githubusers.remote.user.detail

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
internal data class GetUserResponse(
    @SerializedName("message")
    val message: String?,
    @SerializedName("login")
    val login: String?,
    @SerializedName("avatar_url")
    val avatarUrl: String?,
    @SerializedName("created_at")
    val createdAt: String?,
)