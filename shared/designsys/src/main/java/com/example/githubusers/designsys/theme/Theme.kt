package com.example.githubusers.designsys.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun GithubUsersTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    MaterialTheme(
//        colors = colors,
//        typography = Typography,
//        shapes = Shapes,
        content = content
    )
}