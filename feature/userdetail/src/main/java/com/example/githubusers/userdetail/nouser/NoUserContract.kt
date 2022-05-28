package com.example.githubusers.userdetail.nouser

import com.example.githubusers.mvi.UiEffect
import com.example.githubusers.mvi.UiEvent
import com.example.githubusers.mvi.UiState

internal interface NoUserContract {

    sealed interface Event : UiEvent {
        object OnNeedShowModal : Event
        object OnOkClicked : Event
    }

    data class State(
        val showModal: Boolean,
    ) : UiState

    sealed interface Effect : UiEffect {
        object NavigateToBack : Effect
    }
}