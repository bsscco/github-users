package com.example.githubusers.usersearch

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.githubusers.designsys.theme.GithubUsersTheme

@Composable
fun UserItem(
    userName: String,
    avatarUrl: String,
    favorite: Boolean,
    onUserItemClicked: () -> Unit,
    onFavoriteIconClicked: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clickable(onClick = onUserItemClicked),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Spacer(Modifier.width(16.dp))

        AsyncImage(
            modifier = Modifier
                .size(ButtonDefaults.IconSize * 2)
                .clip(CircleShape)
                .background(Color.LightGray),
            model = avatarUrl,
            contentDescription = null,
        )

        Spacer(Modifier.width(8.dp))

        Text(text = userName)

        Spacer(Modifier.weight(1f))

        Image(
            modifier = Modifier
                .wrapContentWidth()
                .fillMaxHeight()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(bounded = false),
                    onClick = onFavoriteIconClicked,
                )
                .padding(horizontal = 16.dp)
                .size(ButtonDefaults.IconSize),
            imageVector = if (favorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
            contentDescription = null,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun FavoritePreview() {
    GithubUsersTheme {
        UserItem(
            userName = "이름",
            avatarUrl = "",
            favorite = true,
            onUserItemClicked = {},
            onFavoriteIconClicked = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun NotFavoritePreview() {
    GithubUsersTheme {
        UserItem(
            userName = "이름",
            avatarUrl = "",
            favorite = false,
            onUserItemClicked = {},
            onFavoriteIconClicked = {},
        )
    }
}