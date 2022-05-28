package com.example.githubusers.userdetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.githubusers.designsys.theme.GithubUsersTheme

@Composable
internal fun TopBar(
    state: UserDetailContract.State.TopBarState,
    onBackIconClicked: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            modifier = Modifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(bounded = false),
                    onClick = onBackIconClicked,
                )
                .padding(16.dp)
                .size(ButtonDefaults.IconSize),
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = null,
        )

        if (state is UserDetailContract.State.TopBarState.Bar) {
            Text(
                text = state.userName,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoadingPreview() {
    GithubUsersTheme {
        TopBar(
            state = UserDetailContract.State.TopBarState.Loading,
            onBackIconClicked = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    GithubUsersTheme {
        TopBar(
            state = UserDetailContract.State.TopBarState.Bar(
                userName = "bsscco",
            ),
            onBackIconClicked = {},
        )
    }
}