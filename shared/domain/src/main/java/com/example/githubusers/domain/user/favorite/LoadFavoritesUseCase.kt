package com.example.githubusers.domain.user.favorite

import com.example.githubusers.domain.usecase.FlowUseCase
import com.example.githubusers.domain.user.UserRepository
import com.example.githubusers.ktutil.coroutine.DefaultDispatcher
import com.example.githubusers.ktutil.flow.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoadFavoritesUseCase @Inject constructor(
    @DefaultDispatcher defaultDispatcher: CoroutineDispatcher,
    private val userRepository: UserRepository,
) : FlowUseCase<LoadFavoritesUseCase.Params, List<Favorite>>(defaultDispatcher) {

    override fun execute(parameters: Params): Flow<Result<List<Favorite>>> {
        return when (parameters) {
            is Params.All -> userRepository.getFavorites()
            is Params.OnlySearched -> userRepository.searchFavorites(parameters.keyword)
        }
    }

    sealed interface Params {
        object All : Params
        data class OnlySearched(val keyword: String) : Params
    }
}