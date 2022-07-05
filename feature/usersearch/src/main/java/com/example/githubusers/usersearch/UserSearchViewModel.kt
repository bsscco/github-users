package com.example.githubusers.usersearch

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.map
import com.example.githubusers.domain.user.favorite.AddFavoriteUseCase
import com.example.githubusers.domain.user.favorite.RemoveFavoriteUseCase
import com.example.githubusers.domain.user.search.LoadSearchUsersUseCase
import com.example.githubusers.domain.user.search.SearchUser
import com.example.githubusers.domain.user.search.SearchUserRateLimitedPerMinuteException
import com.example.githubusers.domain.user.search.SearchUserTimeOutException
import com.example.githubusers.ktutil.coroutine.DefaultDispatcher
import com.example.githubusers.ktutil.flow.onErrorOrCatch
import com.example.githubusers.ktutil.flow.successOrThrow
import com.example.githubusers.ktutil.flow.withResult
import com.example.githubusers.ktutil.log.CrashReportHelper
import com.example.githubusers.ktutil.log.EventLogHelper
import com.example.githubusers.mvi.MviReducer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class UserSearchViewModel @Inject constructor(
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    private val savedState: SavedStateHandle,
    private val loadSearchUsersUseCase: LoadSearchUsersUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val removeFavoriteUseCase: RemoveFavoriteUseCase,
    private val eventLogHelper: EventLogHelper,
    private val crashReportHelper: CrashReportHelper,
) : ViewModel() {

    private val forceRefresh = MutableStateFlow(0L)
    private val keyword = MutableStateFlow(savedState.getKeyword())
    private var userMap = mutableMapOf<String, SearchUser>()

    private val reducer = MviReducer<
            UserSearchContract.Event,
            UserSearchContract.State,
            UserSearchContract.Effect,
            >(
        viewModelScope = viewModelScope,
        initialState = createInitialState(),
        handleEvent = ::handleEvent,
    )

    val eventHandler = reducer::setEvent
    val stateFlow = reducer.stateFlow
    val effectFlow = reducer.effectFlow

    init {
        loadUserListState()
    }

    private fun SavedStateHandle.getKeyword(): String {
        return get<String>(KEYWORD) ?: ""
    }

    private fun createInitialState() = UserSearchContract.State(
        keyword = keyword.value,
        isRefreshing = true,
        userList = UserSearchContract.State.UserListState.Loading,
    )

    private fun loadUserListState() {
        combine(
            forceRefresh,
            keyword.filter { keyword -> keyword.isNotBlank() },
        ) { a, b -> a to b }
            .onEach { reducer.setState { copy(isRefreshing = true) } }
            .map { (_, keyword) ->
                withResult {
                    val useCaseParams = LoadSearchUsersUseCase.Params(refreshFromRemote = true, keyword)
                    val pagingDataFlow = loadSearchUsersUseCase(useCaseParams).successOrThrow()

                    userMap.clear()

                    reducer.setState {
                        val state = pagingDataFlow
                            .map { pagingData ->
                                pagingData.map { user ->
                                    userMap[user.userName] = user
                                    user
                                }
                            }
                            .toUserListState()
                        copy(isRefreshing = false, userList = state)
                    }
                }
            }
            .onErrorOrCatch { error -> handleLoadingError(error) }
            .flowOn(defaultDispatcher)
            .launchIn(viewModelScope)
    }

    private fun handleLoadingError(error: Throwable) {
        crashReportHelper.logAndReport(error)

        reducer.setEffect(UserSearchContract.Effect.ShowErrorToast(error.message!!))
        reducer.setEffect(UserSearchContract.Effect.FinishApp)
    }

    private fun handleEvent(event: UserSearchContract.Event) {
        when (event) {
            is UserSearchContract.Event.OnPullToRefresh -> handlePullToRefresh()
            is UserSearchContract.Event.OnSearchIconClicked -> handleSearchIconClicked(event.keyword)
            is UserSearchContract.Event.OnDoneKeyClicked -> handleDoneKeyClicked(event.keyword)
            is UserSearchContract.Event.OnUserItemClicked -> handleUserItemClicked(event.userName)
            is UserSearchContract.Event.OnFavoriteIconClicked -> handleFavoriteIconClicked(event.userName)
            is UserSearchContract.Event.OnRefreshError -> handleRefreshError(event.error)
        }
    }

    private fun handlePullToRefresh() {
        eventLogHelper.logEvent("pullToRefresh")
        forceRefresh.value += 1
    }

    private fun handleSearchIconClicked(keyword: String) {
        eventLogHelper.logEvent("clickSearchIcon", mapOf("keyword" to keyword))
        setKeyword(keyword)
        forceRefresh.value += 1
    }

    private fun setKeyword(keyword: String) {
        this.keyword.value = keyword
        savedState[KEYWORD] = keyword
        reducer.setState { copy(keyword = keyword) }
    }

    private fun handleDoneKeyClicked(keyword: String) {
        eventLogHelper.logEvent("clickDoneKey", mapOf("keyword" to keyword))
        setKeyword(keyword)
        forceRefresh.value += 1
    }

    private fun handleUserItemClicked(userName: String) {
        eventLogHelper.logEvent("clickUserItem", mapOf("userName" to userName))
        reducer.setEffect(UserSearchContract.Effect.NavigateToUserDetail(userName))
    }

    private fun handleFavoriteIconClicked(userName: String) {
        eventLogHelper.logEvent("clickFavoriteIcon", mapOf("userName" to userName))

        viewModelScope.launch {
            try {
                val user = userMap.getValue(userName)
                if (user.favorite) {
                    removeFavoriteUseCase(RemoveFavoriteUseCase.Params(userName)).successOrThrow()
                } else {
                    addFavoriteUseCase(AddFavoriteUseCase.Params(userName, user.avatarUrl)).successOrThrow()
                }
            } catch (error: Exception) {
                handleFavoriteError(error)
            }
        }
    }

    private fun handleFavoriteError(error: Exception) {
        crashReportHelper.logAndReport(error)
        reducer.setEffect(UserSearchContract.Effect.ShowErrorToast(error.message!!))
    }

    private fun handleRefreshError(error: Throwable) {
        eventLogHelper.logEvent("refreshError", mapOf("error" to error.message))

        when (error) {
            is SearchUserRateLimitedPerMinuteException -> reducer.setEffect(UserSearchContract.Effect.ShowSearchRateLimitedToast)
            is SearchUserTimeOutException -> reducer.setEffect(UserSearchContract.Effect.ShowSearchTimeOutToast)
            else -> reducer.setEffect(UserSearchContract.Effect.ShowErrorToast(error.message!!))
        }
    }

    companion object {
        private const val KEYWORD = "keyword"
    }
}