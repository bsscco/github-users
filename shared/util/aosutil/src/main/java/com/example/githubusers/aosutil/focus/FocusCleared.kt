package com.example.githubusers.aosutil.focus

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalFocusManager

@Composable
fun FocusCleared(content: @Composable () -> Unit) {
    val focusManager = LocalFocusManager.current
    LaunchedEffect(true) {
        focusManager.clearFocus(force = true)
    }

    content()
}