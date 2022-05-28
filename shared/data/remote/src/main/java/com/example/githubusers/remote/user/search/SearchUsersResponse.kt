package com.example.githubusers.remote.user.search

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
internal data class SearchUsersResponse(
    @SerializedName("total_count")
    val totalCount: Int,
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean,
    @SerializedName("items")
    val items: List<Item>,
) {
    @Keep
    data class Item(
        @SerializedName("login")
        val login: String,
        @SerializedName("avatar_url")
        val avatarUrl: String,
    )
}