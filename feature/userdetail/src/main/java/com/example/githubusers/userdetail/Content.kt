package com.example.githubusers.userdetail

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.githubusers.aosutil.dimention.textDp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.delay

@Composable
internal fun Content(
    isRefreshing: Boolean,
    state: UserDetailContract.State.ContentState,
    onPullToRefresh: () -> Unit,
    onFavoriteIconClicked: () -> Unit,
) {
    val isLoadingOrRefreshing by remember(state, isRefreshing) {
        mutableStateOf((state is UserDetailContract.State.ContentState.Loading) || isRefreshing)
    }
    SwipeRefresh(
        state = rememberSwipeRefreshState(isLoadingOrRefreshing),
        onRefresh = { onPullToRefresh() },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ) {
            if (state is UserDetailContract.State.ContentState.Content) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Spacer(Modifier.width(16.dp))

                    Column {
                        Text(
                            text = "계정 생성일",
                            fontSize = 12.textDp,
                        )

                        Spacer(Modifier.height(8.dp))

                        Text(
                            text = state.createdAt,
                            fontWeight = FontWeight.Black,
                        )
                    }


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
                            .size(ButtonDefaults.IconSize * 2),
                        imageVector = if (state.favorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = null,
                    )
                }

                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(Color.LightGray),
                    model = state.avatarUrl,
                    contentDescription = null,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoadingPreview() {
    Content(
        isRefreshing = true,
        state = UserDetailContract.State.ContentState.Loading,
        onPullToRefresh = {},
        onFavoriteIconClicked = {},
    )
}

@Preview(showBackground = true)
@Composable
private fun FavoritePreview() {
    Content(
        isRefreshing = false,
        state = UserDetailContract.State.ContentState.Content(
            avatarUrl = "",
            createdAt = "2022년10월10일",
            favorite = true,
        ),
        onPullToRefresh = {},
        onFavoriteIconClicked = {},
    )
}

@Preview(showBackground = true)
@Composable
private fun NotFavoritePreview() {
    Content(
        isRefreshing = false,
        state = UserDetailContract.State.ContentState.Content(
            avatarUrl = "",
            createdAt = "2022년10월10일",
            favorite = false,
        ),
        onPullToRefresh = {},
        onFavoriteIconClicked = {},
    )
}