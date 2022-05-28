package com.example.githubusers.home

import androidx.annotation.Keep
import com.example.githubusers.mvi.UiEffect
import com.example.githubusers.mvi.UiEvent
import com.example.githubusers.mvi.UiState

internal interface HomeContract {

    sealed interface Event : UiEvent {
        data class OnTabClicked(val tab: State.Tab) : Event
        object onBackKeyClicked : Event
    }

    data class State(
        val selectedTab: Tab,
    ) : UiState {

        @Keep
        enum class Tab { USER_SEARCH, FAVORITE }
    }

    sealed interface Effect : UiEffect {
        object FinishApp : Effect
    }
}