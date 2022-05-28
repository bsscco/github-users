package com.example.githubusers.designsys.transition

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun Dim(
    modifier: Modifier = Modifier,
    visible: Boolean,
    content: @Composable BoxScope.() -> Unit = {},
) {
    FadeIn(
        modifier = modifier,
        visible = visible,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable(enabled = false, onClick = {})
                .background(color = Color.Black.copy(alpha = 0.3f)),
        ) {
            content()
        }
    }
}