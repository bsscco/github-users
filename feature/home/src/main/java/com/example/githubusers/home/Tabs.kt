package com.example.githubusers.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.githubusers.designsys.theme.GithubUsersTheme

@Composable
internal fun Tabs(
    selectedTab: HomeContract.State.Tab,
    onTabClicked: (HomeContract.State.Tab) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .clickable { onTabClicked(HomeContract.State.Tab.USER_SEARCH) },
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "UserSearch",
                color = Color.Black,
                fontWeight = if (selectedTab == HomeContract.State.Tab.USER_SEARCH) FontWeight.Bold else FontWeight.Normal,
            )
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .clickable { onTabClicked(HomeContract.State.Tab.FAVORITE) },
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "Favorite",
                color = Color.Black,
                fontWeight = if (selectedTab == HomeContract.State.Tab.FAVORITE) FontWeight.Bold else FontWeight.Normal,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ListTabSelectedPreview() {
    GithubUsersTheme {
        Tabs(
            selectedTab = HomeContract.State.Tab.USER_SEARCH,
            onTabClicked = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun FavoriteTabSelectedPreview() {
    GithubUsersTheme {
        Tabs(
            selectedTab = HomeContract.State.Tab.FAVORITE,
            onTabClicked = {},
        )
    }
}