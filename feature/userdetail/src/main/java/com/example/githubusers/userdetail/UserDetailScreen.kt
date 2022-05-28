package com.example.githubusers.userdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.githubusers.aosutil.navigation.LocalNavController
import com.example.githubusers.aosutil.toast.showToast
import com.example.githubusers.designsys.divider.ColumnDivider
import com.example.githubusers.userdetail.nouser.NoUserContract
import com.example.githubusers.userdetail.nouser.NoUserModal
import com.example.githubusers.userdetail.nouser.NoUserViewModel

@Composable
fun UserDetailScreen(userName: String) {
    val userDetailViewModel = hiltViewModel<UserDetailViewModel>()
    val noUserViewModel = hiltViewModel<NoUserViewModel>()
    val context = LocalContext.current
    val navController = LocalNavController.current

    LaunchedEffect(true) {
        userDetailViewModel.initUserName(userName)
        userDetailViewModel.effectFlow.collect { effect ->
            when (effect) {
                is UserDetailContract.Effect.NavigateToBack -> navController.popBackStack()
                is UserDetailContract.Effect.ShowNoUserModal -> noUserViewModel.eventHandler(NoUserContract.Event.OnNeedShowModal)
                is UserDetailContract.Effect.ShowErrorToast -> context.showToast(effect.message)
            }
        }
    }

    LaunchedEffect(true) {
        noUserViewModel.effectFlow.collect { effect ->
            when (effect) {
                is NoUserContract.Effect.NavigateToBack -> navController.popBackStack()
            }
        }
    }

    UserDetailScreen(
        userDetailState = userDetailViewModel.stateFlow.collectAsState().value,
        onUserDetailEvent = userDetailViewModel.eventHandler,
        noUserState = noUserViewModel.stateFlow.collectAsState().value,
        onNoUserEvent = noUserViewModel.eventHandler,
    )
}

@Composable
private fun UserDetailScreen(
    userDetailState: UserDetailContract.State,
    onUserDetailEvent: (UserDetailContract.Event) -> Unit,
    noUserState: NoUserContract.State,
    onNoUserEvent: (NoUserContract.Event) -> Unit,
) {
    UserDetailScreen(userDetailState, onUserDetailEvent)

    NoUserModal(noUserState, onNoUserEvent)
}

@Composable
private fun UserDetailScreen(
    state: UserDetailContract.State,
    onEvent: (UserDetailContract.Event) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        TopBar(
            state = state.topBar,
            onBackIconClicked = { onEvent(UserDetailContract.Event.OnBackIconClicked) },
        )

        ColumnDivider()

        Content(
            isRefreshing = state.isRefreshing,
            state = state.content,
            onPullToRefresh = { onEvent(UserDetailContract.Event.OnPullToRefresh) },
            onFavoriteIconClicked = { onEvent(UserDetailContract.Event.OnFavoriteIconClicked) }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LoadingPreview() {
    UserDetailScreen(
        state = UserDetailContract.State(
            isRefreshing = true,
            topBar = UserDetailContract.State.TopBarState.Loading,
            content = UserDetailContract.State.ContentState.Loading,
        ),
        onEvent = {},
    )
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    UserDetailScreen(
        state = UserDetailContract.State(
            isRefreshing = false,
            topBar = UserDetailContract.State.TopBarState.Bar(
                userName = "bsscco"
            ),
            content = UserDetailContract.State.ContentState.Content(
                avatarUrl = "",
                createdAt = "2022년10월10일",
                favorite = false,
            ),
        ),
        onEvent = {},
    )
}