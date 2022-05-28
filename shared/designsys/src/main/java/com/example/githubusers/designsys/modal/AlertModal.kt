package com.example.githubusers.designsys.modal

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.githubusers.aosutil.dimention.textDp
import com.example.githubusers.aosutil.focus.FocusCleared
import com.example.githubusers.designsys.theme.GithubUsersTheme
import com.example.githubusers.designsys.transition.FadeIn

@Composable
fun AlertModal(
    modifier: Modifier = Modifier,
    state: AlertModal.State,
    onEvent: (AlertModal.Event) -> Unit,
) {
    BackHandler(enabled = state.visible, onBack = {})

    FadeIn(visible = state.visible) {
        FocusCleared {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f))
                    .clickable(enabled = false, onClick = {})
                    .padding(horizontal = 32.dp),
                contentAlignment = Alignment.Center,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.White)
                        .padding(16.dp),
                ) {
                    if (state.title != null) {
                        Text(
                            text = state.title,
                            color = Color.Black,
                            fontSize = 18.textDp,
                            fontWeight = FontWeight.Bold,
                        )
                        Spacer(Modifier.height(16.dp))
                    }

                    Text(
                        lineHeight = 24.textDp,
                        text = state.content,
                        color = Color.Black,
                        fontSize = 16.textDp,
                    )

                    Spacer(Modifier.height(40.dp))

                    Box(
                        modifier = Modifier
                            .align(Alignment.End)
                            .wrapContentWidth()
                            .height(40.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color.Black)
                            .clickable { onEvent(AlertModal.Event.OnPrimaryClick) }
                            .padding(horizontal = 16.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = state.primaryButtonText,
                            color = Color.White,
                            fontSize = 14.textDp,
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TitlePreview() {
    GithubUsersTheme {
        AlertModal(
            state = AlertModal.State(
                visible = true,
                title = "Title",
                content = "Content",
                primaryButtonText = "Primary action",
            ),
            onEvent = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun NoTitlePreview() {
    GithubUsersTheme {
        AlertModal(
            state = AlertModal.State(
                visible = true,
                content = "Content",
                primaryButtonText = "Primary action",
            ),
            onEvent = {}
        )
    }
}