package com.example.githubusers.favorite

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubusers.domain.user.favorite.Favorite
import com.example.githubusers.domain.user.favorite.LoadFavoritesUseCase
import com.example.githubusers.domain.user.favorite.RemoveFavoriteUseCase
import com.example.githubusers.ktutil.coroutine.DefaultDispatcher
import com.example.githubusers.ktutil.flow.onSuccess
import com.example.githubusers.ktutil.flow.throwIfError
import com.example.githubusers.ktutil.log.CrashReportHelper
import com.example.githubusers.ktutil.log.EventLogHelper
import com.example.githubusers.mvi.MviReducer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
internal class FavoriteListViewModel @Inject constructor(
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    private val savedState: SavedStateHandle,
    private val loadFavoritesUseCase: LoadFavoritesUseCase,
    private val removeFavoriteUseCase: RemoveFavoriteUseCase,
    private val eventLogHelper: EventLogHelper,
    private val crashReportHelper: CrashReportHelper,
) : ViewModel() {

    private val keyword = MutableStateFlow(savedState.getKeyword())
    private var favorites = emptyList<Favorite>()
    private var favoriteMap = emptyMap<String, Favorite>()

    private val reducer = MviReducer<
            FavoriteListContract.Event,
            FavoriteListContract.State,
            FavoriteListContract.Effect,
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

    private fun SavedStateHandle.getKeyword(): String {
        return get<String>(KEYWORD) ?: ""
    }

    private fun createInitialState() = FavoriteListContract.State(
        keyword = keyword.value,
        favorites = emptyList(),
    )

    private fun loadState() {
        keyword
            .flatMapLatest { keyword ->
                if (keyword.isBlank()) {
                    loadFavoritesUseCase(LoadFavoritesUseCase.Params.All)
                } else {
                    loadFavoritesUseCase(LoadFavoritesUseCase.Params.OnlySearched(keyword))
                }
            }
            .onSuccess { favorites ->
                this.favorites = favorites
                favoriteMap = favorites.associateBy { it.userName }

                reducer.setState {
                    val state = withContext(defaultDispatcher) { favorites.toFavoriteListState() }
                    copy(favorites = state)
                }
            }
            .throwIfError()
            .catch { error -> handleLoadingError(error) }
            .flowOn(defaultDispatcher)
            .launchIn(viewModelScope)
    }

    private fun handleLoadingError(error: Throwable) {
        crashReportHelper.logAndReport(error)
        reducer.setEffect(FavoriteListContract.Effect.ShowErrorToast(error.message!!))
    }

    private fun handleEvent(event: FavoriteListContract.Event) {
        when (event) {
            is FavoriteListContract.Event.OnKeywordChanged -> handleKeywordChanged(event.keyword)
            is FavoriteListContract.Event.OnUserItemClicked -> handleUserItemClicked(event.userName)
            is FavoriteListContract.Event.OnFavoriteIconClicked -> handleFavoriteIconClicked(event.userName)
        }
    }

    private fun handleKeywordChanged(keyword: String) {
        this.keyword.value = keyword
        savedState[KEYWORD] = keyword
        reducer.setState { copy(keyword = keyword) }
    }

    private fun handleUserItemClicked(userName: String) {
        eventLogHelper.logEvent("clickUserItem", mapOf("userName" to userName))
        reducer.setEffect(FavoriteListContract.Effect.NavigateToUserDetail(userName))
    }

    private fun handleFavoriteIconClicked(userName: String) {
        eventLogHelper.logEvent("clickFavoriteIcon", mapOf("userName" to userName))

        viewModelScope.launch {
            try {
                delay(200L)
                removeFavoriteUseCase(RemoveFavoriteUseCase.Params(userName))
            } catch (error: Exception) {
                handleFavoriteError(error)
            }
        }
    }

    private fun handleFavoriteError(error: Exception) {
        crashReportHelper.logAndReport(error)
        reducer.setEffect(FavoriteListContract.Effect.ShowErrorToast(error.message!!))
    }

    companion object {
        private const val KEYWORD = "keyword"
    }
}