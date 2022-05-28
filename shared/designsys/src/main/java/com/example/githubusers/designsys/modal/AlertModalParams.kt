package com.example.githubusers.designsys.modal

interface AlertModal {

    sealed interface Event {
        object OnPrimaryClick : Event
    }

    data class State(
        val visible: Boolean = true,
        val title: String? = null,
        val content: String,
        val primaryButtonText: String,
    )
}