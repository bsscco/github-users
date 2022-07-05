package com.example.githubusers.userdetail

import com.example.githubusers.domain.user.detail.DetailUser
import org.threeten.bp.format.DateTimeFormatter

internal fun DetailUser.toTopBarState() = UserDetailContract.State.TopBarState.Bar(
    userName = userName,
)

internal fun DetailUser.toContentState() = UserDetailContract.State.ContentState.Content(
    avatarUrl = avatarUrl,
    createdAt = createdAt.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")),
    favorite = favorite,
)