package com.example.githubusers.userdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubusers.domain.user.detail.DetailUser
import com.example.githubusers.domain.user.detail.LoadDetailUserUseCase
import com.example.githubusers.domain.user.detail.NoDetailUserException
import com.example.githubusers.domain.user.favorite.AddFavoriteUseCase
import com.example.githubusers.domain.user.favorite.RemoveFavoriteUseCase
import com.example.githubusers.ktutil.coroutine.DefaultDispatcher
import com.example.githubusers.ktutil.flow.filterSuccess
import com.example.githubusers.ktutil.flow.mapToData
import com.example.githubusers.ktutil.flow.successOrThrow
import com.example.githubusers.ktutil.flow.throwIfError
import com.example.githubusers.ktutil.log.CrashReportHelper
import com.example.githubusers.ktutil.log.EventLogHelper
import com.example.githubusers.mvi.MviReducer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class UserDetailViewModel @Inject constructor(
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    private val loadDetailUserUseCase: LoadDetailUserUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val removeFavoriteUseCase: RemoveFavoriteUseCase,
    private val eventLogHelper: EventLogHelper,
    private val crashReportHelper: CrashReportHelper,
) : ViewModel() {

    private val forceRefresh = MutableStateFlow(0L)
    private val userName = MutableStateFlow<String?>(null)
    private val user: Flow<DetailUser> = getUser()

    private val reducer = MviReducer<
            UserDetailContract.Event,
            UserDetailContract.State,
            UserDetailContract.Effect,
            >(
        viewModelScope = viewModelScope,
        initialState = createInitialState(),
        handleEvent = ::handleEvent,
    )

    val eventHandler = reducer::setEvent
    val stateFlow = reducer.stateFlow
    val effectFlow = reducer.effectFlow

    init {
        loadState()
    }

    private fun createInitialState() = UserDetailContract.State(
        isRefreshing = true,
        topBar = UserDetailContract.State.TopBarState.Loading,
        content = UserDetailContract.State.ContentState.Loading,
    )

    private fun getUser(): Flow<DetailUser> {
        return combine(
            forceRefresh,
            userName.filterNotNull(),
        ) { a, b -> a to b }
            .onEach { reducer.setState { copy(isRefreshing = true) } }
            .flatMapLatest { (_, userName) ->
                loadDetailUserUseCase(LoadDetailUserUseCase.Params(refreshFromRemote = true, userName))
            }
            .throwIfError()
            .filterSuccess()
            .mapToData()
            .onEach { reducer.setState { copy(isRefreshing = false) } }
            .catch { error -> handleLoadingError(error) }
            .stateIn(viewModelScope, SharingStarted.Lazily, null)
            .filterNotNull()
    }

    private fun handleLoadingError(error: Throwable) {
        crashReportHelper.logAndReport(error)

        when (error) {
            is NoDetailUserException -> {
                reducer.setEffect(UserDetailContract.Effect.ShowNoUserModal)
                reducer.setState { copy(isRefreshing = false) }
            }
            else -> {
                reducer.setEffect(UserDetailContract.Effect.ShowErrorToast(error.message!!))
                reducer.setEffect(UserDetailContract.Effect.NavigateToBack)
            }
        }
    }

    private fun loadState() {
        loadTopBar()
        loadContent()
    }

    private fun loadTopBar() {
        user
            .onEach { user ->
                val state = user.toTopBarState()
                reducer.setState { copy(topBar = state) }
            }
            .catch { error -> handleLoadingError(error) }
            .flowOn(defaultDispatcher)
            .launchIn(viewModelScope)
    }

    private fun loadContent() {
        user
            .onEach { user ->
                val state = user.toContentState()
                reducer.setState { copy(content = state) }
            }
            .catch { error -> handleLoadingError(error) }
            .flowOn(defaultDispatcher)
            .launchIn(viewModelScope)
    }

    fun initUserName(userName: String) {
        this.userName.value = userName
    }

    private fun handleEvent(event: UserDetailContract.Event) {
        when (event) {
            is UserDetailContract.Event.OnBackIconClicked -> handleBackIconClicked()
            is UserDetailContract.Event.OnPullToRefresh -> handlePullToRefresh()
            is UserDetailContract.Event.OnFavoriteIconClicked -> handleFavoriteIconClicked()
        }
    }

    private fun handleBackIconClicked() {
        eventLogHelper.logEvent("clickBackIcon")
        reducer.setEffect(UserDetailContract.Effect.NavigateToBack)
    }

    private fun handlePullToRefresh() {
        eventLogHelper.logEvent("pullToRefresh")
        forceRefresh.value += 1
    }

    private fun handleFavoriteIconClicked() {
        viewModelScope.launch {
            try {
                val user = user.first()
                eventLogHelper.logEvent("clickFavoriteIcon", mapOf("userName" to user.userName))

                if (user.favorite) {
                    removeFavoriteUseCase(RemoveFavoriteUseCase.Params(user.userName)).successOrThrow()
                } else {
                    addFavoriteUseCase(AddFavoriteUseCase.Params(user.userName, user.avatarUrl)).successOrThrow()
                }
            } catch (error: Exception) {
                handleFavoriteError(error)
            }
        }
    }

    private fun handleFavoriteError(error: Exception) {
        crashReportHelper.logAndReport(error)
        reducer.setEffect(UserDetailContract.Effect.ShowErrorToast(error.message!!))
    }
}