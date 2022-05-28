package com.example.githubusers.userdetail.nouser

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubusers.domain.user.search.SearchUser
import com.example.githubusers.ktutil.log.EventLogHelper
import com.example.githubusers.mvi.MviReducer
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class NoUserViewModel @Inject constructor(
    private val eventLogHelper: EventLogHelper,
) : ViewModel() {

    private lateinit var user: SearchUser

    private val reducer = MviReducer<
            NoUserContract.Event,
            NoUserContract.State,
            NoUserContract.Effect,
            >(
        viewModelScope = viewModelScope,
        initialState = createInitialState(),
        handleEvent = ::handleEvent,
    )

    val eventHandler = reducer::setEvent
    val stateFlow = reducer.stateFlow
    val effectFlow = reducer.effectFlow

    private fun createInitialState() = NoUserContract.State(
        showModal = false,
    )

    private fun handleEvent(event: NoUserContract.Event) {
        when (event) {
            is NoUserContract.Event.OnNeedShowModal -> handleNeedShowModal()
            is NoUserContract.Event.OnOkClicked -> handleOkClicked()
        }
    }

    private fun handleNeedShowModal() {
        eventLogHelper.logEvent("showNoUserModal")
        reducer.setState { copy(showModal = true) }
    }

    private fun handleOkClicked() {
        eventLogHelper.logEvent("clickOk")
        reducer.setEffect(NoUserContract.Effect.NavigateToBack)
    }
}