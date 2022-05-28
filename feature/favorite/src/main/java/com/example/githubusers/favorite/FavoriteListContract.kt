package com.example.githubusers.favorite

import com.example.githubusers.mvi.UiEffect
import com.example.githubusers.mvi.UiEvent
import com.example.githubusers.mvi.UiState

interface FavoriteListContract {

    sealed interface Event : UiEvent {
        data class OnKeywordChanged(val keyword: String) : Event
        data class OnUserItemClicked(val userName: String) : Event
        data class OnFavoriteIconClicked(val userName: String) : Event
    }

    data class State(
        val keyword: String,
        val favorites: List<Favorite>,
    ) : UiState {

        data class Favorite(
            val userName: String,
            val avatarUrl: String,
        )
    }

    sealed interface Effect : UiEffect {
        data class NavigateToUserDetail(val userName: String) : Effect
        data class ShowErrorToast(val message: String) : Effect
    }
}