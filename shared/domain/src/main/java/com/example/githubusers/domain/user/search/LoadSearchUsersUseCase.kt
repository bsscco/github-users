package com.example.githubusers.domain.user.search

import androidx.paging.PagingData
import com.example.githubusers.domain.usecase.CoroutineUseCase
import com.example.githubusers.domain.usecase.FlowUseCase
import com.example.githubusers.domain.user.UserRepository
import com.example.githubusers.domain.user.favorite.AddFavoriteUseCase
import com.example.githubusers.ktutil.coroutine.DefaultDispatcher
import com.example.githubusers.ktutil.flow.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoadSearchUsersUseCase @Inject constructor(
    @DefaultDispatcher defaultDispatcher: CoroutineDispatcher,
    private val userRepository: UserRepository,
) : CoroutineUseCase<LoadSearchUsersUseCase.Params, Flow<PagingData<SearchUser>>>(defaultDispatcher) {

    override suspend fun execute(parameters: Params): Flow<PagingData<SearchUser>> {
        return userRepository.getSearchUsers(parameters.refreshFromRemote, parameters.keyword)
    }

    data class Params(
        val refreshFromRemote: Boolean,
        val keyword: String,
    )
}