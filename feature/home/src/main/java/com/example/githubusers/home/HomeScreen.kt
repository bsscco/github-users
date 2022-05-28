package com.example.githubusers.home

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.githubusers.designsys.divider.ColumnDivider
import com.example.githubusers.designsys.theme.GithubUsersTheme
import com.example.githubusers.favorite.FavoriteList
import com.example.githubusers.usersearch.UserSearch

@Composable
fun HomeScreen() {
    val viewModel = hiltViewModel<HomeViewModel>()
    val context = LocalContext.current

    LaunchedEffect(true) {
        viewModel.effectFlow.collect { effect ->
            when (effect) {
                is HomeContract.Effect.FinishApp -> (context as Activity).finish()
            }
        }
    }

    HomeScreen(
        state = viewModel.stateFlow.collectAsState().value,
        onEvent = viewModel.eventHandler,
    )
}

@Composable
private fun HomeScreen(
    state: HomeContract.State,
    onEvent: (HomeContract.Event) -> Unit,
) {
    BackHandler(
        enabled = true,
        onBack = { onEvent(HomeContract.Event.onBackKeyClicked) },
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        ) {
            UserSearch(isVisible = (state.selectedTab == HomeContract.State.Tab.USER_SEARCH))
            FavoriteList(isVisible = (state.selectedTab == HomeContract.State.Tab.FAVORITE))
        }

        ColumnDivider()

        Tabs(
            selectedTab = state.selectedTab,
            onTabClicked = { tab -> onEvent(HomeContract.Event.OnTabClicked(tab)) },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun UserSearchTabPreview() {
    GithubUsersTheme {
        HomeScreen(
            state = HomeContract.State(
                selectedTab = HomeContract.State.Tab.USER_SEARCH,
            ),
            onEvent = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun FavoriteTabPreview() {
    GithubUsersTheme {
        HomeScreen(
            state = HomeContract.State(
                selectedTab = HomeContract.State.Tab.FAVORITE,
            ),
            onEvent = {},
        )
    }
}