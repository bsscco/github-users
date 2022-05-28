package com.example.githubusers.domain.user.detail

import com.example.githubusers.domain.usecase.FlowUseCase
import com.example.githubusers.domain.user.UserRepository
import com.example.githubusers.ktutil.coroutine.DefaultDispatcher
import com.example.githubusers.ktutil.flow.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoadDetailUserUseCase @Inject constructor(
    @DefaultDispatcher defaultDispatcher: CoroutineDispatcher,
    private val userRepository: UserRepository,
) : FlowUseCase<LoadDetailUserUseCase.Params, DetailUser>(defaultDispatcher) {

    override fun execute(parameters: Params): Flow<Result<DetailUser>> {
        return userRepository.getDetailUser(parameters.refreshFromRemote, parameters.userName)
    }

    data class Params(
        val refreshFromRemote: Boolean,
        val userName: String,
    )
}