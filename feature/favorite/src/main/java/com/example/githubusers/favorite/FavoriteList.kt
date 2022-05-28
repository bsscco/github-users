package com.example.githubusers.favorite

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.githubusers.aosutil.navigation.LocalNavController
import com.example.githubusers.aosutil.toast.showToast
import com.example.githubusers.designsys.divider.ColumnDivider
import com.example.githubusers.designsys.theme.GithubUsersTheme
import com.example.githubusers.designsys.transition.FadeIn
import com.example.githubusers.navigation.Uris
import com.example.githubusers.usersearch.UserItem

@Composable
fun FavoriteList(isVisible: Boolean) {
    val isPreviewMode = LocalInspectionMode.current
    if (isPreviewMode) {
        FavoriteListForPreview(isVisible)
    } else {
        FavoriteListWithVm(isVisible)
    }
}

@Composable
private fun FavoriteListForPreview(isVisible: Boolean) {
    FavoriteList(
        isVisible = isVisible,
        state = FavoriteListContract.State(
            keyword = "bsscco",
            favorites = listOf(
                FavoriteListContract.State.Favorite(
                    userName = "bsscco1",
                    avatarUrl = "",
                ),
                FavoriteListContract.State.Favorite(
                    userName = "bsscco2",
                    avatarUrl = "",
                ),
                FavoriteListContract.State.Favorite(
                    userName = "bsscco3",
                    avatarUrl = "",
                ),
                FavoriteListContract.State.Favorite(
                    userName = "bsscco4",
                    avatarUrl = "",
                ),
            ),
        ),
        onEvent = {},
    )
}

@Composable
private fun FavoriteListWithVm(isVisible: Boolean) {
    val viewModel = hiltViewModel<FavoriteListViewModel>()
    val context = LocalContext.current
    val navController = LocalNavController.current

    LaunchedEffect(true) {
        viewModel.effectFlow.collect { effect ->
            when (effect) {
                is FavoriteListContract.Effect.NavigateToUserDetail -> {
                    val uri = "${Uris.GithubUsers.USER_DETAIL}?user_name=${effect.userName}".toUri()
                    navController.navigate(uri)
                }
                is FavoriteListContract.Effect.ShowErrorToast -> context.showToast(effect.message)
            }
        }
    }

    FavoriteList(
        isVisible = isVisible,
        state = viewModel.stateFlow.collectAsState().value,
        onEvent = viewModel.eventHandler,
    )
}

@Composable
private fun FavoriteList(
    isVisible: Boolean,
    state: FavoriteListContract.State,
    onEvent: (FavoriteListContract.Event) -> Unit,
) {
    FadeIn(
        modifier = Modifier.fillMaxSize(),
        visible = isVisible,
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            SearchBox(
                keyword = state.keyword,
                onKeywordChanged = { keyword -> onEvent(FavoriteListContract.Event.OnKeywordChanged(keyword)) },
            )

            ColumnDivider()

            LazyFavoriteList(
                state = state,
                onUserItemClicked = { userName -> onEvent(FavoriteListContract.Event.OnUserItemClicked(userName)) },
                onFavoriteIconClicked = { userName -> onEvent(FavoriteListContract.Event.OnFavoriteIconClicked(userName)) },
            )
        }
    }
}

@Composable
private fun ColumnScope.LazyFavoriteList(
    state: FavoriteListContract.State,
    onUserItemClicked: (String) -> Unit,
    onFavoriteIconClicked: (String) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f),
    ) {
        items(
            items = state.favorites,
            key = { favorite -> favorite.userName }
        ) { favorite ->
            var isFavorite by remember { mutableStateOf(true) }
            UserItem(
                userName = favorite.userName,
                avatarUrl = favorite.avatarUrl,
                favorite = isFavorite,
                onUserItemClicked = { onUserItemClicked(favorite.userName) },
                onFavoriteIconClicked = {
                    isFavorite = false
                    onFavoriteIconClicked(favorite.userName)
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EmptyKeywordPreview() {
    GithubUsersTheme {
        FavoriteList(
            isVisible = true,
            state = FavoriteListContract.State(
                keyword = "",
                favorites = listOf(
                    FavoriteListContract.State.Favorite(
                        userName = "a1",
                        avatarUrl = "",
                    ),
                    FavoriteListContract.State.Favorite(
                        userName = "b2",
                        avatarUrl = "",
                    ),
                    FavoriteListContract.State.Favorite(
                        userName = "c3",
                        avatarUrl = "",
                    ),
                ),
            ),
            onEvent = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun KeywordInputPreview() {
    GithubUsersTheme {
        FavoriteListForPreview(isVisible = true)
    }
}

@Preview(showBackground = true)
@Composable
private fun EmptyListPreview() {
    GithubUsersTheme {
        FavoriteList(
            isVisible = true,
            state = FavoriteListContract.State(
                keyword = "bsscco",
                favorites = emptyList(),
            ),
            onEvent = {},
        )
    }
}
