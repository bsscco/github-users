package com.example.githubusers.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubusers.ktutil.log.EventLogHelper
import com.example.githubusers.mvi.MviReducer
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val savedState: SavedStateHandle,
    private val eventLogHelper: EventLogHelper,
) : ViewModel() {

    private val reducer = MviReducer<
            HomeContract.Event,
            HomeContract.State,
            HomeContract.Effect,
            >(
        viewModelScope = viewModelScope,
        initialState = createInitialState(),
        handleEvent = ::handleEvent,
    )

    val eventHandler = reducer::setEvent
    val stateFlow = reducer.stateFlow
    val effectFlow = reducer.effectFlow

    private fun createInitialState() = HomeContract.State(
        selectedTab = savedState.getSelectedTab(),
    )

    private fun SavedStateHandle.getSelectedTab(): HomeContract.State.Tab {
        return get<HomeContract.State.Tab>(SELECTED_TAB) ?: HomeContract.State.Tab.USER_SEARCH
    }

    private fun handleEvent(event: HomeContract.Event) {
        when (event) {
            is HomeContract.Event.OnTabClicked -> handleTabClicked(event.tab)
            is HomeContract.Event.onBackKeyClicked -> handleBackKeyClicked()
        }
    }

    private fun handleTabClicked(tab: HomeContract.State.Tab) {
        eventLogHelper.logEvent("clickTab", mapOf("tab" to tab.name))

        savedState.set(SELECTED_TAB, tab)
        reducer.setState { copy(selectedTab = tab) }
    }

    private fun handleBackKeyClicked() {
        eventLogHelper.logEvent("clickBackKey")
        reducer.setEffect(HomeContract.Effect.FinishApp)
    }

    companion object {
        private const val SELECTED_TAB = "selectedTab"
    }
}