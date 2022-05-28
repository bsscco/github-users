package com.example.githubusers.userdetail

import com.example.githubusers.mvi.UiEffect
import com.example.githubusers.mvi.UiEvent
import com.example.githubusers.mvi.UiState

internal interface UserDetailContract {

    sealed interface Event : UiEvent {
        object OnBackIconClicked : Event
        object OnPullToRefresh : Event
        object OnFavoriteIconClicked : Event
    }

    data class State(
        val topBar: TopBarState,
        val isRefreshing : Boolean,
        val content: ContentState,
    ) : UiState {
        sealed interface TopBarState {
            object Loading : TopBarState
            data class Bar(
                val userName: String,
            ) : TopBarState
        }

        sealed interface ContentState {
            object Loading : ContentState
            data class Content(
                val avatarUrl: String,
                val createdAt: String,
                val favorite: Boolean,
            ) : ContentState
        }
    }

    sealed interface Effect : UiEffect {
        object NavigateToBack : Effect
        object ShowNoUserModal : Effect
        data class ShowErrorToast(val message: String) : Effect
    }
}