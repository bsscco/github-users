package com.example.githubusers.usersearch

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.githubusers.aosutil.navigation.LocalNavController
import com.example.githubusers.aosutil.toast.showToast
import com.example.githubusers.designsys.divider.ColumnDivider
import com.example.githubusers.designsys.theme.GithubUsersTheme
import com.example.githubusers.designsys.transition.FadeIn
import com.example.githubusers.navigation.Uris
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.flow.flow

@Composable
fun UserSearch(isVisible: Boolean) {
    val isPreviewMode = LocalInspectionMode.current
    if (isPreviewMode) {
        UserSearchForPreview(isVisible)
    } else {
        UserSearchWithVm(isVisible)
    }
}

@Composable
private fun UserSearchForPreview(isVisible: Boolean) {
    UserSearch(
        isVisible = isVisible,
        state = UserSearchContract.State(
            keyword = "bsscco",
            isRefreshing = false,
            userList = UserSearchContract.State.UserListState.UserList(
                users = flow {
                    emit(PagingData.from(
                        listOf(
                            UserSearchContract.State.UserListState.UserList.User(
                                userName = "bsscco1",
                                avatarUrl = "",
                                favorite = false,
                            ),
                            UserSearchContract.State.UserListState.UserList.User(
                                userName = "bsscco2",
                                avatarUrl = "",
                                favorite = true,
                            ),
                            UserSearchContract.State.UserListState.UserList.User(
                                userName = "bsscco3",
                                avatarUrl = "",
                                favorite = true,
                            ),
                            UserSearchContract.State.UserListState.UserList.User(
                                userName = "bsscco4",
                                avatarUrl = "",
                                favorite = false,
                            ),
                        )
                    ))
                },
            )
        ),
        onEvent = {},
    )
}

@Composable
private fun UserSearchWithVm(isVisible: Boolean) {
    val viewModel = hiltViewModel<UserSearchViewModel>()
    val context = LocalContext.current
    val navController = LocalNavController.current

    LaunchedEffect(true) {
        viewModel.effectFlow.collect { effect ->
            when (effect) {
                is UserSearchContract.Effect.NavigateToUserDetail -> {
                    val uri = "${Uris.GithubUsers.USER_DETAIL}?user_name=${effect.userName}".toUri()
                    navController.navigate(uri)
                }
                is UserSearchContract.Effect.ShowErrorToast -> context.showToast(effect.message)
                is UserSearchContract.Effect.ShowSearchRateLimitedToast -> context.showToast("1분 동안 10회 검색 제한에 의해 검색이 실패했습니다.")
                is UserSearchContract.Effect.ShowSearchTimeOutToast -> context.showToast("서버가 응답하지 않아 검색이 실패했습니다.")
                is UserSearchContract.Effect.FinishApp -> (context as Activity).finish()
            }
        }
    }

    UserSearch(
        isVisible = isVisible,
        state = viewModel.stateFlow.collectAsState().value,
        onEvent = viewModel.eventHandler,
    )
}

@Composable
private fun UserSearch(
    isVisible: Boolean,
    state: UserSearchContract.State,
    onEvent: (UserSearchContract.Event) -> Unit,
) {
    FadeIn(
        modifier = Modifier.fillMaxSize(),
        visible = isVisible,
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            SearchBox(
                keyword = state.keyword,
                onSearchIconClicked = { keyword -> onEvent(UserSearchContract.Event.OnSearchIconClicked(keyword)) },
                onDoneKeyClicked = { keyword -> onEvent(UserSearchContract.Event.OnDoneKeyClicked(keyword)) },
            )

            ColumnDivider()

            UserList(
                isRefreshing = state.isRefreshing,
                state = state.userList,
                onPullToRefresh = { onEvent(UserSearchContract.Event.OnPullToRefresh) },
                onUserItemClicked = { userName -> onEvent(UserSearchContract.Event.OnUserItemClicked(userName)) },
                onFavoriteIconClicked = { userName -> onEvent(UserSearchContract.Event.OnFavoriteIconClicked(userName)) },
                onRefreshError = { error -> onEvent(UserSearchContract.Event.OnRefreshError(error)) }
            )
        }
    }
}

@Composable
private fun ColumnScope.UserList(
    isRefreshing: Boolean,
    state: UserSearchContract.State.UserListState,
    onPullToRefresh: () -> Unit,
    onUserItemClicked: (String) -> Unit,
    onFavoriteIconClicked: (String) -> Unit,
    onRefreshError: (Throwable) -> Unit,
) {
    val isLoadingOrRefreshing by remember(state, isRefreshing) {
        mutableStateOf((state is UserSearchContract.State.UserListState.Loading) || isRefreshing)
    }
    SwipeRefresh(
        state = rememberSwipeRefreshState(isLoadingOrRefreshing),
        onRefresh = { onPullToRefresh() },
    ) {
        if (state is UserSearchContract.State.UserListState.UserList) {
            val lazyPagingItems = state.users.collectAsLazyPagingItems()
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
            ) {
                val refreshState = lazyPagingItems.loadState.refresh
                val appendState = lazyPagingItems.loadState.append

                if (refreshState is LoadState.Error) {
                    item {
                        LaunchedEffect(true) {
                            onRefreshError(refreshState.error)
                        }
                    }
                }

                items(lazyPagingItems) { user ->
                    if (user != null) {
                        UserItem(
                            userName = user.userName,
                            avatarUrl = user.avatarUrl,
                            favorite = user.favorite,
                            onUserItemClicked = { onUserItemClicked(user.userName) },
                            onFavoriteIconClicked = { onFavoriteIconClicked(user.userName) },
                        )
                    }
                }

                when {
                    refreshState is LoadState.Error -> item {
                        ErrorMessage(error = refreshState.error)
                    }
                    appendState == LoadState.Loading -> item {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentWidth(Alignment.CenterHorizontally)
                        )
                    }
                    appendState is LoadState.Error -> item {
                        ErrorMessage(error = appendState.error)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EmptyKeywordPreview() {
    GithubUsersTheme {
        UserSearch(
            isVisible = true,
            state = UserSearchContract.State(
                keyword = "",
                isRefreshing = false,
                userList = UserSearchContract.State.UserListState.UserList(
                    users = flow {
                        emit(PagingData.empty())
                    },
                ),
            ),
            onEvent = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchedResultPreview() {
    GithubUsersTheme {
        UserSearchForPreview(isVisible = true)
    }
}