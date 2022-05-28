package com.example.githubusers.usersearch

import androidx.paging.PagingData
import com.example.githubusers.mvi.UiEffect
import com.example.githubusers.mvi.UiEvent
import com.example.githubusers.mvi.UiState
import kotlinx.coroutines.flow.Flow

interface UserSearchContract {

    sealed interface Event : UiEvent {
        object OnPullToRefresh : Event
        data class OnSearchIconClicked(val keyword: String) : Event
        data class OnDoneKeyClicked(val keyword: String) : Event
        data class OnUserItemClicked(val userName: String) : Event
        data class OnFavoriteIconClicked(val userName: String) : Event
        data class OnRefreshError(val error: Throwable) : Event
    }

    data class State(
        val keyword: String,
        val isRefreshing: Boolean,
        val userList: UserListState,
    ) : UiState {

        sealed interface UserListState {
            object Loading : UserListState

            data class UserList(val users: Flow<PagingData<User>>) : UserListState {
                data class User(
                    val userName: String,
                    val avatarUrl: String,
                    val favorite: Boolean,
                )
            }
        }
    }

    sealed interface Effect : UiEffect {
        data class NavigateToUserDetail(val userName: String) : Effect
        data class ShowErrorToast(val message: String) : Effect
        object ShowSearchRateLimitedToast : Effect
        object ShowSearchTimeOutToast : Effect
        object FinishApp : Effect
    }
}